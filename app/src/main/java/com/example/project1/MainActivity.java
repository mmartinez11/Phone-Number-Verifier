package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;


public class MainActivity extends Activity
{

    //Layout Utilities
    private Button act1Button2;
    private EditText act1TextField;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        act1Button2 = (Button) findViewById(R.id.activity_button2);

        act1TextField = (EditText) findViewById(R.id.activity1_TextField);
        act1TextField.setEnabled(false);

    }

    //Method to go to Activity Two
    public void goToActivity2(View v) {

        Intent intent = new Intent(MainActivity.this, ActivityTwo.class);
        startActivityForResult(intent, 1);
    }

    protected void onActivityResult(int code, final int result_code, Intent i) {
        super.onActivityResult(code, result_code, i);

            //Get Phone Number String from Intent
            final String result = i.getStringExtra("NUMBER");

        //--------------------Debuging Tools---------------------------
        Log.i("Bug1", "My Result Code " + result_code);
        Log.i("Bug1", "This is the Phone Number " + result);
        //----------------------------------------------------------

        //Show the User the Phone Number they picked
        act1TextField.setText("Your Number: " + result);

        act1Button2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            //Try to catch any exceptions
            try {

                //If the phone number is not valid display Toast Message
                if (result_code == 0) {
                    Toast.makeText(MainActivity.this, result + " Is Not a Valid Number", Toast.LENGTH_LONG).show();
                }
                //If the phone number is valid then use an implicit intent to find the dialer application
                else if (result_code == -1){

                    Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + result));

                    if (phoneIntent.resolveActivity(getPackageManager()) != null) {

                        //Call the dialer application
                        startActivity(phoneIntent);

                        Log.i("Bug1", "Dialer Application has been called");
                    }

                }

            }catch (Exception e){
                Log.e("Bug1", e.toString());
            }
            }
        });
    }
}