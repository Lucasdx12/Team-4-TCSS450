package edu.uw.tcss450lucasd12.team_4_tcss450.Views.chat.ChatList;

import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * ChatList of each individual chats room that the user is in,
 * which will be displayed in the chat list.
 *
 * @author Paul Lee
 * @version Autumn 2022
 */
public final class ChatList implements Serializable {
    private final int mChatId;
    private final String mTitle;

    public ChatList(int chatId, String title) {
        this.mChatId = chatId;
        this.mTitle = title;
    }

    public int getChatId() {
        return this.mChatId;
    }

    public String getTitle() {
        return this.mTitle;
    }

    /**
     * Provides equality solely based on chatId.
     * @param other the other object to check for equality.
     * @return true if other message ID matches this message ID, false otherwise.
     */
    @Override
    public boolean equals(@Nullable Object other) {
        boolean result = false;
        if (other instanceof ChatList) {
            result = mChatId == ((ChatList) other).mChatId;
        }
        return result;
    }
}
