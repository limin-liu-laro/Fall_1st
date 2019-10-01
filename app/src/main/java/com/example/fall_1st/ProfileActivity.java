package com.example.fall_1st;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class ProfileActivity extends AppCompatActivity {
    private static final String ACTIVITY_NAME = "ProfileActivity";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        prefs = getSharedPreferences("FileName", MODE_PRIVATE); //get sharedPreferences from previous page (email)
        String previous = prefs.getString("ReserveName", "Default Value"); //find the key
        Log.e(ACTIVITY_NAME,"previous="+previous);
        EditText emailEdit= findViewById(R.id.emailEdit); //find the id of the button
        emailEdit.setText(previous);

        ImageButton mImageButton=findViewById(R.id.photoButton);
        mImageButton.setOnClickListener(clk -> {
            dispatchTakePictureIntent(); // onClick take action to dispatchTakePictureIntent()
        });

/*        Button chattingButton=findViewById(R.id.chattingButton);
        chattingButton.setOnClickListener(clk ->{
            Intent goToPage3 = new Intent(ProfileActivity.this, ChatRoomActivity.class);
            startActivity(goToPage3);

        });*/

    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ImageButton mImageButton=findViewById(R.id.photoButton);
            mImageButton.setImageBitmap(imageBitmap);
        }
    }
  @Override
    protected void onStart() {
        super.onStart();
        Log.e(ACTIVITY_NAME,"onStart");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.e(ACTIVITY_NAME,"onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(ACTIVITY_NAME,"onPause");
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


}
