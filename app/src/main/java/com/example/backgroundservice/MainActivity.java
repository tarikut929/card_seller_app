package com.example.backgroundservice;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.role.RoleManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //Reference Db_Controller class
    Db_controller db_controller;

    //Edit TExt
    EditText firstname,lastname;
    TextView textView;

    //button objects
    private Button buttonStart;
    private Button buttonStop;

    private static final int REQUEST_ID = 1;
    @RequiresApi(Build.VERSION_CODES.Q)
    public void requestRole() {
        try {


            RoleManager roleManager = (RoleManager) getSystemService(ROLE_SERVICE);
            Intent intent = roleManager.createRequestRoleIntent(RoleManager.ROLE_CALL_SCREENING);
            startActivityForResult(intent, REQUEST_ID);
        }catch (Exception e){
            Log.d("EEX","EXCEpetion tttttt "+ e);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ID) {
            if (resultCode == android.app.Activity.RESULT_OK) {
                // Your app is now the call screening app
                Log.d("TTTTTTTTTr","here we go");
            } else {
                // Your app is not the call screening app
                Log.d("TTTTTTTTTr","here we go noo");
            }
        }
    }
    


    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Intialize Dbcontroller
        db_controller = new Db_controller(this,"",null,1);
       try {
           requestRole();
       }catch (Exception ex){
           Log.d("this is great","Exce "+ex);
       }





        Intent mCallServiceIntent = null;
        try {
            mCallServiceIntent = new Intent(this, Class.forName("android.telecom.CallScreeningService"));
            Log.d("EXXXX2","EEEE");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.d("EXXXX","EEEE");
        }
        ServiceConnection mServiceConnection = new ServiceConnection(){

            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                // iBinder is an instance of CallScreeningService.CallScreenBinder
                // CallScreenBinder is an inner class present inside CallScreenService
                Log.d("EXXXX1","EXXXX");
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                Log.d("EXXXX3","EEEE");
            }

            @Override
            public void onBindingDied(ComponentName name) {
                Log.d("EXXXX4","EEEE");
            }
        };
        Log.d("EXXXX6","EEEE");
        bindService(mCallServiceIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
        Log.d("EXXXX5","EEEE");





        //getting buttons from xml
        buttonStop = (Button) findViewById(R.id.buttonStop);








        //attaching onclicklistener to buttons
        buttonStop.setOnClickListener(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
        }
    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {


        if (view == buttonStart) {
            TelephonyManager telemamanger = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
          //  String getSimSerialNumber = telemamanger.getSimSerialNumber();
            String getSimNumber = telemamanger.getLine1Number();
            Toast.makeText(this, "Number: "+getSimNumber, Toast.LENGTH_SHORT).show();

            //startService(new Intent(this, MyService.class));
            CallAssist callAssist = new CallAssist(this);
            callAssist.docallAssist();
        } else if (view == buttonStop) {
            //stopping service
            stopService(new Intent(this, MyService.class));
        }
    }



    /////////Intents///////////

    public void intentViewUser(){
        Intent intent = new Intent(MainActivity.this,EditProfileActivity.class);
        startActivity(intent);
    }
    public void intentShowCard(){
        Intent intent = new Intent(MainActivity.this,ShowCardActivity.class);
        startActivity(intent);
    }
    public void intentViewHistory(){
        Intent intent = new Intent(MainActivity.this,ShowHistoryActivity.class);
        startActivity(intent);
    }
    public void intentAddUser(){
        Intent intent = new Intent(MainActivity.this,AddUserActivity.class);
        startActivity(intent);
    }
    public void intentAddCard(){
        Intent intent = new Intent(MainActivity.this,AddCardActivity.class);
        startActivity(intent);
    }


    public void btn_click(View view) {
        switch (view.getId()){
            case (R.id.btn_addCardNo):
                intentAddCard();
                break;
            case (R.id.btn_show_card):
                intentShowCard();
                break;
            case (R.id.btn_history):
                //db_controller.updateUser(firstname.getText().toString(),lastname.getText().toString());
                intentViewHistory();
                break;
            case (R.id.btn_show):
                intentViewUser();
                break;
            case (R.id.btn_adduser):
                intentAddUser();
                break;
        }
    }
}

