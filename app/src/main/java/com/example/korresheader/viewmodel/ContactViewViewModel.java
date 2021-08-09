package com.example.korresheader.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.korresheader.util.Contact;
import com.example.korresheader.util.ContactRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
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
    @Inject
    public ContactViewViewModel(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
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
        //First we check the LiveData, in case it's null then we know that it's a new Contact for sure
        if (contactMutableLiveData.getValue() == null) {
            contactRepository.setContact(prepareContact());
            return true;
        } else {
            //We check if the Contact exist in the DB; in case it does we delete it
            if (contactRepository.checkIfContactExist(contactMutableLiveData.getValue().getName())) {
                contactRepository.deleteContact(contactMutableLiveData.getValue().getName());
            }
            contactRepository.setContact(prepareContact());
            return true;
        }
    }

    /**
     * Deletes the current contact
     */
    public void deleteContact() {
        contactRepository.deleteContact(contactMutableLiveData.getValue().getName());
    }

    /**
     * Establish the value for the contact
     * @param contact The contact
     */
    public void setContact(Contact contact) {
        contactMutableLiveData.setValue(contact);
    }

    /**
     * Returns the LiveData of the actual Contact
     * @return The contact
     */
    public MutableLiveData<Contact> getContactMutableLiveData() {
        return contactMutableLiveData;
    }
}
