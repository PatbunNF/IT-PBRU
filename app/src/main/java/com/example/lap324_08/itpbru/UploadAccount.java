package com.example.lap324_08.itpbru;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class UploadAccount extends AppCompatActivity {

    //  Explicit...
    private TextView inOutTextView, nameTextView;
    private Spinner spinner;
    private EditText editText;
    private String[] loginStrings;
    private int inoutAnInt;
    private String[] inoutStrings = new String[]{"Receiving-list", "Spending-list"};
    private String[] receiveStrings = new String[]{"Salary", "Over-Time", "Part-Time", "Extra class", "Commission", "Selling Product", "Other"};
    private String[] spendStrings = new String[]{"Food", "Tools", "Groceries", "Travel", "Entertainment", "Education", "Commission", "Hard/Software", "Phone/Internet", "Other"};
    private ArrayAdapter<String> stringArrayAdapter;
    private String categoryString, amountString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_account);

        //  Bind widget...
        inOutTextView = (TextView) findViewById(R.id.textView7);
        nameTextView = (TextView) findViewById(R.id.textView8);
        spinner = (Spinner) findViewById(R.id.spinner);
        editText = (EditText) findViewById(R.id.editText7);

        //  Receive value from intent...
        loginStrings = getIntent().getStringArrayExtra("Login");
        inoutAnInt = getIntent().getIntExtra("InOut", 0);

        //  show view...
        inOutTextView.setText(inoutStrings[inoutAnInt]);
        nameTextView.setText(loginStrings[1]+" "+loginStrings[2]);

        //  Create spinner...
        createSpinner();

    }   //  Main method...

    private void createSpinner() {

        switch (inoutAnInt) {

            case 0:
                stringArrayAdapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, receiveStrings);
                break;
            case 1:
                stringArrayAdapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, spendStrings);
                break;

        }   //  Switch...

        spinner.setAdapter(stringArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoryString = findCategory(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                categoryString = findCategory(0);
            }
        });

    }   //  Spinner creating...

    private String findCategory(int position) {

        String strResult = null;

        switch (inoutAnInt) {
            case 0:
                strResult = receiveStrings[position];
                break;
            case 1:
                strResult = spendStrings[position];
                break;
        }

        return strResult;
    }

    public void clickUpload(View view) {

        amountString = editText.getText().toString().trim();

        if (amountString.equals("")) {
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, "Didn't set amount of money", "Please enter your amount of money.");
        } else {
            uploadValueToAcc();
        }

    }   // Upload to account...

    private void uploadValueToAcc() {

        Log.d("June8th", "user ID ==>" + loginStrings[0]);
        Log.d("June8th", "Date ==>" + getIntent().getStringExtra("Date"));
        Log.d("June8th", "In_Out ==>" + inoutAnInt);
        Log.d("June8th", "Category ==>" + categoryString);
        Log.d("June8th", "Amount ==>" + amountString);

        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormEncodingBuilder()
                .add("isAdd", "true")
                .add("id_user", loginStrings[0])
                .add("Date", getIntent().getStringExtra("Date"))
                .add("In_Out", Integer.toString(inoutAnInt))
                .add("Category", categoryString)
                .add("Amount", amountString)
                .build();

        Request.Builder builder = new Request.Builder();
        Request request = builder.url("http://swiftcodingthai.com/pbru2/add_account_master.php").post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                finish();
            }
        });

    }

    public void clickBack(View view) {
        finish();
    }   //  Go back...

}   //  Main class...
