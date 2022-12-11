//package edu.uw.tcss450lucasd12.team_4_tcss450.Views.contacts;
//
//import java.util.Arrays;
//import java.util.List;
//
//public class ContactsListGenerator {
//    private static final Contact[] CONTACTS;
//    private static final String[] mNicknames = {"Paul", "Lucas", "Alexz", "Alec", "Steph", "David", "Charles"};
//
//    private static final String[] mEmail = {"plee83@uw.edu", "lucasd12@uw.edu", "alexzr@uw.edu", "dowtya@uw.edu", "stephg02@uw.edu", "DavidDude@yahoo.com", "CharlesB@gmail.com"};
//
//    private static int mFriendStatus = 1; //0=not-friend, 1=friend, 2=blocked
//
//    public static final int PROFILES = 7;
//
//    static {
//        CONTACTS = new Contact[PROFILES];
//        for (int i = 0; i < CONTACTS.length; i++) {
//            CONTACTS[i] = new Contact
//                    .Builder(mNicknames[i], mEmail[i], mFriendStatus)
//                    .build();
//        }
//    }
//
//    public static List<Contact> getContactsList() {
//        return Arrays.asList(CONTACTS);
//    }
//
//    public static Contact[] getContacts() {
//        return Arrays.copyOf(CONTACTS, CONTACTS.length);
//    }
//
//    private ContactsListGenerator() {
//
//    }
//}
