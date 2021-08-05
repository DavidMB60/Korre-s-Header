package com.example.korresheader;

import androidx.annotation.NonNull;

//This class is the model of the contacts stored in the system.
public class Contact {

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
}
