package com.example.login;

import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class GetScore {
    public static String sendPostGetCookie(String userName, String password) throws Exception {
//        OutputStreamWriter out = null;
        String urlPath = "http://xuegong.qfnu.edu.cn:8080/authentication/login";
        String headerField = "";
        try {
            URL url = new URL(urlPath);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("user-agent", "Dart/2.13 (dart:io)");
            conn.setRequestProperty("content-type", "application/json; charset=utf-8");
            conn.setRequestProperty("accept-encoding", "gzip");

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("username", userName);
            jsonParam.put("password", password);
            jsonParam.put("type", "student");

//            conn.setRequestProperty("username", userName);
//            conn.setRequestProperty("password", password);
//            conn.setRequestProperty("json", jsonPrarms);


            // 发送POST请求必须设置为true
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //设置连接超时时间和读取超时时间
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
//            out = new OutputStreamWriter(conn.getOutputStream());
            OutputStream out = new DataOutputStream(conn.getOutputStream()) ;
            // POST的请求参数写在正文中

            out.write((jsonParam.toString()).getBytes());
            out.flush();
            out.close();
            headerField = String.valueOf(conn.getHeaderFields());

//            System.out.println(conn.getResponseCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return headerField.split(";")[3].split(", ")[2];
    }

    public static String sendPost(String cookie) {

        String url = "http://202.194.176.81:8080/studentJwc/score";

        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("user-agent", "Dart/2.13 (dart:io)");
            conn.setRequestProperty("content-type", "application/json; charset=utf-8");
            conn.setRequestProperty("accept-encoding","gzip");
            conn.setRequestProperty("cookie",cookie);
            conn.setRequestProperty("host","202.194.176.81:8080");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //1.获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            //2.中文有乱码的需要将PrintWriter改为如下
            //out=new OutputStreamWriter(conn.getOutputStream(),"UTF-8")
//            // 发送请求参数
//            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        // System.out.println("post推送结果："+result);
        return result;
    }

    public static void main(String[] args) throws Exception {
        String userName = "2020416094";
        String password = "93999331Xj";

        String error = "Content-Type=[application/json";
        String cookie = sendPostGetCookie(userName, password);
//        cookie = "syt.sessionId=af387ceb-9b03-4cd5-95ce-9f5298e10890";
        System.out.println(cookie);
        String rs = sendPost(cookie);
        System.out.println(rs);
    }
}
