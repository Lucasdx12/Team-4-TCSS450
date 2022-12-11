package edu.uw.tcss450lucasd12.team_4_tcss450.Views.contacts;

import java.io.Serializable;

public class Contact implements Serializable {

    private final String mNickname;
    private final String mEmail;
    private int mFriendStatus;
    private int mBlockedStatus;

    public static class Builder {
        private final String mNickname;
        private final String mEmail;
        private int mFriendStatus;
        private int mBlockedStatus;

        public Builder(String nickName, String email, int friendStatus, int blockedStatus) {
            this.mNickname = nickName;
            this.mEmail = email;
            this.mFriendStatus = friendStatus; //0=not-friend, 1=friend
            this.mBlockedStatus = blockedStatus; //0=not-blocked, 1=blocked
        }

//        public Builder addEmail(final String val) {
//            mEmail = val;
//            return this;
//        }

        public Builder addFriendStatus(final int val) {
            mFriendStatus = val;
            return this;
        }

        public Builder addBlockedStatus(final int val) {
            mBlockedStatus = val;
            return this;
        }

        public Contact build() {
            return new Contact(this);
        }

    }

    private Contact(final Builder builder) {
        this.mNickname = builder.mNickname;
        this.mEmail = builder.mEmail;
        this.mFriendStatus = builder.mFriendStatus;
        this.mBlockedStatus = builder.mBlockedStatus;
    }

    public Contact(String username, String email, int friendstatus, int blockedstatus) {
        mNickname = username;
        mEmail = email;
        mFriendStatus = friendstatus;
        mBlockedStatus = blockedstatus;
    }

    public String getNickname() {
        return this.mNickname;
    }

    public String getEmail() {
        return this.mEmail;
    }

    public int getFriendStatus() {
        return this.mFriendStatus;
    }

    public void setFriendStatus(int newStatus) {
        this.mFriendStatus = newStatus;
    }

    public int getBlockedStatus() {
        return this.mBlockedStatus;
    }

    public void setBlockedStatus(int newStatus) {
        this.mBlockedStatus = newStatus;
    }
}
