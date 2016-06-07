package com.example.lap324_08.itpbru;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class signUpActivity extends AppCompatActivity {

    //  Explicit : Declare variable...
    private EditText nameEditText, surnameEditText, userEditText, passwordEditText;
    private String nameString, surnameString, userString, passwordString;
    private static final String urlUpload = "http://swiftcodingthai.com/pbru2/add_user_Pat.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //  Bind widget...
        nameEditText = (EditText) findViewById(R.id.editText3);
        surnameEditText = (EditText) findViewById(R.id.editText);
        userEditText = (EditText) findViewById(R.id.editText2);
        passwordEditText = (EditText) findViewById(R.id.editText4);


    }   //  Main method...

    public void clickSignUpSign(View view) {

        nameString = nameEditText.getText().toString().trim();
        surnameString = surnameEditText.getText().toString().trim();
        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();

        //  Check space...
        if (checkSpace()) {
            //  True...
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, "Cannot continue...", "Please fill out all the fields to proceed.");

        } else {
            //  False...
            uploadValueToServer();

        }

    }   //  Click sign...

    private void uploadValueToServer() {

    }   //  Upload...

    private boolean checkSpace() {

        boolean result = true;

        result = nameString.equals("") || surnameString.equals("") ||
                userString.equals("") || passwordString.equals("");

        return result;
    }

}   //  Main class...
