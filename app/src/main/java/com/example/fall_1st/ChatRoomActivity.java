package com.example.fall_1st;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import java.util.Arrays;


public class ChatRoomActivity extends AppCompatActivity {
            //items to display
            public static final String ITEM_SELECTED = "ITEM";
            public static final String ITEM_POSITION = "POSITION";
            public static final String ITEM_ID = "ID";
            public static final int EMPTY_ACTIVITY = 345;
            ArrayList<Message> messages = new ArrayList<>();
            private static final String ACTIVITY_NAME = "ChatRoomActivity";
            //ListView theList;

            BaseAdapter myAdapter;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.framelayout);
                boolean isTablet = findViewById(R.id.gotoToolbarButton) != null;
                //You only need 2 lines in onCreate to actually display data:
                ListView theList = findViewById(R.id.theList);
                theList.setAdapter( myAdapter = new MyListAdapter() );
                theList.setOnItemClickListener( ( parent,  view,  position,  id) ->{
                    Log.i("CLicked", "You clicked item:" + position);

                });

                DatabaseOpenHelper dbOpener = new DatabaseOpenHelper(this);
                SQLiteDatabase db = dbOpener.getWritableDatabase();

                //query all the results from the database:
                //distinct:false , no distinct.
                //equal to: select * from table_name;
                String [] columns = {DatabaseOpenHelper.COL_ID, DatabaseOpenHelper.COL_MESSAGE, DatabaseOpenHelper.COL_IS_SENT};

                Cursor results = db.query(false, DatabaseOpenHelper.TABLE_NAME, columns, null, null, null, null, null, null);
                //=========================================================
                this.printCursor(results);
                //find the column indices:
                int isSentlColumnIndex = results.getColumnIndex(DatabaseOpenHelper.COL_IS_SENT);
                int messageColIndex = results.getColumnIndex(DatabaseOpenHelper.COL_MESSAGE);
                int idColIndex = results.getColumnIndex(DatabaseOpenHelper.COL_ID);


                results = db.query(false, DatabaseOpenHelper.TABLE_NAME, columns, null, null, null, null, null, null);

                //iterate over the results, return true if there is a next item:
                while(results.moveToNext())
                {
                    String message = results.getString(messageColIndex);
                    boolean isSent = Boolean.valueOf(results.getString(isSentlColumnIndex));
                    long id = results.getLong(idColIndex);

                    //add the new Message to the array list:
                    messages.add(new Message(message, isSent, id));
                }


                Button sendButton = findViewById(R.id.sendButton);
                sendButton.setOnClickListener( clik -> {
                    EditText input = findViewById(R.id.messageEnter);
                    String content = input.getText().toString();

                    //add to the database and get the new ID
                    ContentValues newRowValues = new ContentValues();
                    //put string name in the NAME column:
                    newRowValues.put(DatabaseOpenHelper.COL_IS_SENT, "true");
                    //put string email in the EMAIL column:
                    newRowValues.put(DatabaseOpenHelper.COL_MESSAGE, content);
                    //insert in the database:
                    long newId = db.insert(DatabaseOpenHelper.TABLE_NAME, null, newRowValues);

                    //now you have the newId, you can create the Contact object
                    Message newMessage = new Message(content, true, newId);

                    messages.add(newMessage);


                    myAdapter.notifyDataSetChanged(); //update yourself
                    input.setText("");// content clear
                } );
               /* Button receiveButton = findViewById(R.id.recieveButton);
                receiveButton.setOnClickListener( clik -> {
                    EditText input = findViewById(R.id.messageEnter);
                    String content = input.getText().toString();
                    //add to the database and get the new ID
                    ContentValues newRowValues = new ContentValues();
                    //put string name in the NAME column:
                    newRowValues.put(DatabaseOpenHelper.COL_IS_SENT, "false");
                    //put string email in the EMAIL column:
                    newRowValues.put(DatabaseOpenHelper.COL_MESSAGE, content);
                    //insert in the database:
                    long newId = db.insert(DatabaseOpenHelper.TABLE_NAME, null, newRowValues);

                    //now you have the newId, you can create the Contact object
                    Message newMessage = new Message(content, false, newId);

                    messages.add(newMessage);
                    myAdapter.notifyDataSetChanged(); //update yourself
                    input.setText(""); // content clear
                } );*/
                theList.setAdapter(myAdapter = new MyListAdapter());

                theList.setOnItemClickListener((list, item, position, id) -> {

                    Bundle dataToPass = new Bundle();
                    dataToPass.putString(ITEM_SELECTED, messages.get(position).toString());
                    dataToPass.putInt(ITEM_POSITION, position);
                    dataToPass.putLong(ITEM_ID, id);

                    if (isTablet) {
                        DetailFragment dFragment = new DetailFragment(); //add a DetailFragment
                        dFragment.setArguments(dataToPass); //pass it a bundle for information
                        dFragment.setTablet(true);  //tell the fragment if it's running on a tablet or not
                        getSupportFragmentManager()
                                .beginTransaction()
                                .add(R.id.fragmentLocation, dFragment) //Add the fragment in FrameLayout
                                .addToBackStack("AnyName") //make the back button undo the transaction
                                .commit(); //actually load the fragment.
                    } else //isPhone
                    {
                        Intent nextActivity = new Intent(ChatRoomActivity.this, EmptyActivity.class);
                        nextActivity.putExtras(dataToPass); //send data to next activity
                        startActivityForResult(nextActivity, EMPTY_ACTIVITY); //make the transition
                    }
                });

            }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EMPTY_ACTIVITY) {
            if (resultCode == RESULT_OK) //if you hit the delete button instead of back button
            {
                long id = data.getLongExtra(ITEM_ID, 0);
                deleteMessageId((int) id);
            }
        }
    }

    public void deleteMessageId(int id) {
        Log.i("Delete this message", " id=" + id);
        messages.remove(id);
        myAdapter.notifyDataSetChanged();
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


                    if(getItem(p).isSent()) {
                        thisRow = inflater.inflate(R.layout.activity_chatroom_send, null);
                    }else{
                        thisRow = inflater.inflate(R.layout.acitivity_chatroom_receive, null);

                    }

                    TextView itemField = thisRow.findViewById(R.id.messageTextView);
                    itemField.setText(getItem(p).getMessage());

                    return thisRow;
                }

            }
            private void printCursor(Cursor c){
                Log.i(ACTIVITY_NAME," Version number is :"+ DatabaseOpenHelper.VERSION_NUM);
                Log.i(ACTIVITY_NAME," Number of columns is :"+c.getColumnCount());
                Log.i(ACTIVITY_NAME," Name of columns  is: "+ Arrays.toString(c.getColumnNames()));
                Log.i(ACTIVITY_NAME," Number of results is " + c.getCount());

                //iterate over the results, return true if there is a next item:
                c.moveToFirst();
                while(!c.isAfterLast())

                {
                    int isSentlColumnIndex = c.getColumnIndex(DatabaseOpenHelper.COL_IS_SENT);
                    int messageColIndex = c.getColumnIndex(DatabaseOpenHelper.COL_MESSAGE);
                    int idColIndex = c.getColumnIndex(DatabaseOpenHelper.COL_ID);
                    Log.i(ACTIVITY_NAME," Row No." + c.getString(idColIndex)+" "+c.getString(messageColIndex)+" "+c.getString(isSentlColumnIndex));
                    c.moveToNext();
                }


            }
        }
