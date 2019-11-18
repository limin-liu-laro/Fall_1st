package com.example.fall_1st;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private static final String ACTIVITY_NAME = "MainActivity";
    private String email;
    private SharedPreferences prefs;
    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor editor = prefs.edit();//use SharedPreferences interface to save the user's email
        editor.putString("ReserveName", email);// set the Key to ReserveName email address
        editor.commit();
        Log.d(ACTIVITY_NAME,"onpause email="+email);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // step one: super
        setContentView(R.layout.activity_main); // step two : setContentView
        prefs = getSharedPreferences("FileName", MODE_PRIVATE); // prefs : getSharedPreferences from FileName
        String previous = prefs.getString("ReserveName", "Default Value"); // get Key: getString from ReserveName

        EditText emailText = findViewById(R.id.email); //
       Log.d(ACTIVITY_NAME,emailText.toString());
        Log.d(ACTIVITY_NAME,emailText.getText().toString());
        this.email = emailText.getText().toString();

        Button loginButton = (Button)findViewById(R.id.loginButton);

        emailText.setText(previous);
        loginButton.setOnClickListener(clk -> {
            Log.d(ACTIVITY_NAME,emailText.toString());
            Log.d(ACTIVITY_NAME,emailText.getText().toString());
            this.email = emailText.getText().toString();
            Intent goToPage2 = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(goToPage2);

    });
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.e(ACTIVITY_NAME,"onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(ACTIVITY_NAME,"onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(ACTIVITY_NAME,"onDestroy");
    }

/*    @Override
   protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
  }*/
}