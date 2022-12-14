package edu.uw.tcss450lucasd12.team_4_tcss450.Views.chat.ChatRoomSetting;

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
 * View Model for getting all the emails in a specific chat room
 * from the database.
 *
 * @author Paul Lee
 * @version Fall 2022
 */
public class ChatGetEmailViewModel extends AndroidViewModel {
    private Map<Integer, MutableLiveData<List<String>>> mEmails;
    private int mChatId;

    /**
     * Constructor
     *
     * @param application Application
     */
    public ChatGetEmailViewModel(@NonNull Application application) {
        super(application);
        mEmails = new HashMap<>();
    }

    /**
     * Register as an observer to listen to a specific chat setting's list of emails
     *
     * @param chatId the chat ID of the chat to observer
     * @param owner the fragments lifecycle owner
     * @param observer the observer
     */
    public void addResponseObserver(int chatId,
                                    @NonNull LifecycleOwner owner,
                                    @NonNull Observer<? super List<String>> observer) {
        getOrCreateMapEntry(chatId).observe(owner, observer);
    }

    /**
     * Return a reference to the List<> associated with the chat setting. If the View Model does
     * not have a mapping for this chatID, it will be created.
     *
     * @param chatId the id of the chat room List to retrieve
     * @return a reference to the list of emails.
     */
    public List<String> getListOfEmailsByChatId(final int chatId) {
        return getOrCreateMapEntry(chatId).getValue();
    }

    private MutableLiveData<List<String>> getOrCreateMapEntry(final int chatId) {
        if (!mEmails.containsKey(chatId)) {
            mEmails.put(chatId, new MutableLiveData<>(new ArrayList<>()));
        }
        return mEmails.get(chatId);
    }

    /**
     * Makes a request to the web service to get all the emails that are associated
     * with the specific chat Id and adds it to the List. Informs observers of the update.
     *
     * @param jwt the users signed JWT
     * @param chatId the chat Id to request the list of emails
     */
    public void getAllEmails(final String jwt, final int chatId) {
        mChatId = chatId;
        String url = getApplication().getResources().getString(R.string.base_url) +
                "chats/" + chatId;

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
        List<String> emails;

        try {
            emails = getListOfEmailsByChatId(mChatId);
            JSONArray listOfEmails = response.getJSONArray("rows");
            for (int i = 0; i < listOfEmails.length(); i++) {
                JSONObject email = listOfEmails.getJSONObject(i);
                String userEmail = email.getString("email");

                if (!emails.contains(userEmail)) {
                    // Don't add a duplicate
                    emails.add(0, userEmail);
                } else {
                    // This shouldn't happen but could with the asynchronous
                    // nature of the application
                    Log.wtf("Email already received",
                            "Or duplicate email: " + userEmail);
                }
            }

            // Inform observers of the change (setValue)
            getOrCreateMapEntry(mChatId).setValue(emails);
        } catch (JSONException e) {
            Log.e("JSON PARSE ERROR", "Found ing handle Success ChatGetEmailViewModel");
            Log.e("JSON PARSE ERROR", "Error: " + e.getMessage());
        }
    }

    private void handleError(final VolleyError error) {
        if (Objects.isNull(error.networkResponse)) {
            Log.e("NETWORK ERROR IN CHATGETEMAILVIEWMODEL", error.getMessage());
        } else {
            String data = new String(error.networkResponse.data, Charset.defaultCharset());
            Log.e("CLIENT ERROR IN CHATGETEMAILVIEWMODEL",
                    error.networkResponse.statusCode +
                    " " +
                    data);
        }
    }
}
