package com.example.jelena.smart_test.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Win 7 on 25.1.2016.
 */
public class HttpClient {
    String https_url;
    URL url;
    InputStream inputStream=null;
    HttpsURLConnection connection;
    ByteArrayOutputStream bufferByte = new ByteArrayOutputStream();
    String content=null;

    public HttpClient(String https_url) {

        this.https_url = https_url;
    }

    public String getContent() {
        try {

            url = new URL(https_url);
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

}