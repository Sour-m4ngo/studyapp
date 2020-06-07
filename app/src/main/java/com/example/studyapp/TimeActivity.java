package com.example.studyapp;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class TimeActivity extends AppCompatActivity {

    private TextView tv_Timer;
    Calendar cal= Calendar.getInstance();//获取系统时间
    int year=cal.get(Calendar.YEAR);
    int month=cal.get(Calendar.MONTH)+1;
    int day=cal.get(Calendar.DATE);
    int wekday=cal.get(Calendar.DAY_OF_WEEK);
    double credits=0;
    private Handler handlerp = new Handler(){
        public void handleMessage(Message msg) {
            if (msg.what==1)
                finish();
        }
    };

    //监听返回键
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        //Toast.makeText(TimeActivity.this,"任务终止，扣除相应积分", Toast.LENGTH_SHORT).show();
        //onDestroy();
        Dao dao=new Dao(getApplicationContext());
        credits = dao.getPreCredits(year, month);//获取截至上一次学习为止的当月积分
        if(dao.isfisrtRecord(year,month)) //是本月第一条记录，直接修改本条记录中的积分
        {
            dao.minusCredits(year,month,day,credits-3);//修改本日积分
        }
        else //本月有上一次学习记录，修改上一次学习记录
        {
            dao.minuspreCredits(year,month,credits-3);//修改上次学习积分
            double tdCredits = dao.getTdCredits(year, month);//获得当日积分
            if(tdCredits == 0){//今日尚未学习，用前日积分覆盖今日积分
                double ytdCredits = dao.getYtdCredits(year, month);
                dao.setCredits(year, month, day, ytdCredits);
            }
            else{
                dao.setCredits(year, month, day, tdCredits - 3);
            }
        }
        System.exit(0);
    }

    int time = 0;
    int timeFromNote = 0 ;
    int timeFromTomato = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        //获取用户输入的专注时间
        tv_Timer = findViewById(R.id.tv_timer1);
        Intent intent = getIntent();
        Bundle bundle= intent.getExtras();
        Dao dao=new Dao(getApplicationContext());

        //int type = bundle.getInt("KET_TYPE");
        timeFromNote = bundle.getInt("DURATION");
        timeFromTomato = bundle.getInt("num");
        //Toast.makeText(TimeActivity.this,"asda"+timeFromNote,Toast.LENGTH_SHORT).show();
        if(timeFromTomato == 0){
            time = timeFromNote;
        }else {
            time = timeFromTomato;
        }

        if(!dao.query(year,month,day))//今日无记录，初始化今日学习记录
        {
            dao.insert(year,month,day);
            Log.d("初始化","已经初始化今日学习记录");
        }

        time = time *1000;
        final long finalTime = time/1000;
        if(time != 0){
            new CountDownTimer(time,1000) {
                @Override
                public void onTick(long t) {
                    long hour=t/(1000*60*60);
                    long minute=(t-hour*(1000*60*60))/(1000*60);
                    long second=(t-hour*(1000*60*60)-minute*(1000*60))/1000;
                    tv_Timer.setText("剩余" + hour+"小时" + minute + "分钟" +second + "秒");
                }
                @Override
                public void onFinish() {
                    Toast.makeText(TimeActivity.this,"您已成功完成专注计划", Toast.LENGTH_SHORT).show();
                    dao.update(year,month,day,(int)finalTime);//更新学习记录

                    //查询是否连续学习连续学习则天数+1
                    if(dao.isAddCtn(year, month, day))   //是否学习时间超过30min
                    {
                        if(day==1) //本月第一天
                        {
                            dao.recCtnDays(year,month,day); //重置
                        }
                        else if(dao.isCtnDays(year, month, day - 1)) //前一天学习，今日学习天数加一
                        {
                            int Ctndays = dao.getCtnDays(year, month, day-1);
                            dao.addCtnDays(year,month,day, Ctndays);
                        }
                        else  //前一天无学习记录，重置
                            {
                            dao.recCtnDays(year,month,day);
                        }
                    }
                    int time = dao.getTime(year, month, day);//获取当日学习时长
                    credits = dao.getPreCredits(year, month);//获取截至上一次学习为止的当月积分
                    int totaltimes = time / 30;//触发单日增加积分的次数
                    int ctndays = dao.getCtnDays(year, month, day);//获取连续学习天数
                    credits =dao.calCredits(credits, ctndays, totaltimes);//计算变化后的当月积分
                    Log.d("Credit","积分为"+credits);
                    dao.setCredits(year, month, day, credits);//记录当月积分
                    Message msg = new Message();
                    msg.what= 1;
                    handlerp.sendMessage(msg);
                }

            }.start();
        }


    }


}
