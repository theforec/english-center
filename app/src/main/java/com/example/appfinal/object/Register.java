package com.example.appfinal.object;

import android.os.Parcel;
import android.os.Parcelable;

public class Register implements Parcelable {
    private String Name, Email, Phone, Address;

    public Register() {
    }

    public Register(String name, String email, String phone, String address) {
        Name = name;
        Email = email;
        Phone = phone;
        Address = address;
    }

    protected Register(Parcel in) {
        Name = in.readString();
        Email = in.readString();
        Phone = in.readString();
        Address = in.readString();
    }

    public static final Creator<Register> CREATOR = new Creator<Register>() {
        @Override
        public Register createFromParcel(Parcel in) {
            return new Register(in);
        }

        @Override
        public Register[] newArray(int size) {
            return new Register[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Name);
        dest.writeString(Email);
        dest.writeString(Phone);
        dest.writeString(Address);
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
