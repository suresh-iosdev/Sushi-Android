package com.app.sushi.tei.WebService;


import android.content.Context;
import android.util.Log;

import com.app.sushi.tei.GlobalMembers.GlobalValues;
import com.app.sushi.tei.Utils.Prefs;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class WebserviceAssessor {

    JSONObject jArray = null;
    static JSONObject jsonResponse;

    static String bufferData;
    public static int RESPONSE_OK = 200;
    static InputStream is = null;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static OkHttpClient getClient()
    {

        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(60, TimeUnit.SECONDS);
        client.setReadTimeout(60, TimeUnit.SECONDS);
        client.setWriteTimeout(60, TimeUnit.SECONDS);

       /* client..connectTimeout(10, TimeUnit.SECONDS);
        client.writeTimeout(10, TimeUnit.SECONDS);
        client.readTimeout(50, TimeUnit.SECONDS);*/

        return client;
    }

    public static String getData(String url) {

        try {
            OkHttpClient client = getClient();
            Log.e("url_Support", url+"\n"+ GlobalValues.ACCESS_TOKEN);
            Request request=null;
                    if(!GlobalValues.ACCESS_TOKEN.isEmpty()){
                         request = new Request.Builder()
                                .url(url)
                                 .addHeader("Authorization",GlobalValues.ACCESS_TOKEN)
                                .build();

                    }else {
                        request = new Request.Builder()
                                .url(url)
                                .build();
                    }
            Response response = client.newCall(request).execute();
            bufferData = response.body().string();
            System.out.println(bufferData);

        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return bufferData;
    }

    public static String GetRequest(String url) {

        try {


            OkHttpClient client = getClient();
            Log.e("url_Support", url+"\n"+GlobalValues.ACCESS_TOKEN);
            Request request;
            if(!GlobalValues.ACCESS_TOKEN.isEmpty()){
                 request = new Request.Builder()
                         .url(url)
                         .addHeader("Authorization",GlobalValues.ACCESS_TOKEN)
                         .build();
            }else {
                 request = new Request.Builder().url(url).build();
            }

            Response response = client.newCall(request).execute();
            bufferData = response.body().string();
            System.out.println(bufferData);

        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return bufferData;
    }


    public static String postRequest(Context context, String url, Map<String, String> mapData) {


        String result = null;
        try {
            final Context mContext = context;

         //   RequestBody formBody = null;


            OkHttpClient client = getClient();
            MultipartBuilder multipartBuilder = new MultipartBuilder();
            multipartBuilder.type(MultipartBuilder.FORM);
            for (String key:mapData.keySet()) {
                multipartBuilder.addFormDataPart(key,mapData.get(key));
            }
            RequestBody requestBody = multipartBuilder.build();


             Request request = null;
            if(!GlobalValues.ACCESS_TOKEN.isEmpty()){
                request = new Request.Builder()
                        .url(url)
                        .addHeader("Authorization",GlobalValues.ACCESS_TOKEN)
                        .post(requestBody)
                        .build();
            }else {
                request = new Request.Builder()
                        .url(url)
                        .post(requestBody)
                        .build();
            }


            Response response = client.newCall(request).execute();
            result = response.body().string();
            System.out.println("response------>" + result);


        } catch (Exception e) {

            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }



}
