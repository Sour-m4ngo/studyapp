package com.example.studyapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

//操作数据库的增删改查
public class Dao {

    private static final String TAG = "Dao";
    private final DatabaseHelper mHelper;

    public Dao(Context context) {
        //创建数据库
        mHelper = new DatabaseHelper(context);

    }


    //插入信息
    public void insert(int year, int month, int day, int time) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
          /*String sql="insert into "+ Constants.TABLE_NAME +"(date,time,times,phone) values(?,?,?,?)";
          db.execSQL(sql,new Object[]{"2020.3.9",50,1});*/

        ContentValues values = new ContentValues();
        //添加数据
        values.put("Years", year);
        values.put("Months", month);
        values.put("Days", day);
        values.put("time", time);
        values.put("times", 1);
        db.insert(Constants.TABLE_NAME, null, values);
        db.close();
    }

    //删除信息
    public void delete() {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String sql = "delete from " + Constants.TABLE_NAME + " where " + "Days=" + "33";
        db.execSQL(sql);

        //db.delete(Constants.TABLE_NAME,null,null);
        db.close();
    }

    //更新
    public void update(int year, int month, int day, int t) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        //修改当天专注时间 time=time+t
        String sql1 = "update " + Constants.TABLE_NAME + " set time=" + "time+" + t + " where Years=" + year + " and Months=" + month + " and Days=" + day;
        db.execSQL(sql1);
        //修改当天专注次数times+1
        String sql2 = "update " + Constants.TABLE_NAME + " set times=" + "times+" + 1 + " where Years=" + year + " and Months=" + month + " and Days=" + day;
        db.execSQL(sql2);
        db.close();
          /*
          ContentValues values =new ContentValues();
          values.put("time",60);
          db.update(Constants.TABLE_NAME,values,null,null);*/

    }


    public void recCtnDays(int year, int month, int day) {  //修改数据库学习天数为1
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String sql1 = "update " + Constants.TABLE_NAME + " set CtnDays=" + 1 + " where Years=" + year + " and Months=" + month + " and Days=" + day;
        db.execSQL(sql1);
        Log.d(TAG, "日期" + day);
        db.close();
    }

    public void addCtnDays(int year, int month, int day, int ctnDays) {     //修改数据库学习天数+1
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ctnDays = ctnDays + 1;
        String sql1 = "update " + Constants.TABLE_NAME + " set CtnDays=" + ctnDays + " where Years=" + year + " and Months=" + month + " and Days=" + day;
        db.execSQL(sql1);
        db.close();
    }



    //查询
    public boolean query(int year, int month, int day) {
        Log.d(TAG, "年月日" + year + " " + month + " " + day);
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String sql = "Select * from " + Constants.TABLE_NAME + " where Years=" + year + " and Months=" + month + " and Days=" + day;
        //String sql="select * from "+ Constants.TABLE_NAME +" where Years=year and Months=month and Days=day" ;
        Cursor cursor = db.rawQuery(sql, null);
        Log.d(TAG, "数据项数" + cursor.getCount());
        if (cursor.getCount() > 0) {
            cursor.close();
            db.close();
            return true;
        } else {
            cursor.close();
            db.close();
            return false;
        }
    }
    public boolean isCtnDays(int year, int month, int day) {    //判断某一天是否有在学习
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String sql = "Select * from " + Constants.TABLE_NAME + " where Years=" + year + " and Months=" + month + " and Days=" + day;
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            if (cursor.getInt(cursor.getColumnIndex("CtnDays")) > 0) {
                cursor.close();
                db.close();
                return true;
            } else {
                cursor.close();
                db.close();
                return false;
            }
        }
        else{
            return false;
        }
    }
    public boolean isAddCtn(int year, int month, int day) {     //判断某一天是否满足计入学习天数+1条件（当日学习时间不少于30分钟）
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String sql = "Select * from " + Constants.TABLE_NAME + " where Years=" + year + " and Months=" + month + " and Days=" + day;
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            if (cursor.getInt(cursor.getColumnIndex("time")) >= 30) {
                cursor.close();
                db.close();
                return true;
            } else {
                cursor.close();
                db.close();
                return false;
            }
        }
        else{
            return false;
        }
    }
    //获取数据进行显示
    public void getdata(ArrayAdapter<String> adapter) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        adapter.clear();//清空adapter的内容，避免更新重复的内容
        //查询student表中所有数据
        //查询到的数据都将从Cursor对象取出
        Cursor cursor = db.query(false, Constants.TABLE_NAME, null, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {//遍历对象
            do {
                //向适配器中添加数据

                String date, time;
                date = "日期：" + cursor.getString(cursor.getColumnIndex("Years")) + "." + cursor.getString(cursor.getColumnIndex("Months")) + "." + cursor.getString(cursor.getColumnIndex("Days"));
                time = "专注时间：" + cursor.getString(cursor.getColumnIndex("time")) + "分钟" + "   当日专注次数：" + cursor.getString(cursor.getColumnIndex("times")) + "次";
                adapter.add(date);
                adapter.add(time);
                //adapter.add(cursor.getString(cursor.getColumnIndex("Days")));
                //adapter.add(cursor.getString(cursor.getColumnIndex("time")));
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    public int getCtnDays(int year, int month, int day) {   //获取某天连续学习天数
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String sql = "Select * from " + Constants.TABLE_NAME +" where Years=" + year + " and Months=" + month + " and Days=" + day;
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        int Ctn = cursor.getInt(cursor.getColumnIndex("CtnDays"));
        cursor.close();
        //Log.d(TAG, "数据项数" + cursor.getCount());
        return  Ctn;
    }

    //获取本周数据进行显示
    public void getdataW(ArrayAdapter<String> adapter) {
        Calendar cal = Calendar.getInstance();
        int wekday = cal.get(Calendar.DAY_OF_WEEK);//周日为1,周一为2
        cal.add(Calendar.DAY_OF_MONTH, -(wekday - 1));//获取本周一时间
        SQLiteDatabase db = mHelper.getWritableDatabase();
        adapter.clear();//清空adapter的内容，避免更新重复的内容

        int sumtime = 0, sumtimes = 0;
        for (int i = 0; i < 7; i++)//存储周数据
        {
            cal.add(Calendar.DAY_OF_MONTH, 1);//每循环一次，日期加一
            int year = cal.get(Calendar.YEAR);//获取本周一的年份
            int month = cal.get(Calendar.MONTH) + 1;//月份
            int day = cal.get(Calendar.DATE);//日期
            String selection = "Years=" + year + " and" + " Months=" + month + " and" + " Days=" + day;
            Cursor cursor = db.query(false, Constants.TABLE_NAME, null, selection, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                //有学习记录，进行记录
                sumtime = cursor.getInt(cursor.getColumnIndex("time"));
                sumtimes = cursor.getInt(cursor.getColumnIndex("times"));

            } else//无学习记录，专注时间，专注次数记为0
            {
                sumtime = 0;
                sumtimes = 0;
            }
            String date, time;
            date = "日期：" + year + "." + month + "." + day;
            time = "专注时间：" + sumtime + "分钟" + "   专注次数：" + sumtimes + "次";
            switch (i%7){
                case 0:
                    date += "    周一";
                    break;
                case 1:
                    date += "    周二";
                    break;
                case 2:
                    date += "    周三";
                    break;
                case 3:
                    date += "    周四";
                    break;
                case 4:
                    date += "    周五";
                    break;
                case 5:
                    date += "    周六";
                    break;
                case 6:
                    date += "    周日";
                    break;
                default:
                    System.out.println(0);
            }
            adapter.add(date);
            adapter.add(time);
            cursor.close();
        }
    }

    //获得本周数据制作折线图
    public void getLviewW(List<String> xlist, List<Float> ylist) {
        Calendar cal = Calendar.getInstance();
        int wekday = cal.get(Calendar.DAY_OF_WEEK);//周日为1,周一为2
        cal.add(Calendar.DAY_OF_MONTH, -(wekday - 1));//获取本周一时间
        SQLiteDatabase db = mHelper.getWritableDatabase();
        float sumtime = 0;
        String date = null;
        for (int i = 0; i < 7; i++)//存储周数据
        {
            cal.add(Calendar.DAY_OF_MONTH, 1);//每循环一次，日期加一
            int year = cal.get(Calendar.YEAR);//获取本周一的年份
            int month = cal.get(Calendar.MONTH) + 1;//月份
            int day = cal.get(Calendar.DATE);//日期
            String selection = "Years=" + year + " and" + " Months=" + month + " and" + " Days=" + day;
            Cursor cursor = db.query(false, Constants.TABLE_NAME, null, selection, null, null, null, null, null);
            if (cursor.moveToFirst()) //有学习记录，进行记录
                sumtime = cursor.getFloat(cursor.getColumnIndex("time"));
            else//无学习记录，专注时间记为0
                sumtime = 0;

            switch (i%7){
                case 0:
                    date = "周一";
                    break;
                case 1:
                    date = "周二";
                    break;
                case 2:
                    date = "周三";
                    break;
                case 3:
                    date = "周四";
                    break;
                case 4:
                    date = "周五";
                    break;
                case 5:
                    date = "周六";
                    break;
                case 6:
                    date = "周日";
                    break;
                default:
                    System.out.println(0);
            }
            xlist.add(date);
            ylist.add(sumtime);
            cursor.close();
        }
    }

    //获取当月数据进行显示
    public void getdataM(ArrayAdapter<String> adapter, int m) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        adapter.clear();//清空adapter的内容，避免更新重复的内容
        //查询student表中所有数据
        //查询到的数据都将从Cursor对象取出
        String selection = "Months=" + m;//选择当月记录
        String orderBy = "Days";//按天排序
        Cursor cursor = db.query(false, Constants.TABLE_NAME, null, selection, null, null, null, orderBy, null);
        if (cursor.moveToFirst()) {//遍历对象
            do {
                //向适配器中添加数据
                String date, time;
                date = "日期：" + cursor.getString(cursor.getColumnIndex("Years")) + "." + cursor.getString(cursor.getColumnIndex("Months")) + "." + cursor.getString(cursor.getColumnIndex("Days"));
                time = "专注时间：" + cursor.getString(cursor.getColumnIndex("time")) + "分钟" + "   当日专注次数：" + cursor.getString(cursor.getColumnIndex("times")) + "次";
                adapter.add(date);
                adapter.add(time);
                //adapter.add(cursor.getString(cursor.getColumnIndex("Days")));
                //adapter.add(cursor.getString(cursor.getColumnIndex("time")));
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    //获取当月数据制作折线图
    public void  getLviewM(List<String> xlist, List<Float> ylist){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        Calendar cal = Calendar.getInstance();
        int sumday=cal.getActualMaximum(Calendar.DAY_OF_MONTH);//获取当月天数
        int y=cal.get(Calendar.YEAR);
        int m=cal.get(Calendar.MONTH)+1;
        float sumtime = 0;
        String date = null;
        for (int i = 1; i <= sumday; i++)//依次查找当月每一天的数据记录
        {
            sumtime = 0;
            date = null;
            String selection = "Years=" + y + " and" + " Months=" + m+" and"+ " Days="+(i);
            Cursor cursor = db.query(false, Constants.TABLE_NAME, null, selection, null, null, null, null, null);
            if (cursor.moveToFirst()) //遍历对象 有学习记录，进行汇总，计算当月总专注时间及次数
            {
                    sumtime = cursor.getInt(cursor.getColumnIndex("time"));
            }
            else//无学习记录，专注时间，专注次数记为0
            {
                    sumtime = 0;
            }
            switch(i){
                case 1:
                    date = String.valueOf(i);
                    break;
                case 8:
                    date = String.valueOf(i);
                    break;
                case 15:
                    date = String.valueOf(i);
                    break;
                case 23:
                    date = String.valueOf(i);
                    break;
                default:
                    date = " ";
                    break;
            }
            if(i==sumday) date = String.valueOf(i);//最后一天也标x
            xlist.add(date);
            ylist.add(sumtime);
            cursor.close();
        }
    }
    //获取本年数据，以月为单位进行展示
    public void getdataY(ArrayAdapter<String> adapter, int y) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        adapter.clear();//清空adapter的内容，避免更新重复的内容
        //查询student表中所有数据
        //查询到的数据都将从Cursor对象取出

        int sumtime = 0, sumtimes = 0;
        for (int i = 0; i < 12; i++)//依次存储每月数据
        {
            //每个月汇总数据清零
            sumtime = 0;
            sumtimes = 0;
            String selection = "Years=" + y + " and" + " Months=" + (i + 1);
            Cursor cursor = db.query(false, Constants.TABLE_NAME, null, selection, null, null, null, null, null);
            if (cursor.moveToFirst()) {//遍历对象 有学习记录，进行汇总，计算当月总专注时间及次数
                do {
                    sumtime += cursor.getInt(cursor.getColumnIndex("time"));
                    sumtimes += cursor.getInt(cursor.getColumnIndex("times"));
                } while (cursor.moveToNext());
            } else//无学习记录，专注时间，专注次数记为0
            {
                sumtime = 0;
                sumtimes = 0;
            }
            String date, time;
            date = "月份：" + (i + 1) + "月";
            time = "专注时间：" + sumtime + "分钟" + "   专注次数：" + sumtimes + "次";
            adapter.add(date);
            adapter.add(time);
            cursor.close();
        }
    }

    //获取本年数据制作折线图
    public void getLviewY(List<String> xlist, List<Float> ylist, int y) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        float sumtime = 0;
        String date = null;
        for (int i = 0; i < 12; i++)//依次存储每月数据
        {
            //每个月汇总数据清零
            sumtime = 0;
            String selection = "Years=" + y + " and" + " Months=" + (i + 1);
            Cursor cursor = db.query(false, Constants.TABLE_NAME, null, selection, null, null, null, null, null);
            if (cursor.moveToFirst()) {//遍历对象 有学习记录，进行汇总，计算当月总专注时间及次数
                do {
                    sumtime += cursor.getInt(cursor.getColumnIndex("time"));
                    date = String.valueOf(i + 1);
                } while (cursor.moveToNext());
            } else//无学习记录，专注时间，专注次数记为0
            {
                sumtime = 0;
                date = String.valueOf(i + 1);
            }
            xlist.add(date);
            ylist.add(sumtime);
            cursor.close();
        }
    }

    public void SaveNoteData (Notes note){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Type",note.getType());//待办类型
        values.put("Content",note.getNotesContent());//待办内容
        values.put("Totaltime",note.getTotalTime());//总时长
        values.put("UnitOfTime",note.getUnitOfTime());
        values.put("HaveFinishedtime",note.getHaveFinishMinutes());//剩余时长，包括习惯中单次剩余时长和目标中的总剩余时长
//        values.put("FnlYears",note.);
//        values.put("FnlMonths",note.);
//        values.put("FnlDays",note.);
        if (note.getType().equals("目标")){//截止时间
            values.put("FinishYears",note.getFinishDate()[0]);//年份
            values.put("FinishMonths",note.getFinishDate()[1]);//月份
            values.put("FinishDays",note.getFinishDate()[2]);//日期
        }else {
            values.put("Frequency",note.getWorkFrequency());
        }
        db.insert(Constants.TO_DO_ITEM, null, values);

    }
    public ArrayList<Notes> getNoteData(){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ArrayList<Notes> backNote = new ArrayList<Notes> ();
        Cursor cursor = db.query(true,Constants.TO_DO_ITEM,null,null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do{
                String Type = cursor.getString(cursor.getColumnIndex("Type"));
                String Content = cursor.getString(cursor.getColumnIndex("Content"));
                int Totaltime = cursor.getInt(cursor.getColumnIndex("Totaltime"));
                String UnitOfTime = cursor.getString(cursor.getColumnIndex("UnitOfTime"));
                int HaveFinishedtime = cursor.getInt(cursor.getColumnIndex("HaveFinishedtime"));
                Log.d(TAG, "aaaaaaaaaaaaaaaaaaaaaaaaaaaa"+Type);
                if (Type.equals("目标")){
                    int FinishYears = cursor.getInt(cursor.getColumnIndex("FinishYears"));
                    int FinishMonths = cursor.getInt(cursor.getColumnIndex("FinishMonths"));
                    int FinishDays = cursor.getInt(cursor.getColumnIndex("FinishDays"));
                    int [] finishDate = new int[]{FinishYears,FinishMonths,FinishDays};//完成日期，年月日
                    Log.d(TAG, "ttttttttttttttttttttttttt"+Content+finishDate[0]+finishDate[1]+finishDate[2]);
                    //String  Content,String type,int totalTime,String unitOfTime,int haveFinishMinutes,int[] finishDate
                    Notes note = new Notes(Content,Type,Totaltime,UnitOfTime,HaveFinishedtime,finishDate);
                    backNote.add(note);
                }else {
                    String Frequency = cursor.getString(cursor.getColumnIndex("Frequency"));
                    Notes note = new Notes(Content,Type,Totaltime,UnitOfTime,HaveFinishedtime,Frequency);
                    backNote.add(note);
                }
            }while (cursor.moveToNext());
        }//ooooooooooooo
        cursor.close();
        return backNote;
    }
}
