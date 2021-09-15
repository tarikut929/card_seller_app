package com.example.backgroundservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class TransactionActivity extends AppCompatActivity {
    ListView list;
    TextView nameTextView,phoneTextView;
    Db_controller db_controller;
    int lengthOfArray;
    int itemPosition;
    String phoneIn;
    Button btn_edit;


    int[] userId = new int[200];
    String[] usersArr = new String[200];
    String[] phoneArr = new String[200];
    String[] noOfSelledCarsArr = new String[200];
    int[] amountArr = new int[200];
    String [] limitArr = new String[200];

    Integer[] imgid={
            R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_foreground,R.drawable.ic_launcher_background ,
            R.drawable.ic_launcher_background,R.drawable.ic_launcher_background ,
            R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_foreground,R.drawable.ic_launcher_background ,
            R.drawable.ic_launcher_background,R.drawable.ic_launcher_background ,
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        phoneIn = getIntent().getStringExtra("phone");
        String nameIn = getIntent().getStringExtra("name");


        nameTextView = findViewById(R.id.titleTrns);
        phoneTextView = findViewById(R.id.subtitleTrns);
        nameTextView.setText(nameIn);
        phoneTextView.setText(phoneIn);


        //Intialize Dbcontroller
        db_controller = new Db_controller(this,"",null,1);
        //define size of array

        addCardAmount();
        addDateArray();
        addPayArray();
        addIdArray();

        for(int i = 0; i < amountArr.length; i++){
            Log.d("MMMMK"," "+ amountArr[i]);
        }

//        addLimitArray();
//        addNoOfCardSelledArray();
//////////////////////////////////////////////////////
        //check action//
        for (int i=0; i<usersArr.length; i++) {
            if(usersArr[i]==null){
                lengthOfArray = i;
                break;
            }
        }
  ///////////////////////////////////////////////////
        String[] minArrName = new String[lengthOfArray];
        for(int i=0; i<lengthOfArray; i++){
            minArrName[i] = usersArr[i];
        }
        //min array phone
        String[] minArrPhone = new String[lengthOfArray];
        for(int i=0; i<lengthOfArray; i++){
            minArrPhone[i] = phoneArr[i];
        }

        //min array amount
        int[] minArrAmount = new int[lengthOfArray];
        for(int i=0; i<lengthOfArray; i++){
            minArrAmount[i] = amountArr[i];
        }

        //min array Id
        int [] minIdArray = new int [lengthOfArray];
        for(int i=0; i<lengthOfArray; i++){
            minIdArray[i] = userId[i];
        }

        //min array limit
        String[] minArrayLimit = new String[lengthOfArray];
        for(int i=0; i<lengthOfArray; i++){
            minArrayLimit[i] = limitArr[i];
        }
        //min array no of cards sold
        String[] minArrNofCardSold = new String[lengthOfArray];
        for(int i=0; i<lengthOfArray; i++){
            minArrNofCardSold[i] = noOfSelledCarsArr[i];
        }
        ////img
        Integer[] minArrImg = new Integer[lengthOfArray];
        for(int i=0; i<lengthOfArray; i++){
            minArrImg[i] = imgid[0];
        }



        MyAdabterTransaction adapter=new MyAdabterTransaction(this, minArrName,minArrPhone,minArrAmount,minIdArray);
        list=(ListView)findViewById(R.id.listTransaction);
        list.setAdapter(adapter);





        for(int i = 0; i < minIdArray.length; i++){
            Log.d("ggggg"," "+minIdArray[i]);
        }


        btn_edit = findViewById(R.id.btn_edit);




        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                // TODO Auto-generated method stub
                itemPosition = position;
                // Toast.makeText(ViewUserActivity.this, ""+usersArr[position], Toast.LENGTH_SHORT).show();
                if(position == 0) {
                    //code specific to first list item
                    // Toast.makeText(getApplicationContext(), Arrays.toString(usersArr), Toast.LENGTH_LONG).show();

                }

                else if(position == 1) {
                    //code specific to 2nd list item
                    // Toast.makeText(getApplicationContext(),"Place Your Second Option Code",Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getApplicationContext(), Arrays.toString(minArrayName()), Toast.LENGTH_LONG).show();
                }

                else if(position == 2) {

                    //Toast.makeText(getApplicationContext(),"Place Your Third Option Code",Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), Arrays.toString(minArrayPhone()), Toast.LENGTH_LONG).show();
                }
                else if(position == 3) {

                    Toast.makeText(getApplicationContext(), Arrays.toString(minArrayNoSelledCards()), Toast.LENGTH_LONG).show();
                }
                else if(position == 4) {

                    Toast.makeText(getApplicationContext(),"Place Your Fifth Option Code",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    ////////////////////////////////
    public void addCardAmount(){

        db_controller.listUserHistoryAmount(phoneIn,usersArr);
        //Toast.makeText(getApplicationContext(), Arrays.toString(usersArr), Toast.LENGTH_LONG).show();
    }
    public void addDateArray(){
        db_controller.listUserHistoryDate(phoneIn,phoneArr);
        //Toast.makeText(getApplicationContext(), Arrays.toString(usersArr), Toast.LENGTH_LONG).show();
    }

    public void addNoOfCardSelledArray(){
        db_controller.listAllUserNoCardSelled(noOfSelledCarsArr);
        //Toast.makeText(getApplicationContext(), Arrays.toString(usersArr), Toast.LENGTH_LONG).show();
    }


    public void addPayArray(){
        db_controller.listUserHistoryPay(phoneIn, amountArr);
    }


    public void addIdArray(){
        db_controller.listUserHistoryID(phoneIn,userId);
    }






    //////////////////////////////
    public String[] minArrayName(){

        for (int i=0; i<usersArr.length; i++) {
            if(usersArr[i]==null){
                lengthOfArray = i;
                break;
            }
        }
        String[] minArr = new String[lengthOfArray];
        for(int i=0; i<lengthOfArray; i++){
            minArr[i] = usersArr[i];
        }
        //Toast.makeText(getApplicationContext(), Arrays.toString(minArr), Toast.LENGTH_LONG).show();
        return minArr;
    }
    public String[] minArrayPhone(){
        String[] minArr = new String[lengthOfArray];
        for(int i=0; i<lengthOfArray; i++){
            minArr[i] = phoneArr[i];
        }
        //Toast.makeText(getApplicationContext(), Arrays.toString(minArr), Toast.LENGTH_LONG).show();
        return minArr;

    }
    public String[] minArrayNoSelledCards(){
        String[] minArr = new String[lengthOfArray];
        for(int i=0; i<lengthOfArray; i++){
            minArr[i] = noOfSelledCarsArr[i];
        }
        //Toast.makeText(getApplicationContext(), Arrays.toString(minArr), Toast.LENGTH_LONG).show();
        return minArr;

    }
    //////////////////////////////////////
    public Integer[] minImage(){
        Integer[] minArr = new Integer[lengthOfArray];
        for(int i=0; i<lengthOfArray; i++){
            minArr[i] = imgid[0];
        }
        //Toast.makeText(getApplicationContext(), Arrays.toString(minArr), Toast.LENGTH_LONG).show();
        return minArr;
    }
    /////////////////////////////
    public void btn_click(View view) {
        switch (view.getId()){
            // case (R.id.mylistLinierLayout):
            //    Toast.makeText(this, "This is Profile", Toast.LENGTH_SHORT).show();
            //    break;
            case (R.id.btn_edit):
               // openEdit();
                break;

        }
    }
    /*
    public void openEdit(){

        Intent intent = new Intent(this,EditActivity.class);
        intent.putExtra("name",usersArr[itemPosition]);
        intent.putExtra("pos",itemPosition);
        startActivity(intent);
    }
    private View.OnClickListener myButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            View parentRow = (View) v.getParent();
            ListView listView = (ListView) parentRow.getParent();
            final int position = listView.getPositionForView(parentRow);
            openEdit();
        }
    };
    */

}
