package com.example.korresheader.util;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.korresheader.SQLiteHandlerModule;

import java.util.HashMap;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.subjects.BehaviorSubject;

@Singleton
public class ContactRepository {

    //This is the BehaviourSubject for the HashMap of the contacts in the DB
    private final BehaviorSubject<HashMap<String, Contact>> contactMap =
            BehaviorSubject.create();

    private HashMap<String, Contact> bakingContactMap;

    //The SQLiteHandler
    private SQLiteHandler sqLiteHandler;

    /**
     * Constructor
     * @param sqLiteHandler
     */
    @Inject
    public ContactRepository(@SQLiteHandlerModule.Contact SQLiteHandler sqLiteHandler) {
        this.sqLiteHandler = sqLiteHandler;
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
        bakingContactMap.remove(name);
        contactMap.onNext(bakingContactMap);
    }

    /**
     * This checks if the given contact by name exist in the DB
     * @param name The name of the contact
     * @return True if exist or false if not.
     */
    public boolean checkIfContactExist(@NonNull String name) {
        return bakingContactMap.containsKey(name);
    }

    /**
     * @return The quicksetList
     */
    public BehaviorSubject<HashMap<String, Contact>> getContactMap() {
        return contactMap;
    }
}
