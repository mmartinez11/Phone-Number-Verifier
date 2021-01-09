package com.example.project1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActivityTwo extends Activity {

    //Layout Utilities
    private EditText act2TextField1;
    private EditText act2TextField2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_two);

        act2TextField1 = (EditText) findViewById(R.id.activity2_TextField1);
        act2TextField1.setEnabled(false);

        act2TextField2 = (EditText) findViewById(R.id.activity_TextField2);

        //Listens when the enter key is used on the soft keyboard
        act2TextField2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int m, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN && m == KeyEvent.KEYCODE_ENTER)
                {
                    //Get Phone Number from text field
                    String phoneNumber = act2TextField2.getText().toString();
                    //Validate Phone Number
                    boolean flagN = validatePhoneNumber(phoneNumber);
                    //Send the Phone Number back to Activity 1
                    sendPhoneNumber(flagN, phoneNumber);

                    return true;
                }
                return false;
            }
        });
    }

    //Method to validate the phone number
    public boolean validatePhoneNumber(String phoneNumber)
    {
        //The Phone Number must have a minimum length of 10 characters
        if(phoneNumber.length() >= 10)
        {
            //Set the Regex expression
            Pattern pattern = Pattern.compile("^\\+?[1]? ?\\(?[0-9]{3}\\)? ?-?[0-9]{3} ?-?[0-9]{4}");
            Matcher matcher = pattern.matcher(phoneNumber);

                //If the Regex is false then the phone number is not valid
                if(!matcher.matches()) {
                    return false;
                }
        }
        else {
            return false;
        }

        //Phone Number is valid
        return true;
    }

    //Method sends the Phone Number back to Activity 1
    public void sendPhoneNumber(boolean flag, String phoneNumber) {

        Intent newIntent = new Intent();
        //Store the phone number in the intent
        newIntent.putExtra("NUMBER", phoneNumber);

        //If the phone number is valid then use the result code (-1)
        if(flag) {
            setResult(Activity.RESULT_OK, newIntent);
        }
        //If the phone number is not valid then use the result code (0)
        else {
            setResult(Activity.RESULT_CANCELED, newIntent);
        }

        //Return to the activity that started activity 2
        finish();
    }

    @Override
    public void onBackPressed() {

        //Prevents Null Exception when back button is pressed 
        Intent newIntent = new Intent();
        newIntent.putExtra("NUMBER", "  ");
        setResult(Activity.RESULT_CANCELED, newIntent);
        finish();

    }
}
