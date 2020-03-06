package com.cjz.myok;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.cjz.myok.util.MyOk;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getAll();
        getInfo();
    }

    private void getAll(){
        MyOk.post("dataInterface/UserWorkEnvironmental/getAll", null, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //切换UI线程执行UI操作
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "访问失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("MainActivity", response.body().string());
                //切换UI线程执行UI操作
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "访问成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void getInfo(){
        //多线程并发访问
        for (int i = 5; i <= 24; i++) {
            MyOk.post("dataInterface/Stage/getInfo", "id=" + i, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.d("MainActivity888", response.body().string());
                }
            });
        }
    }

}
