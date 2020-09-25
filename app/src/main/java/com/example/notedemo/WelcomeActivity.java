package com.example.notedemo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.example.notepad.R;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        SharedPreferences preferences = getSharedPreferences(
                getString(R.string.preference_file_key), MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();


        System.out.println("First:"+preferences.getInt(getString(R.string.preference_file_key), 1));
        if (preferences.getInt(getString(R.string.preference_file_key), 1) == 1){
            editor.putInt(getString(R.string.preference_file_key),
                    preferences.getInt(getString(R.string.preference_file_key), 1)+1);
            editor.apply();

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.println("delay1");
                    Intent selectIntent = new Intent(getApplicationContext(),SelectActivity.class);
                    startActivity(selectIntent);
                }
            },2000);
            System.out.println("delay2");
        }
        else if (preferences.getInt(getString(R.string.preference_file_key), 1) > 1) {
            Intent selectIntent = new Intent(this,SelectActivity.class);
            this.finish();
            startActivity(selectIntent);
        }
        System.out.println("Second:"+preferences.getInt(getString(R.string.preference_file_key), 1));

    }





}
