package com.example.backgroundservice;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyAdabterTransaction extends ArrayAdapter<String> {
    Db_controller db_controller;
    private final Activity context;
    private final String[] maintitle;
    private final String[] subtitle;
    private final int[] payArray;
    private final int[] minIdArray;

    Button btn_edit,btn_pay;
    EditProfileActivity editProfileActivity;

    public MyAdabterTransaction(Activity context, String[] maintitle, String[] subtitle, int[] payArray, int[] minIdArray) {
        super(context, R.layout.list3, maintitle);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.maintitle=maintitle;
        this.subtitle=subtitle;
        this.payArray = payArray;
        this.minIdArray = minIdArray;
    }

    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.list3, null,true);

        ////

        db_controller = new Db_controller(context,"",null,1);

        ////b
        btn_edit = rowView.findViewById(R.id.btn_edit);
        if(payArray[position] == 1){
            btn_edit.setBackgroundColor(Color.GRAY);
            btn_edit.setTextColor(Color.BLACK);
        }else {
            btn_edit.setBackgroundColor(Color.WHITE);
        }

        btn_edit.setOnClickListener(myButtonClickListener);
        TextView titleText = (TextView) rowView.findViewById(R.id.title);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView subtitleText = (TextView) rowView.findViewById(R.id.subtitle);
        // subtitleText.setTextColor(Color.RED);

        titleText.setText(maintitle[position]);
        subtitleText.setText(subtitle[position]);

        return rowView;

    }

    private View.OnClickListener myButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            View parentRow = (View) v.getParent();
            ListView listView = (ListView) parentRow.getParent();
            final int position = listView.getPositionForView(parentRow);
            //parentRow.setBackgroundColor(Color.GRAY);

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            v.setBackgroundColor(Color.GRAY);
                            db_controller.setUserHistoryPayed(1,minIdArray[position]);
                            //Yes button clicked
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();


        }
    };

}
