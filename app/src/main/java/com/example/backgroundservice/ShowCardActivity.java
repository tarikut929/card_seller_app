package com.example.backgroundservice;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class ShowCardActivity extends AppCompatActivity {
    Db_controller db_controller;
    ShowCardAdabter listCardAdabter;
    Spinner amountSpinner,isRechargedSpinner;
    ListView listView;
    String[]cardsArray;
   // int isRechargedInt;
    //String amountText;


 public ShowCardActivity(){

 }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_card);

//get the spinner from the xml.
        amountSpinner = findViewById(R.id.amount_spnr);
        isRechargedSpinner = findViewById(R.id.isrecharged_spnr);
        //create a list of items for the spinner.
        String[] items = new String[]{"5", "10", "15","25","50","100"};
        String[]items2 = new String[]{"new","Recharged"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,items2);
        //set the spinners adapter to the previously created one.
        amountSpinner.setAdapter(adapter);
        isRechargedSpinner.setAdapter(adapter2);

        spinnerListener();
        //fetch the cards list here
        fillListView();
    }


    public void spinnerListener(){
        //On Item selected Listener for amount of card
        amountSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                String var = parentView. getItemAtPosition(position).toString();
                insertAmount(var);
                makeChange();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        // On Item changed Listener for isRechargedSpinner
        isRechargedSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String isRechargedText = isRechargedSpinner.getSelectedItem().toString();
                int var;
                if(isRechargedText.equals("new")){
                    var = 0;
                }else{
                    var = 1;
                }
                insertIsRecharged(var);
                makeChange();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Your code
            }
        });
        makeChange();
 }

    public void makeChange(){
        db_controller =  new Db_controller(this, "", null, 1);
        String amountText_new = db_controller.getMiddleValueAmount();
        int isRechargedInt_new = db_controller.getMiddleValueBought();
        Log.d("RRRR","rrrrr"+ isRechargedInt_new);
        int size_of_array = db_controller.sizeOfCardArray(isRechargedInt_new,amountText_new);
        cardsArray = new String[size_of_array];
        db_controller.listCards(cardsArray,db_controller.getMiddleValueBought(),db_controller.getMiddleValueAmount());
        fillListView();
    }

    public void fillListView(){

        listCardAdabter = new ShowCardAdabter(this, cardsArray);
        listView = (ListView) findViewById(R.id.list_card);
        listView.setAdapter(listCardAdabter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String items =  (String)parent.getItemAtPosition(position);
                confirmRemoval(position);
                listCardAdabter.notifyDataSetChanged();
                Log.d("FFFF","FFFF"+items);
                return false;
            }
        });
    }

    public void confirmRemoval(final int position){
        new AlertDialog.Builder(this)
                .setTitle("Remove Card")
                .setMessage("Do you really want to Remove the card no?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //cardsArray[position] = "100";
                        listCardAdabter.notifyDataSetChanged();
                        db_controller.removeCard(cardsArray[position]);
                        makeChange();

                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }

    public void insertAmount(String var){
        db_controller =  new Db_controller(this, "", null, 1);
        db_controller.insertMiddleValueAmount(var);
        Log.d("DDD","FFFF"+var);

    }

    public void insertIsRecharged(int var){
        db_controller =  new Db_controller(this, "", null, 1);
        db_controller.insertMiddleValueBought(var);
    }
}
