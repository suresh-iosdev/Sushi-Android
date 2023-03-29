package com.app.sushi.tei.netsclicktest;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;


import com.app.sushi.tei.BuildConfig;

import java.io.IOException;
import java.lang.reflect.Field;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class UserAgentInterceptor implements Interceptor {

    final static String HEADER_USER_AGENT = "User-Agent";
    private Context context = null;

    public UserAgentInterceptor(Context context){
        super();
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request requestWithUserAgent = originalRequest.newBuilder()
                .header(HEADER_USER_AGENT, createCustomUserAgent(originalRequest))
                .build();
        return chain.proceed(requestWithUserAgent);
    }

    public static String getApplicationName(Context context) {
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        int stringId = applicationInfo.labelRes;
        return stringId == 0 ? applicationInfo.nonLocalizedLabel.toString() : context.getString(stringId);
    }

    private String createCustomUserAgent(Request originalRequest) {
        // App name can be also retrieved programmatically, but no need to do it for this sample needs
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        String osVersion = "";
        osVersion = " Android Version=" + Build.VERSION.RELEASE;

        StringBuilder androidVersion = new StringBuilder();
        androidVersion.append(osVersion).append(";");

        Field[] fields = Build.VERSION_CODES.class.getFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            int fieldValue = -1;

            try {
                fieldValue = field.getInt(new Object());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            if (fieldValue == Build.VERSION.SDK_INT) {
                androidVersion.append("Codename=").append(fieldName).append(";");
                androidVersion.append("API Level=").append(fieldValue);
                break;
            }
        }

        String ua = "[" + getApplicationName(this.context) + "/" + BuildConfig.VERSION_NAME + androidVersion + " " + manufacturer + "/" + model + "]";

        String baseUa = System.getProperty("http.agent");
        if(baseUa!=null){
            ua = ua + " " + baseUa;
        }
        return ua;
    }

}
