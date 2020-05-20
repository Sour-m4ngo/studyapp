package com.example.studyapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DialoFragment_startCount extends DialogFragment implements View.OnClickListener {
    private View myView;
    private EditText EdInputTime;
    private Button BtnStart;
    public DialoFragment_startCount() {
        super();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);
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

                break;
        }
    }
}
