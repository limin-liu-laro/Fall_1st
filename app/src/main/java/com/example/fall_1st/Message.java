package com.example.fall_1st;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Message {
    private String message;
    private int type;
    public final static int SEND =1;
    public final static int RECEIVE=2;

    public Message(String message,int type) {
        this.message = message;
        this.type=type;
    }

    public String getMessage() {
        return message;
    }

    public int getType() {
        return type;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setType(int type) {
        this.type = type;
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
