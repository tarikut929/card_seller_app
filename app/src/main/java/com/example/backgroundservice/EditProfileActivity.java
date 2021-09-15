package com.example.backgroundservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;

public class EditProfileActivity extends AppCompatActivity {
    ListView list;
    Db_controller db_controller;
    int lengthOfArray;
    int itemPosition;

    String[] usersArr = new String[200];
    String[] phoneArr = new String[200];
    String[] noOfSelledCarsArr = new String[200];
    String[] amountArr = new String[200];
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
        setContentView(R.layout.activity_edit_profile);



        //Intialize Dbcontroller
        db_controller = new Db_controller(this,"",null,1);

        addNameArray();
        addPhoneArray();
        addAmountArray();
        addLimitArray();
        addNoOfCardSelledArray();

        for (int i=0; i<usersArr.length; i++) {
            if(usersArr[i]==null){
                lengthOfArray = i;
                break;
            }
        }
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
        String[] minArrAmount = new String[lengthOfArray];
        for(int i=0; i<lengthOfArray; i++){
            minArrAmount[i] = amountArr[i];
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



        MyListAdapter adapter=new MyListAdapter(this, minArrName,minArrPhone,minArrImg,minArrAmount,minArrayLimit);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);




    }
    ////////////////////////////////
    public void addNameArray(){

        db_controller.listAllUserName(usersArr);
        //Toast.makeText(getApplicationContext(), Arrays.toString(usersArr), Toast.LENGTH_LONG).show();
    }
    public void addPhoneArray(){
        db_controller.listAllUserPhone(phoneArr);
        //Toast.makeText(getApplicationContext(), Arrays.toString(usersArr), Toast.LENGTH_LONG).show();
    }

    public void addNoOfCardSelledArray(){
        db_controller.listAllUserNoCardSelled(noOfSelledCarsArr);
        //Toast.makeText(getApplicationContext(), Arrays.toString(usersArr), Toast.LENGTH_LONG).show();
    }
    public void addAmountArray(){
        db_controller.listAllUserAmount(amountArr);

    }
    public void addLimitArray(){
         db_controller.listAllUserLimit(limitArr);
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
        return minArr;
    }
    public String[] minArrayPhone(){
        String[] minArr = new String[lengthOfArray];
        for(int i=0; i<lengthOfArray; i++){
            minArr[i] = phoneArr[i];
        }
        return minArr;

    }
    public String[] minArrayNoSelledCards(){
        String[] minArr = new String[lengthOfArray];
        for(int i=0; i<lengthOfArray; i++){
            minArr[i] = noOfSelledCarsArr[i];
        }
        return minArr;

    }
    //////////////////////////////////////
    public Integer[] minImage(){
        Integer[] minArr = new Integer[lengthOfArray];
        for(int i=0; i<lengthOfArray; i++){
            minArr[i] = imgid[0];
        }
        return minArr;
    }
    /////////////////////////////
    public void btn_click(View view) {
        switch (view.getId()){
           // case (R.id.mylistLinierLayout):
            //    Toast.makeText(this, "This is Profile", Toast.LENGTH_SHORT).show();
            //    break;
            case (R.id.btn_edit):
                openEdit();
                break;

        }
    }
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
}