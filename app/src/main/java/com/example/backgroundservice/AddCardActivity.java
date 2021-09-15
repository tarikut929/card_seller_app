package com.example.backgroundservice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddCardActivity extends AppCompatActivity {
    EditText cardNumburs;
    Db_controller db_controller;
    Spinner dropdown;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        cardNumburs = findViewById(R.id.editText_cardNo);

        //Intialize Dbcontroller
        db_controller = new Db_controller(this,"",null,1);

        //get the spinner from the xml.
        dropdown = findViewById(R.id.spinner);
        //create a list of items for the spinner.
        String[] items = new String[]{"5", "10", "15","25","50","100"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);

    }

    public void insertCard(View view) {
        String text = dropdown.getSelectedItem().toString();
        db_controller.insertCard(cardNumburs.getText().toString(),Integer.parseInt(text));
        cardNumburs.setText("");
        Toast.makeText(this,"Card Inserted: "+text,Toast.LENGTH_SHORT).show();



    }
}
