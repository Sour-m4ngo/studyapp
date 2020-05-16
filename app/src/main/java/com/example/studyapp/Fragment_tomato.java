package com.example.studyapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Fragment_tomato extends Fragment {
    private long time1;
    private static final String Tag= "test";
    private Button mBtnButton;
    private EditText et_time;
    public Fragment_tomato() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_ins_time, container, false);
        mBtnButton =view.findViewById(R.id.btn_button1);
        et_time = view.findViewById(R.id.ET1);
        mBtnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            //点击事件
            public void onClick(View view) {
                //跳转到倒计时界面
                Intent intent = new Intent(getContext(),TimeActivity.class);
                String text=et_time.getText().toString().trim();
                //判断用户是否输入数字
                if(text.isEmpty())
                {
                    Log.d(Tag,"进入if");
                    Toast.makeText(getContext(),"请输入数字",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Log.d(Tag,"进入else");
                    time1 =Integer.parseInt(et_time.getText().toString());
                    Bundle bundle=new Bundle();
                    bundle.putLong("num",time1);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
        return view;
    }
}
