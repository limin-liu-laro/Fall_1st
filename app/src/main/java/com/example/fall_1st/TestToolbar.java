package com.example.fall_1st;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;


public class TestToolbar extends AppCompatActivity {
        String a="You clicked on the overflow menu";
        EditText edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);
        Toolbar tBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tBar);
         edit= (EditText)findViewById(R.id.view_edit_text);

    }
//
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu items for use in the action bar
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu, menu);
//
//        return true;
//    }
//
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            //what to do when the menu item is selected:
//            case R.id.trashcan_menu:
//                Toast.makeText(this, "You clicked on the overflow menu", Toast.LENGTH_LONG).show();
//                break;
//            case R.id.edit_menu:
//                Toast.makeText(this, "This is the initial message", Toast.LENGTH_LONG).show();
//                break;
//            case R.id.undo_menu:
//                Toast.makeText(this,"Go Back?",Toast.LENGTH_LONG).show();
//                break;
//        }
//        return true;
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.trashcan_menu:
                //alert();
                Toast.makeText(this, a, Toast.LENGTH_SHORT).show();
                break;

            case R.id.edit_menu:
                Toast.makeText(this, "This is the initial message", Toast.LENGTH_SHORT).show();
                break;

            case R.id.undo_menu:
                Snackbar.make(findViewById(R.id.undo_menu),"Go Back?",Snackbar.LENGTH_LONG).show();
                break;

            case R.id.mailbox_menu:
                //Toast.makeText(this, "Go Back?", Toast.LENGTH_SHORT).show();
                alert();
                break;
        }
        return true;
    }

    public void alert()
    {
        View middle = getLayoutInflater().inflate(R.layout.view_extra_stuff, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(middle);
        builder.setMessage("The Message")
                .setPositiveButton("Positive", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.d("print at overflow","");
                        setMessage();
                        finish();
                    }
                })
                .setNegativeButton("Negative", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // What to do on Cancel

                    }
                });

        builder.create().show();
    }

    void setMessage(){
        a=edit.getText().toString();
        Log.d("default message","a="+a);
    }
}
