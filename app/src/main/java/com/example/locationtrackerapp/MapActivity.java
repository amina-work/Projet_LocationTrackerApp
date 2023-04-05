
package com.example.locationtrackerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MapActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView nav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // NAVIGATION SETTINGS
        nav = findViewById(R.id.BottomNavigationBar);
        nav.setOnNavigationItemSelectedListener(this);
        // NAVIGATION SETTINGS
    }


    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.contacts) {
            // Start the MainActivity
            Intent intent = new Intent(MapActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        } else if (id == R.id.map) {
            // Do nothing (already in the SecondActivity)
            return true;
        }
        return false;
    }

    protected void onResume() {
        super.onResume();

        // Set the selected item in the menu to be the second one
        nav.setSelectedItemId(R.id.map);
    }
}