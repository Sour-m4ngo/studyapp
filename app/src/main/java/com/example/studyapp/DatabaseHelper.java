package com.example.studyapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG ="DatabaseHelper";

    public DatabaseHelper(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.VERSION_CODE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       //创建时的回调
        Log.d(TAG,"创建数据库...");
        //创建字段
        //sql: create table table_name(date varchar,time integer,times integer);
        String sql1 = "create table "+ Constants.TABLE_NAME+"(Years integer,Months integer,Days integer,time integer,times integer,CtnDays integer,iscal integer,Credits double)";
        String sql2 = "create table "+ Constants.TO_DO_ITEM+"(Type string,Content string,Totaltime integer,HaveFinishedtime integer,FinishYears integer,FinishMonths integer,FinishDays integer,Frequency string,UnitOfTime string)";
        //FnlYears integer,FnlMonths integer,FnlDays integer,
        db.execSQL(sql1);
        db.execSQL(sql2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       //升级数据库时的回调
        Log.d(TAG,"升级数据库...");

        // sql: alter table table_name add phone integer;
        String sql;

       /*
        switch (oldVersion){
            case 1:
                //添加phone字段和address字段
                sql="alter table "+Constants.TABLE_NAME+" add phone integer";
                db.execSQL(sql);
                sql="alter table "+Constants.TABLE_NAME+" add address varchar";
                db.execSQL(sql);
                break;
            case 2:
                //添加address字段
                sql="alter table "+Constants.TABLE_NAME+" add address varchar";
                db.execSQL(sql);
                break;
        }*/
    }
}
