package com.sceptive.forgiva.integrator.test;

import com.sceptive.forgiva.integrator.core.Configuration;
import com.sceptive.forgiva.integrator.logging.Info;
import com.sceptive.forgiva.integrator.test.webservices.General;
import okhttp3.*;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.cert.CertificateException;

import static com.sceptive.forgiva.integrator.core.Constants.WEB_API_BASEPATH;

public class HttpsClient {

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");



    static OkHttpClient client = null;

    private static OkHttpClient getUnsafeOkHttpClient() {
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

            OkHttpClient okHttpClient = builder.build();

            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static SimpleResponse post_api(String service_path, String json) throws IOException {

        if (client == null) {
            client = getUnsafeOkHttpClient();
        }

        String url = General.https_url_header+ Configuration.runtime.https_port + WEB_API_BASEPATH + service_path;

        RequestBody body = RequestBody.create(json, JSON); // new
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();

        return new SimpleResponse().code(response.code()).body(response.body().string());
    }
}
