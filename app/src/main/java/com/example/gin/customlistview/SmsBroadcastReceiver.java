package com.example.gin.customlistview;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Gin on 8/14/2017.
 */

public class SmsBroadcastReceiver extends BroadcastReceiver {

    public static final String SMS_BUNDLE = "pdus";

    private String username, accountNumber;

    public void onReceive(Context context, Intent intent) {

        final textDatabase db = new textDatabase(context);

        Bundle intentExtras = intent.getExtras();
        if (intentExtras != null) {
            Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
            String smsNumber = "";
            String smsMessages = "";
            for (int i = 0; i < sms.length; ++i) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);

                String number = smsMessage.getOriginatingAddress();
                String smsBody = smsMessage.getMessageBody().toString();

                smsNumber += number;
                smsMessages += smsBody;

                if(smsMessages.contains("KEYWORDMESSAGE")){

                    Pattern p = Pattern.compile("^Your KEYWORDMESSAGE username is (.*) and account number is (\\d+)$");
                    Matcher m = p.matcher(smsMessages.toString());
                    m.find();
                    username = m.group(1);
                    accountNumber = m.group(2);

                    db.addToTable(number,username,accountNumber);
                    db.close();

                    MainActivity.showData();
                }
            }

            MainActivity insts = MainActivity.instances();
            insts.updateLists(smsMessages);
        }
    }
}
