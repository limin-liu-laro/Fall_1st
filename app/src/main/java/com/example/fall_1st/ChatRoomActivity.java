/*package com.example.fall_1st;



import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.Arrays;


        public class ChatRoomActivity extends AppCompatActivity {
            //items to display
            ArrayList<Message> messages = new ArrayList<>(Arrays.asList("Item 1", "Item 2", "Item 3",
                    "Item 4", "Item 5", "Item 6") );

            BaseAdapter myAdapter;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_chat_room);

                //You only need 2 lines in onCreate to actually display data:
                ListView theList = findViewById(R.id.theList);
                theList.setAdapter( myAdapter = new MyListAdapter() );
                theList.setOnItemClickListener( ( parent,  view,  position,  id) ->{
                    Log.i("CLicked", "You clicked item:" + position);

                });


                Button sendButton = findViewById(R.id.sendButton);
                sendButton.setOnClickListener( clik -> {
                    objects.add("Item " + (1 + objects.size()));
                    myAdapter.notifyDataSetChanged(); //update yourself
                } );
                Button receiveButton = findViewById(R.id.recieveButton);
                sendButton.setOnClickListener( clik -> {
                    objects.add("Item " + (1 + objects.size()));
                    myAdapter.notifyDataSetChanged(); //update yourself
                } );

            }



            //Need to add 4 functions here:
            private class MyListAdapter extends BaseAdapter {

                public int getCount() {  return objects.size();  } //This function tells how many objects to show

                public String getItem(int position) { return objects.get(position);  }  //This returns the string at position p

                public long getItemId(int p) { return p; } //This returns the database id of the item at position p

                public View getView(int p, View recycled, ViewGroup parent)
                {

                    LayoutInflater inflater = getLayoutInflater();
                    View thisRow = recycled;

                    if(thisRow == null) {
                        thisRow = inflater.inflate(R.layout.table_row_layout, null);
                    }

                    TextView itemField = thisRow.findViewById(R.id.itemField);
                    itemField.setText(getItem(p));

                    TextView numberField = thisRow.findViewById(R.id.numberField);
                    numberField.setText(Integer.toString(p));

                    return thisRow;
                }
            }
        }*/
