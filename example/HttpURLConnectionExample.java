package org.example;

import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class HttpURLConnectionExample {
    public static String setGet(String urlstr) throws Exception{
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlstr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while((line = br.readLine())!= null) {
            result.append(line);
        }
        br.close();
        return result.toString();
    }

    public static void main(String args[]) {
        try {
            System.out.println(setGet("https://codingclub.tech/test-get-request?name=Revanth"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}