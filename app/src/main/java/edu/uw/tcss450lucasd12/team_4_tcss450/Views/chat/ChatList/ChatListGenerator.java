package edu.uw.tcss450lucasd12.team_4_tcss450.Views.chat.ChatList;

import android.util.Log;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import edu.uw.tcss450lucasd12.team_4_tcss450.Views.chat.ChatList.ChatList;
import edu.uw.tcss450lucasd12.team_4_tcss450.Views.chat.ChatRoom.ChatRoom;
import edu.uw.tcss450lucasd12.team_4_tcss450.Views.chat.ChatRoom.ChatRoomGenerator;

/**
 * Generator to get the list of chats
 *
 * @author Paul Lee
 * @version Autumn 2022
 */
public final class ChatListGenerator {

    private static final ChatList[] CHATS;
    private static final String[] mNames = {"Paul", "Lucas", "Alexz", "Alec", "Steph", "David", "Charles"};

    private static final int ROOM = 2;

    static {
        CHATS = new ChatList[ROOM];
        for (int i = 1; i < CHATS.length + 1; i++) {
//            CHATS[0] = new ChatList(i, "Chat Room Title" + i,
//                    "Recent Message" + i, "4:25PM");
        }
    }

    /**
     * Gets the list of ChatList
     * @return list of ChatList
     */
    public static List<ChatList> getChatList() {
        return Arrays.asList(CHATS);
    }

    /**
     * Gets the array of ChatList
     * @return array of ChatList
     */
    public static ChatList[] getCHATS() {
        return Arrays.copyOf(CHATS, CHATS.length);
    }

    private ChatListGenerator() {

    }
}
