package com.example.texttospeech;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.w3c.dom.Text;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    //Used Variables
    EditText inputTxt;
    Button speakBtn;
    TextToSpeech tts;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setting variables to
        inputTxt = findViewById(R.id.speechInput);
        speakBtn = findViewById(R.id.speakBtn);

        //Listener Button click initiate input text to audio text
        speakBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             tts = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {
                 @Override
                 public void onInit(int status) {
                     if (status == TextToSpeech.SUCCESS) {
                         int result = tts.setLanguage(Locale.US);
                         if (result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA) {
                             Log.e("message", "Language is not supported");
                         }
                         else {
                             //tts.setLanguage(Locale.US);
                             //tts.speak("Test Text to Speech", TextToSpeech.QUEUE_ADD, null);
                            speak();
                         }
                     }
                 }
             });
            }
        });
    }

    //Call input text to speech
    void speak() {
        String text = inputTxt.getText().toString();
        tts.setSpeechRate(0.75f);
        //tts.speak(text, TextToSpeech.QUEUE_ADD, null);
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    //On pause to stop audio output
    @Override
    protected void onPause() {
        super.onPause();
        tts.stop();
    }

   /* @Override
    protected void onDestroy() {
        super.onDestroy();
        tts.shutdown();
    } */
}