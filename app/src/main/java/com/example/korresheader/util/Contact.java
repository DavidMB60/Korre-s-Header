package com.example.korresheader.util;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

//This class is the model of the contacts stored in the system.
public class Contact implements Parcelable {

    //The name of this contact
    private final String name;

    //The phone number of the contact
    private final int phoneNumber;

    //The age of the contact
    private final int age;

    /**
     * Constructor
     * @param name The name of the new contact
     * @param phoneNumber The phone number of the new contact
     * @param age The age of the new contact
     */
    public Contact(@NonNull String name, int phoneNumber, int age) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.age = age;
    }

    protected Contact(Parcel in) {
        name = in.readString();
        phoneNumber = in.readInt();
        age = in.readInt();
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    /**
     * Returns in a String the name of the contact
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the phone number of the contact
     * @return The phone number
     */
    public int getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Returns the age of the contact
     * @return The age
     */
    public int getAge() {
        return age;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(phoneNumber);
        dest.writeInt(age);
    }
}
