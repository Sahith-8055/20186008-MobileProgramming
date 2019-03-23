package com.example.shoppingcartupdated;

import android.util.Log;

import java.util.HashMap;

public class userCart {
    String phnNo;
    String mailId;
    String userName;
    HashMap<String, Long> map;
    public userCart() {}
    public userCart(String phnNo, String mailId, String userName, HashMap<String, Long> map) {
        this.phnNo = phnNo;
        this.mailId = mailId;
        this.userName = userName;
        this.map = map;
    }

    public String getPhnNo() {
        return phnNo;
    }

    public void setPhnNo(String phnNo) {
        this.phnNo = phnNo;
    }

    public String getMailId() {
        return mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public HashMap<String, Long> getMap() {
        return map;
    }

    public void setMap(HashMap<String, Long> map) {
        this.map = map;
    }
}
