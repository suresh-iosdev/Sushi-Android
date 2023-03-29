package com.app.sushi.tei.netsclicktest.HTTPRequests;

import android.content.Context;
import android.util.Log;


import com.app.sushi.tei.R;
import com.app.sushi.tei.netsclicktest.UserAgentInterceptor;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class BaseTask {

    private static final String TAG = BaseTask.class.getName();
    private int timeoutMs = 60000;
    private OkHttpClient client = null;
    private String url;
    private Request request;
    private String reqData;
    protected Context context = null;
    private String mid = "";

    public BaseTask(Context context, String reqData, String mid){
        this.context = context;
        this.reqData = reqData;
        this.mid = mid;
        getOkHttpClient();
    }

    //protected abstract Object parseResponse(String respStr);

    public String send() throws Exception {
        String resp = null;

        MediaType mediaType      = MediaType.parse("application/json; charset=utf-8"); //"text/plain; charset=utf-8");
        RequestBody requestBody    = RequestBody.create(mediaType, this.reqData);
        request = new Request.Builder().url(this.url)
                .post(requestBody)
                .build();
        Call call = this.client.newCall(this.request);
        Response response = null;
        try {
            response = call.execute();
            String respStr = response.body().string();
            System.out.println("HTTP RESP:"+ response.code());
            System.out.println("resp:"+ respStr);
            if(response.code()!=200){
                throw new Exception("HTTP " + response.code());
            }
            //Gson g = new Gson();
            //resp = g.fromJson(respStr, GMTMerchantResponse.class);
            //resp = parseResponse(respStr);
            resp = respStr;
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception(e);
        } catch(IllegalStateException e){
            e.printStackTrace();
            throw new Exception(e);
        }

        return resp;
    }

    protected void getOkHttpClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> Log.d(TAG, message));
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            /*KeyStore keyStore = readKeyStore(context);
// Install the all-trusting trust manager
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("X509");
            kmf.init(keyStore, "admin".toCharArray());
            KeyManager[] keyManagers = kmf.getKeyManagers();*/

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            this.client = builder
                    .readTimeout(timeoutMs, TimeUnit.SECONDS)
                    .writeTimeout(timeoutMs, TimeUnit.SECONDS)
                    .connectTimeout(timeoutMs, TimeUnit.SECONDS)
                    .addInterceptor(loggingInterceptor)
                    .addInterceptor(new UserAgentInterceptor(this.context))
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUrlWithTid(String url, String tid) {
        this.url = url + "?mid=" + this.mid + "&tid=" + tid;
    }

    KeyStore readKeyStore(Context context) {
        KeyStore ks = null;
        InputStream in = null;
        try {
            ks = KeyStore.getInstance("PKCS7");
//ks = KeyStore.getInstance(KeyStore.getDefaultType());
// get user password and file input stream
            char[] password = "23MilkSha".toCharArray();
            in = context.getResources().openRawResource(R.raw.netspay_nets_com_sg);
            ks.load(in, password);
        } catch (KeyStoreException | NoSuchAlgorithmException | IOException | CertificateException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return ks;
    }
}
