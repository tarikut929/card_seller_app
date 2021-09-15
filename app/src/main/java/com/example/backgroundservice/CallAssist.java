package com.example.backgroundservice;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

public class CallAssist {
    private final Context context;

    public CallAssist(Context context) {
        this.context = context;

    }
    public void docallAssist(){
       /* if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            Toast.makeText(context, "This is Version", Toast.LENGTH_SHORT).show();
            context.startForegroundService(new Intent(context,MyService.class));
        }else {
            context.startService(new Intent(context, MyService.class));
        }

        */
        context.startService(new Intent(context, MyService.class));



    }
}
