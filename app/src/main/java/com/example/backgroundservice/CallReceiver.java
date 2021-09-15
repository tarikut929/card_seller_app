    package com.example.backgroundservice;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import static java.lang.Integer.valueOf;

public class CallReceiver extends BroadcastReceiver {
    Db_controller db_controller;
    String[] phonrArray = new String[200];
    String number;
    Boolean is_registerd = false;



    //The receiver will be recreated whenever android feels like it.  We need a static variable to remember data between instantiations

    private static int lastState = TelephonyManager.CALL_STATE_IDLE;
    private static Date callStartTime;
    private static boolean isIncoming;
    private static String savedNumber;  //because the passed incoming is only valid in ringing

    public CallReceiver() {
    }



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {


        Integer callingSIM = 5;
        Bundle bundle = intent.getExtras();
        callingSIM =valueOf(bundle.getInt("simId", -1));
        if(callingSIM == 0){
           // Toast.makeText(context, "Incoming call from SIM1", Toast.LENGTH_SHORT).show();
            // Incoming call from SIM1
        }
        else if(callingSIM == 1){
           // Toast.makeText(context, "Incoming call from SIM2", Toast.LENGTH_SHORT).show();
        }else{
           // Toast.makeText(context, "not Working nw "+callingSIM, Toast.LENGTH_SHORT).show();
        }





        //We listen to two intents.  The new outgoing call only tells us of an outgoing call.  We use it to get the number.
        if (intent.getAction().equals("android.intent.action.NEW_OUTGOING_CALL")) {
            savedNumber = intent.getExtras().getString("android.intent.extra.PHONE_NUMBER");
                  }
        else{

            String stateStr = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);
            number =  intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            db_controller = new Db_controller(context,"",null,1);
            //Check the number and the amount in the database

           // rechargeAccount(context);

            int state = 0;
             if(stateStr.equals(TelephonyManager.EXTRA_STATE_RINGING)){
                state = TelephonyManager.CALL_STATE_RINGING;
                 Toast.makeText(context, "RInging NOw", Toast.LENGTH_SHORT).show();

                db_controller.insertIsRing(1);
                // AddUserActivity addUserActivity = new Ad  dUserActivity();
                //  addUserActivity.nregiserUser();
                // db_controller.insertUserHistory(5,"date 20/34/12","this is string");
             //   Toast.makeText(context, "Bilbill waama jira "+number, Toast.LENGTH_SHORT).show();
            }

            else if(stateStr.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
                state = TelephonyManager.CALL_STATE_OFFHOOK;
                db_controller.insertIsRecieved(1);
                 Toast.makeText(context, "RInging OfHok", Toast.LENGTH_SHORT).show();


             }else if(stateStr.equals(TelephonyManager.EXTRA_STATE_IDLE)){
                 state = TelephonyManager.CALL_STATE_IDLE;
                 int isRing = db_controller.getIsRing();
                 int isRecieved  = db_controller.getIsRecieved();

                 if(isRing==1&&isRecieved==0)
                 {
                     rechargeAccount(context);
                     Log.d("EEEE"," "+isRing+"c "+isRecieved);
                 }

             }



//            if(ring==true&&callReceived==false)
//            {
//                rechargeAccount(context);
//            }
//            //rechargeAccount(context);

           onCallStateChanged(context, state, number,intent);
        }

    }
    public void rechargeAccount(Context context){
        try {
            Log.d("TTTTTTTT"," 0");
            db_controller = new Db_controller(context, "", null, 1);
            Log.d("TTTTTTTT"," 1 ");
            db_controller.listAllUserPhone(phonrArray);
            Log.d("TTTTTTTT"," 2");
            for (int i = 0; i < phonrArray.length; i++) {
                Log.d("TTTTTTTT"," 3");
                String number_from_array = phonrArray[i];
                Log.d("TTTTTTTT"," 4 number from array "+number_from_array+" number "+number);
                //
                if (number_from_array.equals(number)) {
                    Log.d("TTTTTTTT"," 5");
                    String cardAmaount = db_controller.cardAmountToRecharge(number);
                    Log.d("TTTTTTTT"," 6");
                    db_controller.saveCallerNumber(number, cardAmaount);
                    Log.d("TTTTTTTT"," 7");
                    String currentDate = Calendar.getInstance().getTime().toString();
                    Log.d("TTTTTTTT"," 8");
                    DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                    Log.d("TTTTTTTT"," 9");
                    String date = df.format(Calendar.getInstance().getTime());
                    Log.d("TTTTTTTT"," "+date);
                    db_controller.insertUserHistory(cardAmaount,date,number);
                    Log.d("TTTTTTTT"," 10");

                    is_registerd = true;
                    break;
                }
            }
        }catch (Exception e){
            Log.d("DDDD","Exception handled "+e);

        }
    }


    //Derived classes should override these to respond to specific events of interest
    protected void onIncomingCallStarted(Context ctx, String number, Date start){}
    protected void onOutgoingCallStarted(Context ctx, String number, Date start){}
    protected void onIncomingCallEnded(Context ctx, String number, Date start, Date end){}
    protected void onOutgoingCallEnded(Context ctx, String number, Date start, Date end){}
    protected void onMissedCall(Context ctx, String number, Date start){

    }

    //Deals with actual events

    //Incoming call-  goes from IDLE to RINGING when it rings, to OFFHOOK when it's answered, to IDLE when its hung up
    //Outgoing call-  goes from IDLE to OFFHOOK when it dials out, to IDLE when hung up
    public void onCallStateChanged(Context context, int state, String number, Intent intent) {
        int lastStateTemp = lastState;
        lastState = state;
        if(lastStateTemp == state){
            //No change, debounce extras
            return;
        }
        switch (state) {
            case TelephonyManager.CALL_STATE_RINGING:

                isIncoming = true;
                callStartTime = new Date();

                String number1 = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
                String number2 = intent.getStringExtra("android.intent.extra.PHONE_NUMBER");
                savedNumber = number;
                onIncomingCallStarted(context, number, callStartTime);
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                //Transition of ringing->offhook are pickups of incoming calls.  Nothing done on them
                if(lastStateTemp != TelephonyManager.CALL_STATE_RINGING){
                    isIncoming = false;
                    callStartTime = new Date();
                    onOutgoingCallStarted(context, savedNumber, callStartTime);
                }
                break;
            case TelephonyManager.CALL_STATE_IDLE:
                //Went to idle-  this is the end of a call.  What type depends on previous state(s)
                if(lastStateTemp == TelephonyManager.CALL_STATE_RINGING){
                    try {
                        if(is_registerd) {
                            //Ring but no pickup-  a miss
                            onMissedCall(context, savedNumber, callStartTime);
                            CallAssist callAssist = new CallAssist(context);
                            callAssist.docallAssist();
                        }
                    }catch (Exception e){

                    }

                }
                else if(isIncoming){
                    onIncomingCallEnded(context, savedNumber, callStartTime, new Date());
                }
                else{
                    onOutgoingCallEnded(context, savedNumber, callStartTime, new Date());
                }
                break;
        }
        lastState = state;
    }
}

