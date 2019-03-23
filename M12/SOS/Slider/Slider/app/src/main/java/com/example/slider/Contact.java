package com.example.slider;

import android.os.Parcel;
import android.os.Parcelable;

public class Contact implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };
    String name;
    String phnNO;

    public Contact(String name, String phnNO) {
        this.name = name;
        this.phnNO = phnNO;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.phnNO);

    }

    public Contact(Parcel in) {
        this.name = in.readString();
        this.phnNO = in.readString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhnNO() {
        return phnNO;
    }

    public void setPhnNO(String phnNO) {
        this.phnNO = phnNO;
    }
}
