package com.example.backgroundservice;

import android.content.Context;
import android.widget.Toast;
/// do nothin

import java.util.Date;

    public class PhoneCallReceiver extends CallReceiver {

    protected void onIncomingCallReceived(Context ctx, String number, Date start)
    {
        //
    }

    protected void onIncomingCallAnswered(Context ctx, String number, Date start)
    {
        //
    }

    @Override
    protected void onIncomingCallEnded(Context ctx, String number, Date start, Date end)
    {
        Toast.makeText(ctx, "onIncomingCallEnded onIncomingCallEnded"+number, Toast.LENGTH_SHORT).show();
        //
    }

    @Override
    protected void onOutgoingCallStarted(Context ctx, String number, Date start)
    {
        Toast.makeText(ctx, "onOutgoingCallStarted onIncomingCallEnded "+number, Toast.LENGTH_SHORT).show();

        //
    }

    @Override
    protected void onOutgoingCallEnded(Context ctx, String number, Date start, Date end)
    {
        Toast.makeText(ctx, "onOutgoingCallEnded PhoneCalleReciever "+number, Toast.LENGTH_SHORT).show();

        //
    }

    @Override
    protected void onMissedCall(Context ctx, String number, Date start)
    {
        Toast.makeText(ctx, "missed call from Phon e callReciever "+number, Toast.LENGTH_SHORT).show();

        //
    }
}
