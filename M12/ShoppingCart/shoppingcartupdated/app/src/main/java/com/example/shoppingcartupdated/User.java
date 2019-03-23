package com.example.shoppingcartupdated;

public class User {
    String username;
    String mEmail;
    String mPassword;
    String mPhnNo;

    public User() {}

    public User(String username, String mEmail, String mPassword, String mPhnNo) {
        this.username = username;
        this.mEmail = mEmail;
        this.mPassword = mPassword;
        this.mPhnNo = mPhnNo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getmPhnNo() {
        return mPhnNo;
    }

    public void setmPhnNo(String mPhnNo) {
        this.mPhnNo = mPhnNo;
    }
}
