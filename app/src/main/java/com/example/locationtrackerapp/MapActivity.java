
package com.example.locationtrackerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MapActivity extends AppCompatActivity {
    BottomNavigationView nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // NAVIGATION SETTINGS
        nav = findViewById(R.id.BottomNavigationBar);

        nav.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.map:
                    // do nothing since we're already on the MapActivity
                    return true;
                case R.id.contacts:
                    // start the SecondActivity
                    startActivity(new Intent(MapActivity.this, MainActivity.class));
                    return true;
                default:
                    return false;
            }
        });
        // NAVIGATION SETTINGS
    }
}