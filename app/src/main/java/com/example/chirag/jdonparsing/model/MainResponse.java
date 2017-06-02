package com.example.chirag.jdonparsing.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by chirag on 27-Apr-17.
 */

public class MainResponse implements Parcelable {
    private int total;
    private ArrayList<User> users = new ArrayList<>();

    public MainResponse() {

    }


    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.total);
        dest.writeList(this.users);
    }

    public MainResponse(Parcel in) {
        this.total = in.readInt();
        this.users = new ArrayList<User>();
        in.readList(this.users, User.class.getClassLoader());
    }

    public static final Parcelable.Creator<MainResponse> CREATOR = new Parcelable.Creator<MainResponse>() {
        @Override
        public MainResponse createFromParcel(Parcel source) {
            return new MainResponse(source);
        }

        @Override
        public MainResponse[] newArray(int size) {
            return new MainResponse[size];
        }
    };
}
