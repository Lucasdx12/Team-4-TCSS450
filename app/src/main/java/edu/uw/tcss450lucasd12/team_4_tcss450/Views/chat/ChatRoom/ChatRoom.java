package edu.uw.tcss450lucasd12.team_4_tcss450.Views.chat.ChatRoom;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Paul Lee
 * @version Fall 2022
 */
public final class ChatRoom implements Serializable {
    private final int mMessageId;
    private final String mSender;
    private final String mMessage;
    private final String mTimeStamp;
    private final String mUserName;

    public ChatRoom(int messageId, String userName, String sender, String message, String timeStamp) {
        this.mMessageId = messageId;
        this.mUserName = userName;
        this.mSender = sender;
        this.mMessage = message;
        this.mTimeStamp = timeStamp;
    }

    public int getMessageId() {
        return this.mMessageId;
    }

    public String getUserName() {
        return this.mUserName;
    }

    public String getSender() {
        return this.mSender;
    }

    public String getMessage() {
        return this.mMessage;
    }

    public String getTimeStamp() {
        return this.mTimeStamp;
    }

    @Override
    public boolean equals(@Nullable Object other) {
        boolean result = false;
        if (other instanceof ChatRoom) {
            result = mMessageId == ((ChatRoom) other).mMessageId;
        }
        return result;
    }
}
