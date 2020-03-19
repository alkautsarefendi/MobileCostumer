package com.fortuna.android.mobilecustomer.util;

import android.util.Log;

import com.fortuna.android.mobilecustomer.dao.BaseDAO;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by Stuarez on 12/1/2016.
 */
public class ConnectivityWS {

    public static JSONObject getFromServer(String url){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        if(url.contains("?")){
            String dataParams = url.split("\\?")[1];
            Log.v("aa", dataParams);
            String[] params = dataParams.split("&");
            for (String param : params) {
                String[] data = param.split("=");
                nameValuePairs.add(new BasicNameValuePair(data[0], data[1]));
            }
        }
        try{
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            HttpContext localContext = new BasicHttpContext();
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost, localContext);

            InputStream is = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"),8);
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            is.close();

            return new JSONObject(sb.toString());
        }catch(Exception e){
            e.printStackTrace();
            try {
                return new JSONObject("{CODE:99,MESSAGE:"+e.getMessage()+"}");
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }

    public static JSONObject postToServer(BaseDAO domain, String url) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

		/*Generate Params from Domain*/
        Field[] declaredFields = domain.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            try{
                if(field.get(domain) != null){
                    nameValuePairs.add(
                            new BasicNameValuePair(field.getName().toUpperCase(),
                                    String.valueOf(field.get(domain))
                            )
                    );
                }
            }catch(Exception e){
                Log.v("", e.getMessage());
                e.printStackTrace();
            }
        }

        if(url.contains("?")){
            String dataParams = url.split("\\?")[1];
            Log.v("aa", dataParams);
            String[] params = dataParams.split("&");
            for (String param : params) {
                String[] data = param.split("=");
                nameValuePairs.add(new BasicNameValuePair(data[0], data[1]));
            }
        }
        try{
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            HttpContext localContext = new BasicHttpContext();
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost, localContext);

            InputStream is = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"),8);
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            is.close();

            return new JSONObject(sb.toString());
        }catch(Exception e){
            e.printStackTrace();
            try {
                return new JSONObject("{CODE:99,MESSAGE:"+e.getMessage()+"}");
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }
}