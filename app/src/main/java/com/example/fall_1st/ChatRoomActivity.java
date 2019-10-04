package com.example.fall_1st;



import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


        public class ChatRoomActivity extends AppCompatActivity {
            //items to display
            ArrayList<Message> messages = new ArrayList<>();

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
                    EditText input = findViewById(R.id.messageEnter);
                    String content = input.getText().toString();
                    messages.add(new Message(content,Message.SEND));
                    myAdapter.notifyDataSetChanged(); //update yourself
                    input.setText("");
                } );
                Button receiveButton = findViewById(R.id.recieveButton);
                receiveButton.setOnClickListener( clik -> {
                    EditText input = findViewById(R.id.messageEnter);
                    String content = input.getText().toString();
                    messages.add(new Message(content,Message.RECEIVE));
                    myAdapter.notifyDataSetChanged(); //update yourself
                    input.setText("");
                } );

            }

            //Need to add 4 functions here:
            private class MyListAdapter extends BaseAdapter {

                public int getCount() {  return messages.size();  } //This function tells how many objects to show

                public Message getItem(int position) { return messages.get(position);  }  //This returns the string at position p

                public long getItemId(int p) { return p; } //This returns the database id of the item at position p

                public View getView(int p, View recycled, ViewGroup parent)
                {

                    LayoutInflater inflater = getLayoutInflater();
                    View thisRow = null;


                    if(getItem(p).getType() == Message.SEND) {
                        thisRow = inflater.inflate(R.layout.activity_chatroom_send, null);
                    }else{
                        thisRow = inflater.inflate(R.layout.acitivity_chatroom_receive, null);

                    }

                    TextView itemField = thisRow.findViewById(R.id.messageTextView);
                    itemField.setText(getItem(p).getMessage());

                    return thisRow;
                }
            }
        }
