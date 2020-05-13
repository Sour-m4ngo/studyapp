package com.example.studyapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import java.util.Calendar;

public class DialogFragment_AddNotes extends DialogFragment implements View.OnClickListener {
    private Button BtnSelectTarget;
    private Button BtnSelectHabit;
    private Button BtnTargetTime;
    private Button BtnFinish;
    private Button BtnExit;
    private Spinner SpiWorkFrequency;
    private Spinner SpiUnitOfTime;
    private RoundCornerProgressBar progress1;
    private LinearLayout llNotesTarget;
    private LinearLayout llNotesHabit;
    private TextView t;
    private EditText EtWorkHours;
    private EditText EtNotes;
    private View ll_view;
    public static final String REQUESE="response";
    public static final String CONTENT="content";
    public static final String TYPE="type";
    public static final String FINISHDATE="finish";
    public static final String TOTALTIME="totaltime";
    public static final String UNITOFTIME="unitoftime";
    public static final String WORKFREQUENCY="workfrequency";
    private String NotesContent = null;//待办事项的内容
    private String NotesType = "目标";//待办事件类型
    private String TotalTime;//目标制定类型待办事项需要的总时长
    private String UnitOfTime = "0";//时间单位,0为小时，1为分钟，2为秒
    private String WorkFrequency="0";//工作频率，0为每天，1为每周，2为每月
    private int[] FinishDate = new int[]{0,0,0};//完成日期，年月日
    private Boolean IsSelectDate = false;
    private int pro = 0;//
    private int ra = 0;

    Calendar ca = Calendar.getInstance();
    int  mYear = ca.get(Calendar.YEAR);
    int  mMonth = ca.get(Calendar.MONTH);
    int  mDay = ca.get(Calendar.DAY_OF_MONTH);

