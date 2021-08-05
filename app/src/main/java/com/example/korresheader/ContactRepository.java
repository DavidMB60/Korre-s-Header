package com.example.korresheader;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.HashMap;

import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class ContactRepository {

    //This is the BehaviourSubject for the HashMap of the contacts in the DB
    private final BehaviorSubject<HashMap<String, Contact>> contactMap =
            BehaviorSubject.create();

    private HashMap<String, Contact> bakingContactMap;

    //The SQLiteHandler
    private SQLiteHandler sqLiteHandler;

    /**
     * Constructor
     * @param context The context for the SQLite
     */
    public ContactRepository(Context context) {
        this.sqLiteHandler = new SQLiteHandler(context);
        bakingContactMap = new HashMap<>();
        getAllContacts();
    }

    /**
     * We send the order to get all contacts from the SQLiteHandler
     */
    private void getAllContacts() {
        bakingContactMap = sqLiteHandler.getAllContacts();
        contactMap.onNext(bakingContactMap);
    }

    /**
     * We save a new contact into the DB
     * @param contact The contact to save
     */
    public void setContact(@NonNull Contact contact) {
        sqLiteHandler.addContact(contact);
        bakingContactMap.put(contact.getName(), contact);
        contactMap.onNext(bakingContactMap);
    }

    /**
     * Deletes the contact by the given name
     * @param name The name of the contact to remove
     */
    public void deleteContact(@NonNull String name) {
        sqLiteHandler.deleteContact(name);
    }

    /**
     * @return The quicksetList
     */
    public BehaviorSubject<HashMap<String, Contact>> getContactMap() {
        return contactMap;
    }
}
