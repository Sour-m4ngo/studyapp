package com.example.studyapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DialoFragment_startCount extends DialogFragment implements View.OnClickListener {
    private View myView;
    private EditText EdInputTime;
    private Button BtnStart;
    private String contentForSearch =  " ";
    public DialoFragment_startCount() {
        super();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        myView = inflater.inflate(R.layout.dialog_start_count,null);
        builder.setView(myView);
        initView();
        initEvents();
        return builder.create();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();

        if (bundle != null) {
            contentForSearch = bundle.getString("NOTECONTENT");
        }else {
            //throw
        }
    }

    private void initView (){
        BtnStart = myView.findViewById(R.id.btn_dialog_start);
        EdInputTime = myView.findViewById(R.id.edit_input_time);
    }
    private void initEvents (){
        BtnStart.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_dialog_start:
                int duration = 0;
                String test = EdInputTime.getText().toString();
                if(test.isEmpty()){
                    Toast.makeText(getContext(),"请输入一个大于0的数字",Toast.LENGTH_SHORT).show();
                }else {
                    duration = Integer.parseInt(EdInputTime.getText().toString());
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("CONTENT",contentForSearch);
                    bundle.putInt("DURATION",duration);
                    intent.putExtras(bundle);
                    intent.setClass(getContext(),TimeActivity.class);
                    startActivity(intent);
                    dismiss();
                }
                break;
        }
    }
    public static DialoFragment_startCount newIntance(String noteContent){
        DialoFragment_startCount StartCount = new DialoFragment_startCount();
        Bundle bundle = new Bundle();
        bundle.putString("NOTECONTENT",noteContent);
        StartCount.setArguments(bundle);
        return  StartCount;
    }
}
