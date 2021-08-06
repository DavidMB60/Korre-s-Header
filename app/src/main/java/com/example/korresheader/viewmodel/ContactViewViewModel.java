package com.example.korresheader.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.korresheader.Contact;
import com.example.korresheader.ContactRepository;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import io.reactivex.rxjava3.disposables.Disposable;

public class ContactViewViewModel extends ViewModel {

    //The name of the contact
    private String contactName;

    //The phone number of the contact
    private int contactPhoneNumber;

    //The age of the contact
    private int contactAge;

    //The repository to save and delete contacts
    private ContactRepository contactRepository;

    //The LiveData with the data of the opened Contact
    private MutableLiveData<Contact> contactMutableLiveData = new MutableLiveData<>();

    /**
     * Constructor
     */
    public ContactViewViewModel() {

    }

    /**
     * Returns the name of the contact
     * @return The name of the contact
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Sets the new value of the Contact name
     * @param contactName The new name
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Returns the phone number of the contact
     * @return The phone number
     */
    public int getContactPhoneNumber() {
        return contactPhoneNumber;
    }

    /**
     * Sets the new value of the phone number name
     * @param contactPhoneNumber The new phone number
     */
    public void setContactPhoneNumber(int contactPhoneNumber) {
        this.contactPhoneNumber = contactPhoneNumber;
    }

    /**
     * Returns the age of the contact
     * @return The age
     */
    public int getContactAge() {
        return contactAge;
    }

    /**
     * Establish the new age of the contact
     * @param contactAge The new age
     */
    public void setContactAge(int contactAge) {
        this.contactAge = contactAge;
    }

    /**
     * Prepares a new Contact with the data
     * @return The Contact
     */
    private Contact prepareContact() {
        return new Contact(contactName, contactPhoneNumber, contactAge);
    }

    /**
     * Saves the current Contact
     * @return True if saved successfully, false if not
     */
    public boolean saveContact() {
       //First we check if there is a difference between the new or old data to decide to save or not
       if (contactName != contactMutableLiveData.getValue().getName() &&
       contactPhoneNumber != contactMutableLiveData.getValue().getPhoneNumber() &&
       contactAge != contactMutableLiveData.getValue().getAge()) {
           //We check if the Contact exist in the DB; in case it does we delete it
           if (contactRepository.checkIfContactExist(contactMutableLiveData.getValue().getName())) {
               contactRepository.deleteContact(contactMutableLiveData.getValue().getName());
           }
           contactRepository.setContact(prepareContact());
           return true;
       } else {
           return false;
       }
    }

    /**
     * Deletes the current contact
     */
    public void deleteContact() {
        contactRepository.deleteContact(contactName);
    }

    /**
     * Establish the value for the contact
     * @param contact The contact
     */
    public void setContact(Contact contact) {
        contactMutableLiveData.setValue(contact);
    }

    /**
     * Set the context for the Repository
     * @param context The context
     */
    public void setContext(Context context) {
        contactRepository = new ContactRepository(context);
    }
}
