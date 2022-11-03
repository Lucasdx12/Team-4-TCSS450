package edu.uw.tcss450lucasd12.team_4_tcss450.Views.contacts;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import edu.uw.tcss450lucasd12.team_4_tcss450.Views.chat.ChatList.ChatList;

public class ContactsListGenerator {
    private static final ContactsList[] CONTACTS;
    private static final String[] mNicknames = {"Paul", "Lucas", "Alexz", "Alec", "Steph", "David", "Charles"};

    private static final String[] mEmail = {"plee83@uw.edu", "lucasd12@uw.edu", "alexzr@uw.edu", "dowtya@uw.edu", "stephg02@uw.edu", "DavidDude@yahoo.com", "CharlesB@gmail.com"};

    public static final int PROFILES = 7;

    static {
        CONTACTS = new ContactsList[PROFILES];
        for (int i = 0; i < CONTACTS.length; i++) {
            CONTACTS[i] = new ContactsList
                    .Builder(mNicknames[i], mEmail[i])
                    .build();
        }
    }

    public static List<ContactsList> getContactsList() {
        return Arrays.asList(CONTACTS);
    }

    public static ContactsList[] getContacts() {
        return Arrays.copyOf(CONTACTS, CONTACTS.length);
    }

    private ContactsListGenerator() {

    }
}
