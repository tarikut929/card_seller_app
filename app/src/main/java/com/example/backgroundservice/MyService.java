package com.example.backgroundservice;

import android.Manifest;
import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MyService extends Service {
    //creating a mediaplayer object
    private MediaPlayer player;
    private static final int REQUEST_CALL = 1;
    private EditText mEditTextNumber;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {



        Intent dialogIntent = new Intent(this, DoCallActivity.class);
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(dialogIntent);

       // makePhoneCall();
        return START_STICKY;
    }





    @Override
    public void onDestroy() {
        super.onDestroy();

    }



}
