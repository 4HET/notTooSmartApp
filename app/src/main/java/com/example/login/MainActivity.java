package com.example.login;

import static com.example.login.LoginTest.loginTest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private DBOpenHelper dbOpenHelper;
//    ActionBar actionBar; //声明ActionBar

    // 定义SharedPreferences来接收/存储数据
    SharedPreferences sharedPreferences = null;
    String inputUsername;
    String inputPassword;
    boolean login = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        actionBar = getActionBar(); //得到ActionBar
//        actionBar.hide(); //隐藏ActionBar

        // 强制在主线程中使用网络请求
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        dbOpenHelper = new DBOpenHelper(this, "user.db", null, 1);

        sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        inputUsername = sharedPreferences.getString("inputUsername", null);//(key,若无数据需要赋的值)
        inputPassword = sharedPreferences.getString("inputPassword", null);



        // 密码之前就已经存在，则直接取出密码且无需检验，因为在保存密码之前就已经检验过了
        if(inputPassword != null) {
            try {
                login = LoginTest.loginTest(inputUsername, inputPassword);
                if (login) {
                    Toast.makeText(MainActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MainActivity.this, PunchActivity.class);

                    // 将用户名密码传入到为webview
                    intent.putExtra("inputUsername", inputUsername);
                    intent.putExtra("inputPassword", inputPassword);

                    startActivity(intent);

                    System.out.println(inputPassword);
                } else {
                    Toast.makeText(MainActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("error happen!!!");
                Toast.makeText(MainActivity.this, "程序好像出现了错误~~", Toast.LENGTH_SHORT).show();
            }
        }

        Button createDatabase = (Button) findViewById(R.id.lg_login);
        createDatabase.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {
                dbOpenHelper.getWritableDatabase();


                // 用户之前没有登陆过，获取到的密码是null
                if (inputPassword == null) {
                    // 获取输入的用户名、密码

                    EditText username = findViewById(R.id.lg_username);
                    EditText password = findViewById(R.id.lg_password);
                    inputUsername = username.getText().toString();
                    inputPassword = password.getText().toString();

                    // 检验账号密码的合法性
                    try {
                        login = LoginTest.loginTest(inputUsername, inputPassword);
                        // 若账号密码合法，则登录并且保存密码
                        if (login) {
                            Toast.makeText(MainActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(MainActivity.this, PunchActivity.class);

                            // 将用户名密码传入到为webview
                            intent.putExtra("inputUsername", inputUsername);
                            intent.putExtra("inputPassword", inputPassword);


                            startActivity(intent);

                            // 这里是保存密码的部分
                            sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("inputUsername", inputUsername);
                            editor.putString("inputPassword", inputPassword);
                            editor.commit();
                            System.out.println(inputPassword);
                        } else {
                            Toast.makeText(MainActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("error happen!!!");
                        Toast.makeText(MainActivity.this, "程序好像出现了错误~~", Toast.LENGTH_SHORT).show();
                    }
                }


//                Toast.makeText(MainActivity.this, inputPassword, Toast.LENGTH_SHORT).show();

            }
        });
    }


}