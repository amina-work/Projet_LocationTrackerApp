package com.example.locationtrackerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    //initializing variables
    RecyclerView recyclerView;
    ArrayList<ContactModel> arrayList = new ArrayList<ContactModel>();
    MainAdapter adapter;
    BottomNavigationView nav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // NAVIGATION SETTINGS
        nav = findViewById(R.id.BottomNavigationBar);
        nav.setOnNavigationItemSelectedListener(this);
        // NAVIGATION SETTINGS

        //assign variable
        recyclerView = findViewById(R.id.recycler_view);

        //check permission
        checkPermissions();
    }
    //Added so the menu item being clicked will lead back here
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId(); //getting the menu items ID's
        if (id == R.id.map) {
            // Start the MainActivity
            Intent intent = new Intent(MainActivity.this, MapActivity.class);
            startActivity(intent);
            finish(); // add this line to finish the current activity
            return true;
        } else if (id == R.id.contacts) {
            // Do nothing (already in the SecondActivity)
            return true;
        }
        return false;
    }
    protected void onResume() {
        super.onResume();
        // Set the selected item in the menu to be the first one
        nav.setSelectedItemId(R.id.contacts); //changing menu item colors basically
    }

    private void checkPermissions() {
        //check condition
        if(ContextCompat.checkSelfPermission(MainActivity.this,
                android.Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED){
            //when permission is not granted => request again
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_CONTACTS}, 100);
            } else {
            getContactList();
        }
    }

    private void getContactList() {
        //Initialize Uri
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        //Sort by alphabet
        String sort = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" ASC";
        //Initialize cursor
        // =>An interface that provides read-only access to the result set of a database query
        Cursor cursor = getContentResolver().query(
                uri, null, null, null, sort
        );
        //Check condition
        if (cursor.getCount() > 0){
            //when count is greater than 0 => use while loop
            while (cursor.moveToNext()){
                //cursor move to next => get contact id
                @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                //get contact name
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                //initialize phone uri
                Uri uriPhone = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                //initialize selection
                String selection = ContactsContract.CommonDataKinds.Phone.CONTACT_ID+" =?";
                //initialize phone cursor
                Cursor phoneCursor = getContentResolver().query(uriPhone, null, selection, new String[]{id}, null);
                //check condition
                if (phoneCursor.moveToNext()){
                    //when phone cursor move to next
                    @SuppressLint("Range") String number = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    //initialize contact model
                    ContactModel model = new ContactModel();
                    //Set name & number
                    model.setName(name);
                    model.setNumber(number);
                    //add model in array list
                    arrayList.add(model);
                    //Close phone cursor
                    phoneCursor.close();
                }
            }
            //close cursor
            cursor.close();
        }
        //set layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //intialize adapter
        adapter = new MainAdapter(this, arrayList);
        //set adapter
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //check condition
        if(requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            //when permission is granted => call method
            getContactList();
        } else{
            //else => Display toast
            Toast.makeText(MainActivity.this, "Permossion Denied.", Toast.LENGTH_SHORT).show();
            //call check permission method
            checkPermissions();
        }
    }
}