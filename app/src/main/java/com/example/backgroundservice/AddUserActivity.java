package com.example.backgroundservice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddUserActivity extends AppCompatActivity {
    //Reference Db_Controller class
    Db_controller db_controller;

    //Edit TExt
    EditText name,phoneNumber,amount,limit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        //Intialize Dbcontroller
        db_controller = new Db_controller(this,"",null,1);
        //getting Edit text from xml
        name = findViewById(R.id.name_adduser);
        phoneNumber = findViewById(R.id.phoneNumber_addUser);
        amount = findViewById(R.id.amount_adduser);
        limit = findViewById(R.id.limit_addUser);
    }

    public void btn_click(View view) {
        switch (view.getId()){
            case (R.id.button_register):
                registerUser();
                break;
        }
    }
    public void registerUser(){
        db_controller.insertUser(name.getText().toString(),phoneNumber.getText().toString(),amount.getText().toString(),limit.getText().toString());
        Toast.makeText(this, "User Registered", Toast.LENGTH_SHORT).show();
        name.setText("");
        phoneNumber.setText("");
        amount.setText("");
        limit.setText("");
    }
}
