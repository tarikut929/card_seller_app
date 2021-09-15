package com.example.backgroundservice;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ShowCardAdabter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] maintitle;
    Button btn_edit;

    public ShowCardAdabter(Activity context, String[] cardArray) {

        super(context, R.layout.list_for_card, cardArray);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.maintitle=cardArray;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.list_for_card, null,true);
      //  btn_edit = rowView.findViewById(R.id.btn_edit);
      //  btn_edit.setOnClickListener(myButtonClickListener);
        TextView cardText = (TextView) rowView.findViewById(R.id.card_text);
        cardText.setText(maintitle[position]);
        return rowView;
    }

    private View.OnClickListener myButtonClickListener = new View.OnClickListener() {
        @Override
        public void  onClick(View v) {
            View parentRow = (View) v.getParent();
            ListView listView = (ListView) parentRow.getParent();
            final int position = listView.getPositionForView(parentRow);
           // Intent intent = new Intent(context,EditActivity.class);
            //intent.putExtra("name",maintitle[position]);
           // context.startActivity(intent);
        }
    };

    private View.OnLongClickListener logClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            Log.d("SSSS","that is it");
            View parentRow = (View)v.getParent();
            ListView listView = (ListView)parentRow.getParent();
            final int position = listView.getPositionForView(parentRow);
            return true;

        }
    };

}


