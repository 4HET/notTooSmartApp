package com.example.login;

import com.alibaba.fastjson.JSONObject;
//import jdk.nashorn.internal.parser.JSONParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class Punch {
    public static String sendPostGetPunch(String userName, String password, String place) throws Exception {
        String cookie = getCookie(userName, password);
        System.out.println(cookie);
        String home = "";

        if (place == "山东省日照市东港区学海路85正靠近中国银行（大学城分理处）")
            home = "在校";
        else
            home = "在家";
        // 自动打卡抓包获得的网址
        String urlPath = "http://xuegong.qfnu.edu.cn:8080/student/healthInfo/save";
        String headerField = "";
        JSONObject info = new JSONObject();
        info.put("home", home);
        info.put("address",place);
        info.put("keepInHome", "否");
        info.put("keepInHomeDate", "null");
        info.put("keepInHomeReasonSite", "");
        info.put("contact", "否");
        info.put("contactType", "");
        info.put("infect", "否");
        info.put("infectType", "");
        info.put("infectDate", "");
        info.put("familyNCP", "否");
        info.put("familyNCPType", "");
        info.put("familyNCPDate", "");
        info.put("familyNCPRelation", "");
        info.put("cold", "否");
        info.put("fever", "否");
        info.put("feverValue", "");
        info.put("cough", "否");
        info.put("diarrhea", "否");
        info.put("homeInHubei", "否");
        info.put("arriveHubei", "无");
        info.put("travel", "无");
        info.put("remark", "暂无");
        info.put("submitCount", 2);
        info.put("contactDetail", "");
        info.put("location", place);
        info.put("naDetection", "否");
        info.put("areaInfect", "否");
        info.put("areaInfectType", "");
        info.put("areaInfectDate", "");
        info.put("areaInfectNumber", "");
        info.put("contactAH", "否");
        info.put("contactAHDetail", "");
        info.put("outProvinceBack14", "未出省");
        info.put("naDetectionDate", "");
        info.put("pharynxResult", "阴性");
        info.put("anusResult", "阴性");
        info.put("saDetection", "否");
        info.put("lgMResult", "阴性");
        info.put("lgGResult", "阴性");
        info.put("saDetectionDate", "");
        info.put("vaccinationStatus", "已接种_完成第2剂");

        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL url = new URL(urlPath);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("user-agent", "Dart/2.13 (dart:io)");
            conn.setRequestProperty("content-type", "application/json; charset=utf-8");
            conn.setRequestProperty("accept-encoding", "gzip");
            conn.setRequestProperty("cookie",cookie);


            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //1.获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            OutputStream _out = new DataOutputStream(conn.getOutputStream()) ;
            // POST的请求参数写在正文中

            _out.write((info.toString()).getBytes());
            _out.flush();
            _out.close();
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

    public static String getCookie(String userName, String password) throws Exception {
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

        String url = "http://202.194.176.81:8080/studentJwc/timetable";

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
//        ("2020416094","93999331Xj")

        String userName = "2020416094";
        String password = "93999331Xj";

        String place = "山东省日照市东港区学海路85正靠近中国银行（大学城分理处）";
        String response = sendPostGetPunch(userName, password, place);
        System.out.println(response);
        JSONObject res = JSONObject.parseObject(response);
        System.out.println((boolean) res.get("success"));
    }
}
