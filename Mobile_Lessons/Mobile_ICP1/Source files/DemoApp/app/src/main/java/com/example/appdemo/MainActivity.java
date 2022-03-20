/*Mobile ICP1 Login Application*/

package com.example.appdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    /*Used Components class creation*/
    EditText username;
    EditText password;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Creates object for GUI interactive components*/
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btn = findViewById(R.id.btn);

        /*Login Button clicked checks for valid entered credentials and performs actions*/
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*If username and password combined are valid, display next screen and message*/
                if(username.getText().toString().equals("user") && password.getText().toString().equals("pass")) {
                    Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                    startActivity(intent);
                    Toast.makeText(MainActivity.this, "You entered the right value", Toast.LENGTH_SHORT).show();
                }
                /*Username and password not valid, display message*/
                else{
                    Toast.makeText(MainActivity.this, "Value is not correct", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}