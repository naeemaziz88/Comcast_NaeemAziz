package edu.naeemaziz.comcast.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import edu.naeemaziz.comcast.R;


public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Now we are moving to next Screen
                Intent intent;
                intent = new Intent(Splash.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        }, 2000);
    }


}