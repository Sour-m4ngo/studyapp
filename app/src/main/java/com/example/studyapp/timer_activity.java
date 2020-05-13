package com.example.studyapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

public class timer_activity extends AppCompatActivity {
    private long time;
    private TextView tv_timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_activity);
        tv_timer=findViewById(R.id.tv_timer);
        Intent intent = getIntent();
        time = intent.getLongExtra("time",0);
        time = time *1000;
        new CountDownTimer(time,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tv_timer.setText("距离世界毁灭还有 " + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                Toast.makeText(timer_activity.this,"世界毁灭了，请玩家重新开始游戏",Toast.LENGTH_SHORT).show();
            }
        }.start();
    }
}
