package com.stdio.incomeandexpenseplanner;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBExpenses  extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "expensesDb";
    public static final String TABLE_EXPENSES = "expenses";

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_DATE = "date";
    public static final String KEY_COST = "cost";
    public static final String KEY_MONTH = "month";

    public DBExpenses(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_EXPENSES + "(" + KEY_ID
                + " integer primary key,"  + KEY_NAME + " text," + KEY_COST + " text," + KEY_MONTH + " text," +  KEY_CATEGORY + " text," + KEY_DATE + " text" + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_EXPENSES);

        onCreate(db);

    }
}
