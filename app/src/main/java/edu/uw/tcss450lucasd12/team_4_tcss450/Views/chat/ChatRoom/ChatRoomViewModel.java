package edu.uw.tcss450lucasd12.team_4_tcss450.Views.chat.ChatRoom;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Paul Lee
 * @version Fall 2022
 */
public class ChatRoomViewModel extends AndroidViewModel {
    private MutableLiveData<List<ChatRoom>> mChatRoom;

    public ChatRoomViewModel(@NonNull Application application) {
        super(application);
        mChatRoom = new MutableLiveData<>();
        mChatRoom.setValue(ChatRoomGenerator.getChatRoomList());
    }

    public void addChatRoomObserver(@NonNull LifecycleOwner owner,
                                    @NonNull Observer<? super List<ChatRoom>> observer) {
        mChatRoom.observe(owner, observer);
    }

    public List<ChatRoom> getChatRoomList() {
        return mChatRoom.getValue();
    }

    private void handleError(final VolleyError error) {

    }

    private void handleResult(final JSONObject result) {

    }

    public void connectGet() {

    }
}
