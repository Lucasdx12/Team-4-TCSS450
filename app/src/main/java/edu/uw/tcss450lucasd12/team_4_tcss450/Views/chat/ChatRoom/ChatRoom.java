package edu.uw.tcss450lucasd12.team_4_tcss450.Views.chat.ChatRoom;

import java.util.List;

public class ChatRoom {
    private String mMyMessage;
    private String mUser;
    private List<String> mOtherUsers;
    private String mOtherMessage;

    public static class Builder {
        private final String mUser;
        private final List<String> mOtherUsers;
        private String mMyMessage = "";
        private String mOtherMessage = "";

        public Builder(String user, List<String> otherUsers) {
            this.mUser = user;
            this.mOtherUsers = otherUsers;
        }

        public Builder addMyMessage(final String val) {
            mMyMessage = val;
            return this;
        }

        public Builder addOtherMessage(final String val) {
            mOtherMessage = val;
            return this;
        }

        public ChatRoom build() {
            return new ChatRoom(this);
        }
    }

    private ChatRoom(final Builder builder) {
        this.mUser = builder.mUser;
        this.mOtherUsers = builder.mOtherUsers;
        this.mMyMessage = builder.mMyMessage;
        this.mOtherMessage = builder.mOtherMessage;
    }

    public String getUser() {
        return this.mUser;
    }

    public List<String> getOtherUsers() {
        return this.mOtherUsers;
    }

    public String getMyMessage() {
        return this.mMyMessage;
    }

    public String getOtherMessage() {
        return this.mOtherMessage;
    }
}
