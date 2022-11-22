package Helpers;

import java.util.ArrayList;
import java.util.List;

import edu.uw.tcss450lucasd12.team_4_tcss450.Views.chat.ChatList.ChatList;

/**
 * A helper class that creates the hard-coded
 * lists of chats.
 *
 * @author Paul Lee
 * @version Fall 2022
 */
public class ChatHelper {

    /**
     * A list that holds all of the chat rooms.
     */
    private List<ChatList> mChatList;

    /**
     * No-arg Constructor
     */
    public ChatHelper() {
        mChatList = new ArrayList<>();
        ChatList first = new ChatList(1, "Global Chat", "untitled", "N/A");
        ChatList second = new ChatList(2, "Chat", "untitled2", "N/A2");

        mChatList.add(first);
        mChatList.add(second);
    }

    /**
     * Gets the list of chat rooms
     * @return
     */
    public List<ChatList> getChatList() {
        return this.mChatList;
    }

    /**
     * Adds to the list
     * @param chat a chatroom to be added.
     */
    public void addToList(ChatList chat) {
        mChatList.add(chat);
    }
}
