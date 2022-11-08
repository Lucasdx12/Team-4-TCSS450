package edu.uw.tcss450lucasd12.team_4_tcss450.Views.chat.ChatRoom;

import android.util.Log;

import java.util.Arrays;
import java.util.List;

import edu.uw.tcss450lucasd12.team_4_tcss450.Views.chat.ChatList.ChatList;

/**
 *
 * @author Paul Lee
 * @version Fall 2022
 */
public final class ChatRoomGenerator {
    private static ChatRoom[] chats;
    private static final String[] mNames = {"Paul", "Lucas", "Alexz", "Alec", "Steph", "David", "Charles"};

    public static int pointer = 2;
    public static int messages = 8;

    static {
        chats = new ChatRoom[messages];
        chats[0] = new ChatRoom(0, "test","test@testEmail.com", "Hello World!", "12:49 PM");
        for (int i = 1; i < chats.length - 1; i++) {
            chats[i] = new ChatRoom(0,
                    mNames[i-1],
                    mNames[i-1] + "@uw.edu",
                    "Hello " + mNames[i],
                    ((i % 12) + 1) + ":02 PM");
            pointer++;
        }

        String message = "Hello everyone! How are you doing today? Are you having fun doing the TCSS 450 Project or no? What other class are you taking? Are they difficult? Good luck on this project and future projects!";
        chats[7] = new ChatRoom(0,
                                mNames[6],
                        mNames[6] + "@uw.edu",
                                message,
                    "5:15 PM");
    }

    public static List<ChatRoom> getChatRoomList() {
        return Arrays.asList(chats);
    }

    public static ChatRoom[] getCHATS() {
        return Arrays.copyOf(chats, chats.length);
    }

    public static void addMessage(ChatRoom newMessage) {
        Log.i("POINTER", pointer +" ");
        Log.i("MESSAGES", messages + " ");
        if (pointer == messages) {
            messages++;
            ChatRoom[] newArray = new ChatRoom[messages];

            for (int i = 0; i < chats.length; i++) {
                newArray[i] = chats[i];
            }
            newArray[messages - 1] = newMessage;
            chats = newArray;
        } else {
            chats[pointer] = newMessage;
        }

        pointer++;
    }

    private ChatRoomGenerator() {

    }
}
