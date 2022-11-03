package edu.uw.tcss450lucasd12.team_4_tcss450.Views.contacts;

import java.io.Serializable;

import edu.uw.tcss450lucasd12.team_4_tcss450.Views.chat.ChatList.ChatList;

public class ContactsList implements Serializable {
    private final String mNickname;
    private final String mEmail;

    public static class Builder {
        private final String mNickname;
        private final String mEmail;

        public Builder(String nickName, String email) {
            this.mNickname = nickName;
            this.mEmail = email;
        }

//        public Builder addEmail(final String val) {
//            mEmail = val;
//            return this;
//        }

        public ContactsList build() {
            return new ContactsList(this);
        }

    }

    private ContactsList(final Builder builder) {
        this.mNickname = builder.mNickname;
        this.mEmail = builder.mEmail;
    }

    public String getNickname() {
        return this.mNickname;
    }

    public String getEmail() {
        return this.mEmail;
    }
}
