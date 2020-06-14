package com.example.studyapp;



import android.graphics.drawable.AnimatedStateListDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Calendar;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class Fragment_mine extends Fragment {

    Calendar cal= Calendar.getInstance();//获取系统时间
    int year=cal.get(Calendar.YEAR);
    int month=cal.get(Calendar.MONTH)+1;

    @BindView(R.id.qt1)
    ImageView qt1;
    @BindView(R.id.by1)
    ImageView by1;
    @BindView(R.id.hj1)
    ImageView hj1;
    @BindView(R.id.bj1)
    ImageView bj1;
    @BindView(R.id.zs1)
    ImageView zs1;
    @BindView(R.id.wz1)
    ImageView wz1;

    @BindView(R.id.qt2)
    TextView qt2;
    @BindView(R.id.by2)
    TextView by2;
    @BindView(R.id.hj2)
    TextView hj2;
    @BindView(R.id.bj2)
    TextView bj2;
    @BindView(R.id.zs2)
    TextView zs2;
    @BindView(R.id.wz2)
    TextView wz2;

    @BindView(R.id.jifen1)
    TextView jifen1;
    @BindView(R.id.jifen2)
    TextView jifen2;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, view);
        Dao dao = new Dao(getContext());
        if (dao.judge0(year,month)==false) {
            jifen1.setText(String.valueOf(0));
            jifen2.setText(String.valueOf(50));
        }
        Double jifen = dao.getTdCredits(year, month);
        if (jifen < 50) {
            qt2.setTextColor(getResources().getColor(R.color.c1));
            qt1.setImageResource(R.drawable.qingtong1);
            jifen1.setText(String.valueOf(jifen));
            jifen2.setText(String.valueOf(50-jifen));
        } else if (jifen < 150) {
            by2.setTextColor(getResources().getColor(R.color.c2));
            by1.setImageResource(R.drawable.baiyin1);
            jifen1.setText(String.valueOf(jifen));
            jifen2.setText(String.valueOf(150-jifen));
        } else if (jifen < 300) {
            hj2.setTextColor(getResources().getColor(R.color.c3));
            hj1.setImageResource(R.drawable.huangjin1);
            jifen1.setText(String.valueOf(jifen));
            jifen2.setText(String.valueOf(300-jifen));
        } else if (jifen < 500) {
            bj2.setTextColor(getResources().getColor(R.color.c4));
            bj1.setImageResource(R.drawable.bojin1);
            jifen1.setText(String.valueOf(jifen));
            jifen2.setText(String.valueOf(500-jifen));
        } else if (jifen < 800) {
            zs2.setTextColor(getResources().getColor(R.color.c5));
            zs1.setImageResource(R.drawable.zuanshi1);
            jifen1.setText(String.valueOf(jifen));
            jifen2.setText(String.valueOf(800-jifen));
        } else {
            wz2.setTextColor(getResources().getColor(R.color.c6));
            wz1.setImageResource(R.drawable.wangzhe1);
            jifen1.setText(String.valueOf(jifen));
            jifen2.setText(String.valueOf("恭喜你已经到达最高段位"));
        }
        return view;
    }

}
