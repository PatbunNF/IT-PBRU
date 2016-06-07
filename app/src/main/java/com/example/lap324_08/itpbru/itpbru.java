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

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class itpbru extends AppCompatActivity {

    //  Explicit...
    private MyManager myManager;
    private static final String urlJSON = "http://swiftcodingthai.com/pbru2/get_user_master.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itpbru);

        myManager = new MyManager(this);

        //  Test to add new user...
        //myManager.addNewUser("123", "Name", "Sur", "User", "Pass");

        //  Delete all sqlite...
        deleteAllSQLite();

        MySynJSON();

    }   // Main method...

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
