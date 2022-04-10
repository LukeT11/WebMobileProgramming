package com.example.mypizza;
import MainActivity.Java;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        listView = findViewById(R.id.summaryList);
        ArrayList<String> summaryArray = new ArrayList<>();

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, summaryArray);
        summaryArray.add(adapterView.getItemAtPosition(i).toString());

        listView.setAdapter(arrayAdapter);
    }
}