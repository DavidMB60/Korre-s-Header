package com.example.korresheader.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.korresheader.util.Contact;
import com.example.korresheader.util.ContactRepository;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.Disposable;

@HiltViewModel
public class HomeActivityViewModel extends ViewModel {

    //The LiveData for the contacts
    private final MutableLiveData<List<Contact>> contacts = new MutableLiveData<>();

    //The repository
    private final ContactRepository contactRepository;

    //The disposable for the Contacts
    private Disposable contactsDisposable;

    /**
     * Constructor
     */
    @Inject
    public HomeActivityViewModel(ContactRepository contactSQLiteHandler) {
        this.contactRepository = contactSQLiteHandler;
        startDisposable();
    }

    /**
     * This method updates the Contacts when the ContactRepository list is changed
     */
    private void updateContacts() {
        HashMap<String, Contact> hashMap = contactRepository
                .getContactMap().blockingLatest().iterator().next();
        // We add the Contact to the local contacts value
        List<Contact> listItems = hashMap.values().stream()
                .sorted(Comparator.comparing(o -> o.getName().toUpperCase()))
                .collect(Collectors.toList());
        contacts.postValue(listItems);
    }

    /**
     * Returns a LiveData with the current list of quickset items to show to the user.
     */
    public LiveData<List<Contact>> getContacts() {
        return contacts;
    }

    /**
     * This method starts the disposable
     */
    private void startDisposable() {
        contactsDisposable = contactRepository.getContactMap().subscribe(o -> updateContacts());
    }
}
