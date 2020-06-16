package com.example.studyapp.BBL;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.studyapp.DAL.Dao;
import com.example.studyapp.R;
import com.example.studyapp.UI.LineView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Fragment_record extends Fragment {
    private ArrayAdapter<String> adapter;
    private Button mBtnButton1;
    private Button mBtnButton2;
    private Button mBtnButton3;
    private LineView headView1;
    private ListView listView;
    private Calendar cal=Calendar.getInstance();//获取系统时间
    private int year=cal.get(Calendar.YEAR);
    private int month=cal.get(Calendar.MONTH)+1;
    public Fragment_record() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        intiView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_record, container, false);
        adapter=new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1);//创建适配器对象
        listView = view.findViewById(R.id.lv1);
        headView1= view.findViewById(R.id.lineView) ;
        listView.setAdapter(adapter);
        intiView();
        //点击事件rbt1
        mBtnButton1 = view.findViewById(R.id.rbt1);
        mBtnButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listView.setAdapter(null);
                adapter=new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1);//创建适配器对象
                listView.setAdapter(adapter);
                Dao dao1=new Dao(getContext());//创建Dao对象
                dao1.getdataW(adapter);//获取周专注数据
                //定义xy坐标
                List<String> xList1=new ArrayList<String>(){} ;
                List<Float> yList1=new ArrayList<Float>(){};
                dao1.getLviewW(xList1,yList1);
                headView1.setData(xList1,yList1);
            }
        });

        //点击事件rbt2
        mBtnButton2 =view.findViewById(R.id.rbt2);
        mBtnButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listView.setAdapter(null);
                adapter=new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1);//创建适配器对象
                listView.setAdapter(adapter);
                Dao dao2=new Dao(getContext());//创建Dao对象
                dao2.getdataM(adapter,month);//获取月专注数据
                //定义xy坐标
                List<String> xList2=new ArrayList<String>(){} ;
                List<Float> yList2=new ArrayList<Float>(){};
                dao2.getLviewM(xList2,yList2);
                headView1.setData(xList2,yList2);
            }
        });

        //点击事件rbt3
        mBtnButton3 =view.findViewById(R.id.rbt3);
        mBtnButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listView.setAdapter(null);//清空listView
                adapter=new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1);//创建适配器对象
                listView.setAdapter(adapter);
                Dao dao3=new Dao(getContext());//创建Dao对象
                dao3.getdataY(adapter,year);//获取年专注数据
                //定义xy坐标
                List<String> xList3=new ArrayList<String>(){} ;
                List<Float> yList3=new ArrayList<Float>(){};
                dao3.getLviewY(xList3,yList3,year);
                headView1.setData(xList3,yList3);
            }
        });
        return view;
    }
    private void intiView (){
        Dao dao=new Dao(getContext());//创建Dao对象
        //初始界面显示
        listView.setAdapter(null);
        adapter=new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1);//创建适配器对象
        listView.setAdapter(adapter);
        dao.getdataW(adapter);//获取周专注数据
        //定义xy坐标
        List<String> xList1=new ArrayList<String>(){} ;
        List<Float> yList1=new ArrayList<Float>(){};
        dao.getLviewW(xList1,yList1);
        headView1.setData(xList1,yList1);
    }
}
