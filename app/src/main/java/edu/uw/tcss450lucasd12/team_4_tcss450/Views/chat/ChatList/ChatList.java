package edu.uw.tcss450lucasd12.team_4_tcss450.Views.chat.ChatList;

import java.io.Serializable;

/**
 *
 * @author Paul Lee
 * @version Autumn 2022
 */
public class ChatList implements Serializable {
    private final String mTitle;
    private final String mRecentMessage;
    private final String mDate;

    public static class Builder {
        private final String mTitle;
        private String mRecentMessage = "";
        private String mDate = "";

        public Builder(String title) {
            this.mTitle = title;
        }

        public Builder addRecentMessage(final String val) {
            mRecentMessage = val;
            return this;
        }

        public Builder addDate(final String val) {
            mDate = val;
            return this;
        }

        public ChatList build() {
            return new ChatList(this);
        }

    }

    private ChatList(final Builder builder) {
        this.mTitle = builder.mTitle;
        this.mRecentMessage = builder.mRecentMessage;
        this.mDate = builder.mDate;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public String getRecentMessage() {
        return this.mRecentMessage;
    }

    public String getDate() {
        return this.mDate;
    }
}
