package edu.uw.tcss450lucasd12.team_4_tcss450.Views.chat.ChatRoom;

import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Paul Lee
 * @version Fall 2022
 */
public final class ChatRoom implements Serializable {

    private final int mMessageId;
    private final String mMessage;
    private final String mSender;
    private final String mTimeStamp;
    private final String mUsername;

    public ChatRoom(int messageId, String message, String sender, String timeStamp) {
        this.mMessageId = messageId;
        this.mMessage = message;
        this.mSender = sender;
        this.mTimeStamp = timeStamp;
        this.mUsername = "";
    }

    /**
     * Static factory method to turn a properly formatted JSON String into a
     * ChatRoom object.
     * @param cmAsJson the String to be parsed into a ChatRoom Object.
     * @return a ChatRoom Object with the details contained in the JSON String.
     * @throws JSONException when cmAsString cannot be parsed into a ChatRoom.
     */
    public static ChatRoom createFromJsonString(final String cmAsJson) throws JSONException {
        final JSONObject msg = new JSONObject(cmAsJson);
        return new ChatRoom(msg.getInt("messageid"),
                msg.getString("message"),
                msg.getString("email"),
                msg.getString("timestamp"));
//                msg.getString("username"));
    }

    public int getMessageId() {
        return this.mMessageId;
    }

    public String getMessage() {
        return this.mMessage;
    }

    public String getSender() {
        return this.mSender;
    }

    public String getTimeStamp() {
        return this.mTimeStamp;
    }

//    public String getUsername() {
//        return this.mUsername;
//    }

    /**
     * Provides equality solely based on MessageId.
     * @param other the other object to check for equality
     * @return true if other message ID matches this message ID, false otherwise.
     */
    @Override
    public boolean equals(@Nullable Object other) {
        boolean result = false;
        if (other instanceof ChatRoom) {
            result = mMessageId == ((ChatRoom) other).mMessageId;
        }
        return result;
    }
}
