package edu.uw.tcss450lucasd12.team_4_tcss450.Views.chat.ChatList;

import android.util.Log;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import edu.uw.tcss450lucasd12.team_4_tcss450.Views.chat.ChatList.ChatList;
import edu.uw.tcss450lucasd12.team_4_tcss450.Views.chat.ChatRoom.ChatRoom;
import edu.uw.tcss450lucasd12.team_4_tcss450.Views.chat.ChatRoom.ChatRoomGenerator;

/**
 *
 * @author Paul Lee
 * @version Autumn 2022
 */
public final class ChatListGenerator {

    private static final ChatList[] CHATS;
    private static final String[] mNames = {"Paul", "Lucas", "Alexz", "Alec", "Steph", "David", "Charles"};

    public static final int ROOM = 1;

    static {
        CHATS = new ChatList[ROOM];
//        for (int i = 0; i < CHATS.length; i++) {
            CHATS[0] = new ChatList
                    .Builder("Chat Room " + (1))
                    .addRecentMessage(ChatRoomGenerator.getCHATS()[7].getUserName()
                                      + ": " +
                                      ChatRoomGenerator.getCHATS()[7].getMessage())
                    .addDate(ChatRoomGenerator.getCHATS()[7].getTimeStamp())
                    .build();
//        }
    }

    public static List<ChatList> getChatList() {
        return Arrays.asList(CHATS);
    }

    public static ChatList[] getCHATS() {
        return Arrays.copyOf(CHATS, CHATS.length);
    }

    private ChatListGenerator() {

    }
}
