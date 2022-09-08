package com.example.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class PunchActivity extends Activity {
    private WebView webView;
    String inputUsername;
    String inputPassword;

    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punch);
        webView = (WebView) findViewById(R.id.web_view);
//        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);

        // 从MainActivity中提取数据
        Intent intent = getIntent();
        inputUsername = intent.getStringExtra("inputUsername");
        inputPassword = intent.getStringExtra("inputPassword");
        Toast.makeText(this, inputPassword, Toast.LENGTH_SHORT).show();
//        System.out.println(inputPassword);

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url); // 根据传入的参数再去加载新的网页
                return true; // 表示当前WebView可以处理打开新网页的请求，不用借助系统浏览器
            }
        });
        webView.loadUrl("http://47.98.116.174:8000/mainActivity/");
        webView.addJavascriptInterface(PunchActivity.this, "getUsername");
        webView.addJavascriptInterface(PunchActivity.this, "getPassword");


    }
    @JavascriptInterface
    public String getPassword() {
        Log.i("inputPassword", "js接受了来自安卓的密码");
        return inputPassword;
    }

    @JavascriptInterface
    public String getUsername() {
        Log.i("inputUsername", "js接受了来自安卓的用户名");
        return inputUsername;
    }

//    @JavascriptInterface
//    public Map<String, String> androidMethod() {
//        Log.i("qcl0228", "js调用了安卓的方法");
//        Map<String, String> res = new HashMap<String, String>();
//        res.put("inputUsername",inputUsername);
//        res.put("inputPassword", inputPassword);
//        return res;
//    }
}
