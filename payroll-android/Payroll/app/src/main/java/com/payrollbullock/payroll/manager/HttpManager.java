package com.payrollbullock.payroll.manager;

import android.util.Base64;

import com.payrollbullock.payroll.param.RequestParam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;



/**
 * Created by bibekshakya on 7/8/16.
 */
public class HttpManager {

    //for request param
    public static String getData(RequestParam requestParam,String username,String password){
        //Bufferreader
        final String AUTHORIZATION ="Authorization";
        final String PREFIX ="Basic ";
        byte [] auth_user = (username+":"+password).getBytes();
        StringBuilder sbAuth = new StringBuilder()
                .append(PREFIX)
                .append(Base64.encodeToString(auth_user,Base64.DEFAULT));

        String uri = requestParam.getUri();
        //only for get Method
        if (requestParam.getMethod().equals("GET")){
            uri += "?"+requestParam.getEncodeParam();
        }

        BufferedReader bufferedReader = null;

        HttpURLConnection httpUrlConnection = null;

            try {

                URL url =new URL(uri);
                //for okhttp library to use
            /*    OkHttpClient client = new OkHttpClient();

                httpUrlConnection = new OkUrlFactory(client).open(url)*/;

                httpUrlConnection = (HttpURLConnection)url.openConnection();
                //ready to get content from web

                //before connection
                httpUrlConnection.addRequestProperty("Authorization",sbAuth.toString());
                httpUrlConnection.setRequestMethod(requestParam.getMethod());
                if (httpUrlConnection.getRequestMethod().equals("POST")){
                    httpUrlConnection.setDoOutput(true);
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpUrlConnection.getOutputStream());

                    outputStreamWriter.write(requestParam.getEncodeParam());
                    //any this written in memory will be flush and sent to server
                    outputStreamWriter.flush();
                }

                StringBuilder sb = new StringBuilder();
                bufferedReader = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream()));
                String line = null;
                while ((line= bufferedReader.readLine())!=null){
                    sb.append(line+"\n");
                }
                return sb.toString();


            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }


        }

    public static String getData(String uri,String username,String password){
        BufferedReader bufferedReader = null;

        HttpURLConnection httpURLConnection =null;
        final String AUTHORIZATION="Authorization";
        final String AUTHORIZATION_PREFIX="Basic ";
        byte[] authU_P =  (username+":"+password).getBytes();
        StringBuilder authorizationBuilder=new StringBuilder()
                .append(AUTHORIZATION_PREFIX)
                .append(Base64.encodeToString(authU_P,Base64.DEFAULT));
        try {
            URL url = new URL(uri);
            httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.addRequestProperty(AUTHORIZATION,authorizationBuilder.toString());
            StringBuilder internetContent = new StringBuilder();
            bufferedReader=new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String content = null;
            while((content=bufferedReader.readLine())!=null){
                internetContent.append(content+"\n");
            }
            return internetContent.toString();

        }catch (Exception e){
            e.printStackTrace();
            try {
                int status =httpURLConnection.getResponseCode();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return null;
        }
        finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                httpURLConnection.disconnect();

            }
        }



    }

    public static String getData(String uri){
        BufferedReader bufferedReader = null;
        HttpURLConnection urlConnection=null;
        try {
            URL url =new URL(uri);
            urlConnection=(HttpURLConnection)url.openConnection();
            //ready to get content from the web and get one line of string or content
            StringBuilder internetContent = new StringBuilder();
            bufferedReader=new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String content = null;
            while((content=bufferedReader.readLine())!=null){
                internetContent.append(content+"\n");
            }
            return content;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
        finally {
            if (bufferedReader!=null){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                urlConnection.disconnect();

            }
        }
    }

}
