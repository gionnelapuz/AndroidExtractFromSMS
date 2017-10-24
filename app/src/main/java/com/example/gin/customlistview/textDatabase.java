package com.example.gin.customlistview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Gin on 8/16/2017.
 */

public class textDatabase extends SQLiteOpenHelper {

    public static final String DB_NAME = "smsDB";

    //CART TABLE
    public static final String SMSTABLE = "sms";
    public static final String SMSID = "id";
    public static final String SMSNUMBER ="number";
    public static final String SMSUSERNAME ="username";
    public static final String SMSACCOUNTNUMBER ="accountNumber";



    private static final int DB_VERSION = 1;

    public textDatabase(Context context)
    {
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String cart = "CREATE TABLE " + SMSTABLE + "(" +
                SMSID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SMSNUMBER + " VARCHAR, "
                + SMSUSERNAME + " VARCHAR, "
                + SMSACCOUNTNUMBER  + " VARCHAR);";

        db.execSQL(cart);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String cart = "DROP TABLE IF EXIST sms";
        db.execSQL(cart);
        onCreate(db);
    }

    //SMS FUNCTIONS
    public boolean addToTable(String number, String username, String accountNumber){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(SMSNUMBER,number);
        contentValues.put(SMSUSERNAME,username);
        contentValues.put(SMSACCOUNTNUMBER,accountNumber);

        long result = db.insert(SMSTABLE,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getSMS(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + SMSTABLE, null);
        return data;
    }

}
