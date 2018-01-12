package com.example.administrator.testverticalviewpager.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.example.administrator.testverticalviewpager.R;

/**
 * Created by Apollo on 2018/1/12 16:01.
 */

public class TestHandlerActivity extends Activity {
    private Handler mainHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.d("apollo", (String) msg.obj);
        }
    };
    private Handler threadHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testhandler);
        findViewById(R.id.btn_main_to).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = Message.obtain();
                message.obj = Thread.currentThread().getName()+":我是主线程发来的消息";
                threadHandler.sendMessage(message);
            }
        });

        findViewById(R.id.btn_to_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        threadHandler = new Handler() {
                            @Override
                            public void handleMessage(Message msg) {
                                super.handleMessage(msg);
                                Log.d("apollo", (String) msg.obj);
                            }
                        };
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Message message = Message.obtain();
                        message.obj = Thread.currentThread().getName()+":我是子线程发来的数据";
                        mainHandler.sendMessage(message);
                        Looper.loop();
                    }
                }).start();
            }
        });
    }
}
