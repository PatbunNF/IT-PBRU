package com.example.lap324_08.itpbru;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

public class itpbru extends AppCompatActivity {

    //  Explicit...
    private MyManager myManager;
    private static final String urlJSON = "http://swiftcodingthai.com/pbru2/get_user_master.php";
    private EditText userEditText, passwordEditText;
    private String userString, passwordString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itpbru);

        //  Bind widget...
        userEditText = (EditText) findViewById(R.id.editText5);
        passwordEditText = (EditText) findViewById(R.id.editText6);

        myManager = new MyManager(this);

        //  Test to add new user...
        //myManager.addNewUser("123", "Name", "Sur", "User", "Pass");

        //  Delete all sqlite...
        deleteAllSQLite();

        //Show Add First Data
        //AddFirst();

        MySynJSON();



    }   // Main method...

    public void clickLogIn(View view) {

        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();

        //  Check spacing...
        if (userString.equals("") || passwordString.equals("")) {
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, "Nah~", "Please enter user and password correctly.");
        } else {
            checkUserPasword();
        }

    }   //  Click to log-in button...

    private void checkUserPasword() {



    }   //  Checking user & password...

    /*private void AddFirst() {

        MyData myData = new MyData();

        String[] nameStrings = myData.getNameStrings();
        String[] surnameStrings = myData.getSurnameStrings();
        String[] userStrings = myData.getUserStrings();
        String[] passowordStrings = myData.getPasswordStrings();

        for (int i = 0; i < nameStrings.length; i++) {

            myManager.addNewUser(Integer.toString(i + 1), nameStrings[i], surnameStrings[i],
                    userStrings[i], passowordStrings[i]);
        }
    }*/

    private void MySynJSON() {
        connectUserTABLE connectUserTABLE = new connectUserTABLE(this);
        connectUserTABLE.execute();
    }

    //  Create inner class...
    private class connectUserTABLE extends AsyncTask<Void, Void, String> {

        private Context context;
        private ProgressDialog progressDialog;


        public connectUserTABLE(Context context) {
            this.context = context;
        }   //  Constructor...

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(context, "Synchronizing Server", "Please wait...");

        }   //  On pre...

        @Override
        protected String doInBackground(Void... params) {

            try {

                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(urlJSON).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();

            }catch (Exception e) {
                Log.d("June7th", "Error DoInBG ==> " + e.toString());
                return null;
            }

        }   //  Do in bg...

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                progressDialog.dismiss();
                Log.d("June7th", "JSON ==> " + s);

                JSONArray jsonArray = new JSONArray(s);

                String[] idStrings = new String[jsonArray.length()];
                String[] nameStrings = new String[jsonArray.length()];
                String[] surnameStrings = new String[jsonArray.length()];
                String[] userStrings = new String[jsonArray.length()];
                String[] passwordStrings = new String[jsonArray.length()];

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    idStrings[i] = jsonObject.getString("id");
                    nameStrings[i] = jsonObject.getString(MyManager.column_name);
                    surnameStrings[i] = jsonObject.getString(MyManager.column_surname);
                    userStrings[i] = jsonObject.getString(MyManager.column_user);
                    passwordStrings[i] = jsonObject.getString(MyManager.column_password);

                    myManager.addNewUser(idStrings[i], nameStrings[i], surnameStrings[i], userStrings[i], passwordStrings[i]);

                }   //  For loop...

            } catch (Exception e) {
                e.printStackTrace();
            }

        }   //  On post...

    }   //  Connected class...


    private void deleteAllSQLite() {
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name, MODE_PRIVATE, null);
        sqLiteDatabase.delete(MyManager.user_table, null, null);
    }   //  Delete

    public void clickSignUpMain(View view) {
        startActivity(new Intent(itpbru.this, signUpActivity.class));
    }

}   // Main class...
