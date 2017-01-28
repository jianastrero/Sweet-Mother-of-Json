package com.jianastrero.sweetmotherofjson;

import android.os.AsyncTask;
import android.util.Log;

import com.jianastrero.sweetmotherofjson.exception.SweetJsonSuperNotCalledException;
import com.jianastrero.sweetmotherofjson.exception.UnknownSubclassInstanceException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Jian Astrero on 1/10/2017.
 */
public class SweetJson {

    private Task task;
    private JSONObject postDataParams;
    private String route;
    private OnConnectionListener onConnectionListener;
    private boolean sweeter;
    private Object object;
    private boolean isSuperCalled=false;

    private final int TIMEOUT = 5 * 1000;

    public SweetJson() {
        task=new Task();
        postDataParams = new JSONObject();
        sweeter=true;
        object=null;
        isSuperCalled=true;
    }

    public void submit() {
        if (!isSuperCalled) throw new SweetJsonSuperNotCalledException();
        if (object==null) throw new UnknownSubclassInstanceException();
        task.execute();
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public void addData(String key, Object value) {
        try {
            postDataParams.put(key, ""+value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean isSweeter() {
        return sweeter;
    }

    public void setSweeter(boolean sweeter) {
        this.sweeter = sweeter;
    }

    public void setSubclassInstance(SweetJson subclassInstance) {
        this.object = subclassInstance;
    }

    private void addAllData() {
        if (!isSuperCalled) throw new SweetJsonSuperNotCalledException();
        if (object==null) throw new UnknownSubclassInstanceException();
        if (sweeter) {
            Field[] fields=object.getClass().getFields();

            for (Field f:fields) {
                try {
                    addData(f.getName(), f.get(object));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setOnConnectionListener(OnConnectionListener onConnectionListener) {
        this.onConnectionListener = onConnectionListener;
    }

    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while (itr.hasNext()) {

            String key = itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }

    private class Task extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            if (onConnectionListener != null) onConnectionListener.onBeforeConnectionStart();
            addAllData();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String path = SweetJsonConfig.getUrl(route);
                URL url = new URL(path);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(TIMEOUT);
                conn.setConnectTimeout(TIMEOUT);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {
                        sb.append(line);
                    }

                    in.close();
                    return sb.toString();

                } else {
                    return "Can't connect to server!\nError code: " + responseCode;
                }
            } catch (ConnectException e) {
                return "Please check your internet connection!";
            } catch (Exception e) {
                e.printStackTrace();
                return "Can't connect to server!\nError: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (onConnectionListener != null) {
                JSONObject jsonObject=null;
                JSONArray jsonArray=null;
                try{
                    jsonObject=new JSONObject(s);
                } catch (Exception e) {}
                try{
                    jsonArray=new JSONArray(s);
                } catch (Exception e) {}
                onConnectionListener.onAfterConnectionStopped(s, jsonObject, jsonArray);
            }
        }
    }

    public interface OnConnectionListener {
        void onBeforeConnectionStart();
        void onAfterConnectionStopped(String result, JSONObject jsonObject, JSONArray jsonArray);
    }
}
