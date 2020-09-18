package com.example.mypasswords.ActivitiesControllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class splach extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this,MainActivity.class));
    }


    @Override
    protected void onPause() {
        super.onPause();
        super.finish();
    }
}
