package com.example.backgroundservice;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] maintitle;
    private final String[] subtitle;
    private final Integer[] imgid;
    private final String[] amount;
    private final String[] limit;
     Button btn_edit;
     EditProfileActivity editProfileActivity;

    public MyListAdapter(Activity context, String[] maintitle, String[] subtitle, Integer[] imgid,String[] amount,String[] limit) {
        super(context, R.layout.mylist, maintitle);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.maintitle=maintitle;
        this.subtitle=subtitle;
        this.imgid=imgid;
        this.amount = amount;
        this.limit = limit;

    }

    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.mylist, null,true);

        ////

        ////
        btn_edit = rowView.findViewById(R.id.btn_edit);
        btn_edit.setOnClickListener(myButtonClickListener);
        TextView titleText = (TextView) rowView.findViewById(R.id.title);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView subtitleText = (TextView) rowView.findViewById(R.id.subtitle);

        titleText.setText(maintitle[position]);
        imageView.setImageResource(imgid[position]);
        subtitleText.setText(subtitle[position]);

        return rowView;

    }
    private View.OnClickListener myButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            View parentRow = (View) v.getParent();
            ListView listView = (ListView) parentRow.getParent();
            final int position = listView.getPositionForView(parentRow);
            Intent intent = new Intent(context,EditActivity.class);
            intent.putExtra("name",maintitle[position]);
            intent.putExtra("phone",subtitle[position]);
            intent.putExtra("amount",amount[position]);
            intent.putExtra("limit",limit[position]);
           context.startActivity(intent);


        }
    };

}
