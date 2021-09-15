package com.example.backgroundservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;

public class ShowHistoryActivity extends AppCompatActivity {
    ListView list;
    Db_controller db_controller;
    int lengthOfArray;
    int itemPosition;

    String[] usersArr = new String[200];
    String[] phoneArr = new String[200];
    String[] noOfSelledCarsArr = new String[200];
    String[] amountArr = new String[200];
    String[] limitArr = new String[200];
    String[] dateArr = new String[200];

    Integer[] imgid = {
            R.drawable.ic_launcher_background, R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background, R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background, R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background, R.drawable.ic_launcher_background,
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_history);




        db_controller = new Db_controller(this, "", null, 1);
        db_controller.listAllUserHistoryAmount();
        addNameArray();
        addPhoneArray();
       // addAmountArray();
      //  addLimitArray();
        addNoOfCardSelledArray();

        //define number of array
        for (int i = 0; i < usersArr.length; i++) {
            if (usersArr[i] == null) {
                lengthOfArray = i;
                break;
            }
        }

        final String[] minArrName = new String[lengthOfArray];

        int new_index = 0;
        for (int i = 0; i < lengthOfArray; i++) {
            int j;
            for(j = 0; j < i; j++){
                if (usersArr[i].equals(usersArr[j]))
                    break;
            }
            if (i == j) {
                minArrName[new_index] = usersArr[i];
                new_index++;
            }
        }



        //min array phone
        int new_index_phone = 0;
        final String[] minArrPhone = new String[lengthOfArray];
        for (int i = 0; i < lengthOfArray; i++) {
            minArrPhone[i] = phoneArr[i];

            int j;
            for(j = 0; j < i; j++){
                if (usersArr[i].equals(usersArr[j]))
                    break;
            }
            if (i == j) {
                minArrPhone[new_index_phone] = phoneArr[i];
                new_index_phone++;
            }

        }
        //distinict value of name
        int newLength = 0;
        for (int i = 0; i < minArrName.length; i++) {
            if (minArrName[i] == null) {
                newLength = i;
                break;
            }
        }

        String[]distArrayName = new String[newLength];
        for (int i = 0; i < newLength; i++) {
            distArrayName[i] = minArrName[i];
        }
        String[]distArrayPhone = new String[newLength];
        for (int i = 0; i < newLength; i++) {
            distArrayPhone[i] = minArrPhone[i];
        }
        //min array amount
        String[] minArrAmount = new String[lengthOfArray];
        for (int i = 0; i < lengthOfArray; i++) {
            minArrAmount[i] = amountArr[i];
        }
        //min array limit
        String[] minArrayLimit = new String[lengthOfArray];
        for (int i = 0; i < lengthOfArray; i++) {
            minArrayLimit[i] = limitArr[i];
        }
        //min array no of cards sold
        String[] minArrNofCardSold = new String[lengthOfArray];
        for (int i = 0; i < lengthOfArray; i++) {
            minArrNofCardSold[i] = noOfSelledCarsArr[i];
        }
        ////img
        Integer[] minArrImg = new Integer[lengthOfArray];
        for (int i = 0; i < lengthOfArray; i++) {
            minArrImg[i] = imgid[0];
        }


        ListAdabterHistory adapter = new ListAdabterHistory(this, distArrayName, distArrayPhone, minArrImg, minArrAmount, minArrayLimit);
        list = (ListView) findViewById(R.id.list_history);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent trnsIntent = new Intent(getApplicationContext(), TransactionActivity.class);
                trnsIntent.putExtra("name", minArrName[position]);
                trnsIntent.putExtra("phone", minArrPhone[position]);
                startActivity(trnsIntent);
                // TODO Auto-generated method stub
                itemPosition = position;


            }
        });

        db_controller.listAllUserHistoryDate(dateArr);
    }

    ////////////////////////////////
    public void addNameArray() {
        db_controller.listAllUserHistoryName(usersArr);
    }
    public void addPhoneArray() {
        db_controller.listAllUserHistoryPhone(phoneArr);
    }
    public void addNoOfCardSelledArray() { db_controller.listAllUserNoCardSelled(noOfSelledCarsArr); }
   // public void addAmountArray() { db_controller.listAllUserAmount(amountArr); }
   // public void addLimitArray() { db_controller.listAllUserLimit(limitArr);}



    //////////////////////////////
//    public String[] minArrayName() {
//        for (int i = 0; i < usersArr.length; i++) {
//            if (usersArr[i] == null) {
//                lengthOfArray = i;
//                break;
//            }
//        }
//        String[] minArr = new String[lengthOfArray];
//        for (int i = 0; i < lengthOfArray; i++) {
//            minArr[i] = usersArr[i];
//        }
//        return minArr;
//    }
//
//    public String[] minArrayPhone() {
//        String[] minArr = new String[lengthOfArray];
//        for (int i = 0; i < lengthOfArray; i++) {
//            minArr[i] = phoneArr[i];
//        }
//        return minArr;
//
//    }
//
//    public String[] minArrayNoSelledCards() {
//        String[] minArr = new String[lengthOfArray];
//        for (int i = 0; i < lengthOfArray; i++) {
//            minArr[i] = noOfSelledCarsArr[i];
//        }
//        return minArr;
//    }
    //////////////////////////////////////
//    public Integer[] minImage() {
//        Integer[] minArr = new Integer[lengthOfArray];
//        for (int i = 0; i < lengthOfArray; i++) {
//            minArr[i] = imgid[0];
//        }
//        return minArr;
//    }
    /////////////////////////////
//    public void btn_click(View view) {
//        switch (view.getId()) {
//            case (R.id.btn_edit):
//               // openEdit();
//                break;
//
//        }
//    }

//    public void openEdit() {
//        Intent intent = new Intent(this, EditActivity.class);
//        intent.putExtra("name", usersArr[itemPosition]);
//        intent.putExtra("pos", itemPosition);
//        startActivity(intent);
//    }
//
//    private View.OnClickListener myButtonClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            View parentRow = (View) v.getParent();
//            ListView listView = (ListView) parentRow.getParent();
//            final int position = listView.getPositionForView(parentRow);
//           // openEdit();
//        }
//    };
}