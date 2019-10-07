package com.example.fall_1st;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Message {
    private String message;
    private boolean isSent;
    private long id;

    public Message(String message,boolean isSent, long id) {
        this.message = message;
        this.isSent=isSent;
        this.id=id;
    }

    /**Chaining constructor: */
    public Message(String message, boolean isSent) {
        this(message, isSent, 0);
    }

    public String getMessage() {
        return message;
    }

    public long getId(){
        return id;
    }

    public void setSent(boolean sent) {
        isSent = sent;
    }

    public boolean isSent() {
        return isSent;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public void setId(long id){
        this.id=id;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }
}
