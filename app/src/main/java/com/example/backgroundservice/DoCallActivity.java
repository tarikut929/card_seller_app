package com.example.backgroundservice;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.app.role.RoleManager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


public class DoCallActivity extends AppCompatActivity {

    ///////////////////////////////////==================

    ///////////////////////////////========================

    Db_controller db_controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_call);
       makePhoneCall();
        this.finish();

    }
    public void makePhoneCall() {
        Log.d("TTTT","amount: ");

        CallReceiver callReceiver = new CallReceiver();
        db_controller = new Db_controller(this,"",null,1);
        String[] num_and_am = new String[2];

        num_and_am = db_controller.showMissedcallNumber();
        //String num = callReceiver.dailNumber;

                  ///===================decomment================
//        if (num_and_am[0].trim().length() > 0) {
//
//            if (ContextCompat.checkSelfPermission(this,
//                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
//            } else {
//                //amount is num_and_am[0]
//
//               // String ussd = "*804" + Uri.encode("#");
//               // startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + ussd)));
//                String dial = "tel:" + num_and_am[0];
//                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
//            }
//
//        } else {
//            Toast.makeText(this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
//        }
    }
}


