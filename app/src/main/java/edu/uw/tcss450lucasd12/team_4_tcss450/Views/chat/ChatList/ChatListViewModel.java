package edu.uw.tcss450lucasd12.team_4_tcss450.Views.chat.ChatList;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import edu.uw.tcss450lucasd12.team_4_tcss450.R;
import edu.uw.tcss450lucasd12.team_4_tcss450.io.RequestQueueSingleton;

/**
 * View Model for the chat list to get
 * the list of chats that the user is in.
 *
 * @author Paul Lee
 * @version Fall 2022
 */
public class ChatListViewModel extends AndroidViewModel {

    /**
     * A List of (known) chat rooms that the user is in.
     */
    private final MutableLiveData<List<ChatList>> mChatRooms;

    /**
     * Constructor
     *
     * @param application Application
     */
    public ChatListViewModel(@NonNull Application application) {
        super(application);
        mChatRooms = new MutableLiveData<>();
        mChatRooms.setValue(new ArrayList<>());
    }

    /**
     * Register as an observer to listen when all the chat rooms that the user is in
     * is received.
     *
     * @param owner the fragments lifecycle owner
     * @param observer the observer
     */
    public void addChatListObserver(@NonNull LifecycleOwner owner,
                                    @NonNull Observer<? super List<ChatList>> observer) {
        mChatRooms.observe(owner, observer);
    }

    /**
     * Get the List of ChatLists
     *
     * @return the list of chat lists.
     */
    public List<ChatList> getChatList() {
        return mChatRooms.getValue();
    }

    /**
     * Makes a request to the web service
     * to get all the chats that the user is in.
     *
     * @param jwt the users signed JWT
     */
    public void getChats(final String jwt) {
        String url = getApplication().getResources().getString(R.string.base_url)
                + "chats";

        Request request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                this::handleSuccess,
                this::handleError) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();

                // Add headers <key,value>
                headers.put("Authorization", jwt);
                return headers;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Instantiate the RequestQueue and add the request to the queue.
        RequestQueueSingleton.getInstance(getApplication().getApplicationContext())
                .addToRequestQueue(request);
    }

    private void handleSuccess(final JSONObject response) {
        List<ChatList> chatLists;

        try {
            chatLists = getChatList();
            JSONArray lists = response.getJSONArray("rows");
            for (int i = 0; i < lists.length(); i++) {
                JSONObject list = lists.getJSONObject(i);
                ChatList cList = new ChatList(
                        list.getInt("chatid"),
                        list.getString("name")
                );
                if (!chatLists.contains(cList)) {
                    // Don't add a duplicate
                    chatLists.add(0, cList);
                } else {
                    // This shouldn't happen but could with the asynchronous
                    // nature of this application
                    Log.wtf("Chat List already received",
                            "Or duplicate id: " + cList.getChatId());
                }
            }

            // Inform observers of the change (setValue)
            mChatRooms.setValue(chatLists);
        } catch (JSONException e) {
            Log.e("JSON PARSE ERROR", "Found in handle Success ChatListViewModel");
            Log.e("JSON PARSE ERROR", "Error: " + e.getMessage());
        }
    }

    private void handleError(final VolleyError error) {
        if (Objects.isNull(error.networkResponse)) {
            Log.e("NETWORK ERROR", error.getMessage());
        } else {
            String data = new String(error.networkResponse.data, Charset.defaultCharset());
            Log.e("CLIENT ERROR",
                    error.networkResponse.statusCode +
                    " " +
                    data);
        }
    }
}
