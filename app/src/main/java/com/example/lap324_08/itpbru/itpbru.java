package com.example.lap324_08.itpbru;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class itpbru extends AppCompatActivity {

    //  Explicit...
    private MyManager myManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itpbru);

        myManager = new MyManager(this);

        //  Test to add new user...
        //myManager.addNewUser("123", "Name", "Sur", "User", "Pass");

    }   // Main method...

    public void clickSignUpMain(View view) {
        startActivity(new Intent(itpbru.this, signUpActivity.class));
    }

}   // Main class...
