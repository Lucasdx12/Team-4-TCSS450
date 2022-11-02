package edu.uw.tcss450lucasd12.team_4_tcss450.Views.chat.ChatList;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import edu.uw.tcss450lucasd12.team_4_tcss450.Views.chat.ChatList.ChatList;

/**
 *
 * @author Paul Lee
 * @version Autumn 2022
 */
public final class ChatListGenerator {

    private static final ChatList[] CHATS;
    private static final String[] mNames = {"Paul", "Lucas", "Alexz", "Alec", "Steph", "David", "Charles"};

    public static final int ROOM = 7;

    static {
        CHATS = new ChatList[ROOM];
        for (int i = 0; i < CHATS.length; i++) {
            CHATS[i] = new ChatList
                    .Builder("Chat Room " + (i + 1))
                    .addRecentMessage(generateName() + ": Hello " + generateName() + "! How are you doing today? Are you having fun doing the TCSS 450 Project or no? What other class are you taking? Are they difficult? I'm tired................................")
                    .addDate((i % 12 + 1) + ":02 pm")
                    .build();
        }
    }

    private static String generateName() {
        Random random = new Random();
        int n = random.nextInt(mNames.length);
        return mNames[n];
    }

    public static List<ChatList> getChatRoomList() {
        return Arrays.asList(CHATS);
    }

    public static ChatList[] getCHATS() {
        return Arrays.copyOf(CHATS, CHATS.length);
    }

    private ChatListGenerator() {

    }
}
