package com.example.studyapp;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class Fragment_mine extends Fragment {

    @BindView(R.id.qingtong1)
    ImageView qingtong1;
    @BindView(R.id.baiyin1)
    ImageView baiyin1;
    @BindView(R.id.huangjin1)
    ImageView huangjin1;
    @BindView(R.id.bojin1)
    ImageView bojin1;
    @BindView(R.id.zuanshi1)
    ImageView zuanshi1;
    @BindView(R.id.wangzhe1)
    ImageView wangzhe1;

    @BindView(R.id.qingtong2)
    TextView qingtong2;
    @BindView(R.id.baiyin2)
    TextView baiyin2;
    @BindView(R.id.huangjin2)
    TextView huangjin2;
    @BindView(R.id.bojin2)
    TextView bojin2;
    @BindView(R.id.zuanshi2)
    TextView zuanshi2;
    @BindView(R.id.wangzhe2)
    TextView wangzhe2;

    @BindView(R.id.biaoti1)
    EditText biaoti1;
    @BindView(R.id.queding)
    Button queding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, view);

        initData();
        return view;
    }

    private void initData() {

        queding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = biaoti1.getText().toString();
                if (string.equals("")) return;
                qingtong2.setTextColor(getResources().getColor(R.color.c7));
                baiyin2.setTextColor(getResources().getColor(R.color.c7));
                huangjin2.setTextColor(getResources().getColor(R.color.c7));
                bojin2.setTextColor(getResources().getColor(R.color.c7));
                zuanshi2.setTextColor(getResources().getColor(R.color.c7));
                wangzhe2.setTextColor(getResources().getColor(R.color.c7));

                qingtong1.setImageResource(R.drawable.qingtong2);
                baiyin1.setImageResource(R.drawable.baiyin2);
                huangjin1.setImageResource(R.drawable.huangjin2);
                bojin1.setImageResource(R.drawable.bojin2);
                zuanshi1.setImageResource(R.drawable.zuanshi2);
                wangzhe1.setImageResource(R.drawable.wangzhe2);

                int sdf = Integer.parseInt(string);
                if (sdf < 10) {
                    qingtong2.setTextColor(getResources().getColor(R.color.c1));
                    qingtong1.setImageResource(R.drawable.qingtong1);
                } else if (sdf < 20) {
                    baiyin2.setTextColor(getResources().getColor(R.color.c2));
                    baiyin1.setImageResource(R.drawable.baiyin1);
                } else if (sdf < 30) {
                    huangjin2.setTextColor(getResources().getColor(R.color.c3));
                    huangjin1.setImageResource(R.drawable.huangjin1);
                } else if (sdf < 40) {
                    bojin2.setTextColor(getResources().getColor(R.color.c4));
                    bojin1.setImageResource(R.drawable.bojin1);
                } else if (sdf < 50) {
                    zuanshi2.setTextColor(getResources().getColor(R.color.c5));
                    zuanshi1.setImageResource(R.drawable.zuanshi1);
                } else {
                    wangzhe2.setTextColor(getResources().getColor(R.color.c6));
                    wangzhe1.setImageResource(R.drawable.wangzhe1);
                }
            }
        });
    }
}
