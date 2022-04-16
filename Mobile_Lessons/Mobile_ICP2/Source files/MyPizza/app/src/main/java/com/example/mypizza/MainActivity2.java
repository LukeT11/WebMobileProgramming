package com.example.mypizza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    ArrayAdapter arrayAdapter;
    ListView listView;

    //Creates a ListView with summary from the order page, taking string from Order Page with Summary
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        listView = findViewById(R.id.summaryList);
        ArrayList<String> summaryArray = new ArrayList<>();

        Intent i = getIntent();

        String summaryText = i.getStringExtra("Summary");
        String[] splitText = summaryText.split("\n");

        for (int l=0; l < splitText.length; l++ ) {
            summaryArray.add(splitText[l]);
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, summaryArray);
        listView.setAdapter(arrayAdapter);
    }

    //Function with Button click, back to MainActivity1 Order Page
    public void orderScreen(View view) {
        Intent orderIntent = new Intent(MainActivity2.this, MainActivity.class);
        startActivity(orderIntent);
    }
}