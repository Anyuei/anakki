package com.anakki.data.utils.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
 public class HttpUtil {
     public static void main(String[] args) throws IOException {
         HashMap<String, String> objectObjectHashMap = new HashMap<>();
         objectObjectHashMap.put("ip","61.149.7.157");
         String s = sendGetRequest("https://qifu-api.baidubce.com/ip/geo/v1/district", objectObjectHashMap);
         System.out.println(s);
     }
    public static String sendGetRequest(String url, HashMap<String, String> params) throws IOException {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuilder response = new StringBuilder();
         try {
            StringBuilder urlBuilder = new StringBuilder(url);
            if (params != null && !params.isEmpty()) {
                urlBuilder.append("?");
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    urlBuilder.append(entry.getKey())
                            .append("=")
                            .append(entry.getValue())
                            .append("&");
                }
                urlBuilder.deleteCharAt(urlBuilder.length() - 1);
            }
             URL requestUrl = new URL(urlBuilder.toString());
            connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("GET");
             reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
         return response.toString();
    }
     public static String sendPostRequest(String url, HashMap<String, String> requestBody) throws IOException {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuilder response = new StringBuilder();
         try {
            URL requestUrl = new URL(url);
            connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
             StringBuilder bodyBuilder = new StringBuilder();
            if (requestBody != null && !requestBody.isEmpty()) {
                for (Map.Entry<String, String> entry : requestBody.entrySet()) {
                    bodyBuilder.append(entry.getKey())
                            .append("=")
                            .append(entry.getValue())
                            .append("&");
                }
                bodyBuilder.deleteCharAt(bodyBuilder.length() - 1);
            }
             OutputStream outputStream = connection.getOutputStream();
            outputStream.write(bodyBuilder.toString().getBytes());
            outputStream.flush();
            outputStream.close();
             reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
         return response.toString();
    }
}