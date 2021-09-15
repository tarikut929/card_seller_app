package com.example.backgroundservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.telecom.CallScreeningService;
import android.telecom.Call.Details;
import android.telecom.CallScreeningService.CallResponse.Builder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;


import org.jetbrains.annotations.NotNull;

import java.util.Arrays;




@RequiresApi(api = Build.VERSION_CODES.N)
public  class MyCallScreeningService extends CallScreeningService{



    @Override
    public void onScreenCall(Details details) {

        Log.d("RRRRRRRRRRRr ","dd");
        String phoneNumber = details.getHandle().toString();
        Log.d("RRRRRRRRRRRr "+phoneNumber,"dd");
        Toast.makeText(this, "the incoming from::: "+phoneNumber, Toast.LENGTH_SHORT).show();
    }
    
}








