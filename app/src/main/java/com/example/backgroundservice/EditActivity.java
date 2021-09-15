package com.example.backgroundservice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {
    String phoneInside;


    //Reference Db_Controller class
    Db_controller db_controller;

    //Edit TExt
    EditText nameEdit,phoneNumberEdit,amountEdit,limitEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        String nameIn = getIntent().getStringExtra("name");
        String phoneIn = getIntent().getStringExtra("phone");
        String amountIn = getIntent().getStringExtra("amount");
        String limitIn = getIntent().getStringExtra("limit");

        //getting Edit text from xml
        nameEdit = findViewById(R.id.name_edit);
        phoneNumberEdit = findViewById(R.id.phoneNumber_addUser);
        amountEdit = findViewById(R.id.amount_adduser);
        limitEdit = findViewById(R.id.limit_addUser);
        //set The previous value
        phoneInside = phoneIn;

        nameEdit.setText(nameIn);
        phoneNumberEdit.setText(phoneIn);
        amountEdit.setText(amountIn);
        limitEdit.setText(limitIn);
        //Intialize Dbcontroller
        db_controller = new Db_controller(this,"",null,1);

    }

    public void btn_click(View view) {
        switch (view.getId()){
            case (R.id.button_register):
                //db_controller.insertUser(nameEdit.getText().toString(),phoneNumberEdit.getText().toString(),amountEdit.getText().toString(),limitEdit.getText().toString());
                db_controller.updateUserProfile(nameEdit.getText().toString(),nameEdit.getText().toString(),phoneInside,phoneNumberEdit.getText().toString(),amountEdit.getText().toString(),limitEdit.getText().toString());
                Toast.makeText(this, "User Registered", Toast.LENGTH_SHORT).show();
                nameEdit.setText("");
                phoneNumberEdit.setText("");
                amountEdit.setText("");
                limitEdit.setText("");
                break;
            case (R.id.button_deleteUser):
                db_controller.deleteUser(phoneInside);

        }
    }

    }
