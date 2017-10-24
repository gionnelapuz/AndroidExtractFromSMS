package com.example.gin.customlistview;

import android.Manifest;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static MainActivity inst;
    public static MainActivity instances()
    {
        return inst;
    }
    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    private static final int PERMISSION_REQUEST_CODE = 1;

    private static ListView listView, listViewDatabase;

    private static textDatabase db;

    private ArrayList<String> message;
    private customListview adapter;

    private static ArrayList<String> databaseNumber;
    private static ArrayList<String> databaseUsername;
    private static ArrayList<String> databaseAccountNumber;
    private static databaseListView adapters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new textDatabase(this);

        message = new ArrayList<>();

        databaseNumber = new ArrayList<>();
        databaseUsername = new ArrayList<>();
        databaseAccountNumber = new ArrayList<>();

        listView = (ListView) findViewById(R.id.listView);
        adapter = new customListview(MainActivity.this, message);
        listView.setAdapter(adapter);

        listViewDatabase = (ListView) findViewById(R.id.listViewDatabase);
        adapters = new databaseListView(MainActivity.this, databaseNumber, databaseUsername, databaseAccountNumber);
        listViewDatabase.setAdapter(adapters);

        checkPermission();
        showData();
    }


    public void refreshSmsInbox() {
        ContentResolver contentResolver = getContentResolver();
        Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);
        int indexBody = smsInboxCursor.getColumnIndex("body");

        if (indexBody < 0 || !smsInboxCursor.moveToFirst()) return;
        do {
            message.add(smsInboxCursor.getString(indexBody));
        } while (smsInboxCursor.moveToNext());
    }

    public void updateLists(String smsMessages) {
        message.add(0, smsMessages);

        adapter.notifyDataSetChanged();
    }


public static void showData() {

    databaseNumber.clear();
    databaseUsername.clear();
    databaseAccountNumber.clear();

    Cursor data = db.getSMS();
    if (data.getCount() == 0) {
    } else {
        data.moveToFirst();
        do {

            databaseNumber.add(data.getString(1));
            databaseUsername.add(data.getString(2));
            databaseAccountNumber.add(data.getString(3));

        } while (data.moveToNext());
    }
    data.close();

    adapters.notifyDataSetChanged();
}

    //PERMISSIONS
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_SMS) + ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECEIVE_SMS);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            requestPermission();
            return false;
        }
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_SMS)) {
            Toast.makeText(getApplicationContext(), "READ SMS PERMISSION ALLOWS TO RETRIEVE MESSAGES WITH THE KEYWORDS", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.RECEIVE_SMS,Manifest.permission.READ_SMS},PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    refreshSmsInbox();
                    Toast.makeText(MainActivity.this, "Permission Granted, Now you can see messages that have the keyword", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Permission Denied, You cannot see messages that have the keyword.", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
    }

