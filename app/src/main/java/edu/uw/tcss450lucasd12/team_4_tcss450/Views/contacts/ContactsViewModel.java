package edu.uw.tcss450lucasd12.team_4_tcss450.Views.contacts;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ContactsViewModel extends ViewModel {
    private final MutableLiveData<ContactsList> selectedContact = new MutableLiveData<>();

    public void selectContact(ContactsList contacts) {
        selectedContact.setValue(contacts);
    }

    public LiveData<ContactsList> getSelectedContact() {
        return selectedContact;
    }
}
