package com.example.slider;

public class User {

    String mUserName;
    String mUserEmail;
    String mUserPassWord;
    String mUserContacts;

    public User() {

    }

    public User(String mUserName, String mUserEmail, String mUserPassWord) {
        this.mUserName = mUserName;
        this.mUserEmail = mUserEmail;
        this.mUserPassWord = mUserPassWord;
        this.mUserContacts = null;
        setmUserContacts("1234566");
    }

    public String getmUserName() {
        return mUserName;
    }

    public String getmUserEmail() {
        return mUserEmail;
    }

    public String getmUserPassWord() {
        return mUserPassWord;
    }

    public String getmUserContacts() {
        return mUserContacts;
    }

    public void setmUserContacts(String mUserContacts) {
        this.mUserContacts = mUserContacts;
    }
}