    String DateOfSelect = null;
    public DialogFragment_AddNotes() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(-1,-2 );//这个好像能让dialog界面宽度和高度自适应变化，没看出来，先留着
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        ll_view = inflater.inflate(R.layout.dialog_add_notes,null);
        builder.setView(ll_view);
        initView();
        initEvents();
        return builder.create();
    }
    private void initView (){

        BtnSelectTarget = ll_view.findViewById(R.id.btn_notes_select_target);
        BtnSelectHabit = ll_view.findViewById(R.id.btn_notes_select_habit);
        BtnTargetTime = ll_view.findViewById(R.id.btn_notes_target_time);
        BtnFinish = ll_view.findViewById(R.id.btn_notes_finish);
        llNotesTarget = ll_view.findViewById(R.id.ll_notes_target);
        llNotesHabit = ll_view.findViewById(R.id.ll_notes_habit);
        SpiWorkFrequency = ll_view.findViewById(R.id.spi_notes_work_frequency);
        SpiUnitOfTime = ll_view.findViewById(R.id.spi_notes_UnitOfTime);
        t = ll_view.findViewById(R.id.tv_notes_t);
        EtWorkHours = ll_view.findViewById(R.id.et_notes_workHours);
        EtNotes = ll_view.findViewById(R.id.et_notes);
        BtnExit = ll_view.findViewById(R.id.btn_notes_exit);

    }
    private void initEvents(){

        BtnSelectHabit.setOnClickListener(this);
        BtnSelectTarget.setOnClickListener(this);
        BtnTargetTime.setOnClickListener(this);
        BtnFinish.setOnClickListener(this);
        BtnExit.setOnClickListener(this);

        SpiWorkFrequency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//选择工作频率
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String[] Fre = getResources().getStringArray(R.array.time_frequency);
                WorkFrequency = Fre[position];
                //Toast.makeText(getActivity(), "你点击的是:"+Fre[position], Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SpiUnitOfTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//选择时间单位
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] Unit = getResources().getStringArray(R.array.time_type);
                UnitOfTime = Unit[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_notes_select_habit://选中“习惯养成”按钮
                ResetView();
                EtNotes.setHint("请输入习惯内容");
                t.setText("每次完成");
                NotesType="习惯";
                BtnSelectHabit.setBackgroundColor(Color.parseColor("#FF4500"));
                llNotesHabit.setVisibility(llNotesHabit.VISIBLE);
                break;
            case R.id.btn_notes_select_target://选中“制定目标”按钮
                ResetView();
                EtNotes.setHint("请输入目标内容");
                t.setText("一共完成");
                NotesType="目标";
                BtnSelectTarget.setBackgroundColor(Color.parseColor("#FF4500"));
                llNotesTarget.setVisibility(llNotesTarget.VISIBLE);
                break;
            case R.id.btn_notes_target_time://设定目标时间
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {//这个函数是在跳出的日历选择器中日期被选中后调用，每选中一次就调用一次
                                mYear = year;
                                mMonth  = month;
                                mDay  = dayOfMonth;
                                DateOfSelect = mYear+"年"+(mMonth  + 1)+"月"+mDay+"日";
                                BtnTargetTime.setText(DateOfSelect);
                                IsSelectDate = true;
                            }
                        },
                        mYear, mMonth, mDay);
                datePickerDialog.setCanceledOnTouchOutside(false);
                datePickerDialog.show();
                break;
            case R.id.btn_notes_finish:
                SetNotesData();
                if(NotesType == "目标"){
                    if((NotesContent.length() > 0)&&(TotalTime.length() > 0)&&(IsSelectDate == true)){
                        SendNote();
                        dismiss();
                    }else {
                        //Toast.makeText(getActivity(),"缺少数据,请查看",Toast.LENGTH_LONG).show();
                        if(NotesContent.length() <= 0)
                            Toast.makeText(getActivity(),"内容为空，请输入",Toast.LENGTH_LONG).show();
                        else{
                            if(IsSelectDate == false)
                                Toast.makeText(getActivity(),"请选择日期",Toast.LENGTH_LONG).show();
                            else
                            if(TotalTime.length() <= 0)
                                Toast.makeText(getActivity(),"请输入时长",Toast.LENGTH_LONG).show();
                        }
                    }
                }
                else {
                    if((NotesContent.length() > 0)&&(TotalTime.length() > 0)){
                        SendNote();
                        dismiss();
                    }
                    else {
                        if(NotesContent.length() <= 0)
                            Toast.makeText(getActivity(),"内容为空，请输入",Toast.LENGTH_LONG).show();
                        else if (TotalTime.length() <= 0)
                            Toast.makeText(getActivity(),"请输入时长",Toast.LENGTH_LONG).show();

                    }
                }
                break;
            case R.id.btn_notes_exit:
                dismiss();
                break;
        }
    }
    public void ResetView(){
        BtnSelectHabit.setBackgroundColor(Color.parseColor("#FFB1B0B0"));
        BtnSelectTarget.setBackgroundColor(Color.parseColor("#FFB1B0B0"));
        llNotesHabit.setVisibility(llNotesHabit.GONE);
        llNotesTarget.setVisibility(llNotesTarget.GONE);
        EtWorkHours.setText("");
    }
    private void SetNotesData(){
        NotesContent = EtNotes.getText().toString();
        TotalTime =EtWorkHours.getText().toString();
        FinishDate[0] = mYear;
        FinishDate[1] = mMonth;
        FinishDate[2] = mDay;

    }
    private void SendNote(){
        Intent ResultIntent = new Intent();
        ResultIntent.putExtra(REQUESE,999);
        ResultIntent.putExtra(CONTENT,NotesContent);
        ResultIntent.putExtra(TYPE,NotesType);
        NotesType = "目标";//恢复默认
        ResultIntent.putExtra(FINISHDATE,FinishDate);
        ResultIntent.putExtra(TOTALTIME,TotalTime);
        ResultIntent.putExtra(UNITOFTIME,UnitOfTime);
        ResultIntent.putExtra(WORKFREQUENCY,WorkFrequency);
        getTargetFragment().onActivityResult(Fragment_notes.REUEST_CODDE, MainActivity.RESULT_OK, ResultIntent);
    }
}
