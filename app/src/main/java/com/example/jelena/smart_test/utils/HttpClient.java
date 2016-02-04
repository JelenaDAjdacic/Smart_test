package com.example.jelena.smart_test.utils;

import android.webkit.URLUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


public class HttpClient {

    String url_address;
    URL url;
    InputStream inputStream=null;
    HttpsURLConnection connection;
    HttpURLConnection httpConnection ;

    ByteArrayOutputStream bufferByte = new ByteArrayOutputStream();
    String content=null;

    public HttpClient(String url_address) {

        this.url_address = url_address;
    }
    public String getContent(){


            if (URLUtil.isHttpUrl(url_address)){

                return getHttpContent();
            }
            else

                return getHttpsContent();


    }

    public String getHttpsContent() {
        try {

            url = new URL(url_address);
            TrustManagerManipulator.allowAllSSL();
            connection = (HttpsURLConnection) url.openConnection();
            inputStream=connection.getInputStream();
            int read=-1;
            byte[] buffer=new byte[1024];
            while ((read=inputStream.read(buffer))!=-1){
                bufferByte.write(buffer, 0, read);

            }
            content=bufferByte.toString();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (connection!=null) connection.disconnect();

            if (inputStream!=null) try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        return content;
    }
    public String getHttpContent() {
        try {

            url = new URL(url_address);
            TrustManagerManipulator.allowAllSSL();
            httpConnection = (HttpURLConnection) url.openConnection();
            inputStream=httpConnection.getInputStream();
            int read=-1;
            byte[] buffer=new byte[1024];
            while ((read=inputStream.read(buffer))!=-1){
                bufferByte.write(buffer, 0, read);

            }
            content=bufferByte.toString();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (connection!=null) connection.disconnect();

            if (inputStream!=null) try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        return content;
    }

   private static class TrustManagerManipulator implements X509TrustManager {

        private static TrustManager[] trustManagers;
        private static final X509Certificate[] acceptedIssuers = new X509Certificate[] {};


        public boolean isClientTrusted(X509Certificate[] chain) {
            return true;
        }

        public boolean isServerTrusted(X509Certificate[] chain) {
            return true;
        }


        public static void allowAllSSL() {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            SSLContext context = null;
            if (trustManagers == null) {
                trustManagers = new TrustManager[] { new TrustManagerManipulator() };
            }
            try {
                context = SSLContext.getInstance("TLS");
                context.init(null, trustManagers, new SecureRandom());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }
            HttpsURLConnection.setDefaultSSLSocketFactory(context
                    .getSocketFactory());
        }

        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return acceptedIssuers;
        }
    }

}