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

import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import edu.uw.tcss450lucasd12.team_4_tcss450.R;
import edu.uw.tcss450lucasd12.team_4_tcss450.io.RequestQueueSingleton;

/**
 * View Model for when deleting members from a specific chat room.
 *
 * @author Paul Lee
 * @version Fall 2022
 */
public class ChatRemoveMemberViewModel extends AndroidViewModel {
    private MutableLiveData<JSONObject> mResponse;

    /**
     * Constructor
     *
     * @param application Application
     */
    public ChatRemoveMemberViewModel(@NonNull Application application) {
        super(application);
        mResponse = new MutableLiveData<>();
        mResponse.setValue(new JSONObject());
    }

    /**
     * Register as an observer to listen to when the member is removed from the
     * chat room.
     *
     * @param owner the fragments lifecycle owner
     * @param observer the observer
     */
    public void addResponseObserver(@NonNull LifecycleOwner owner,
                                    @NonNull Observer<? super JSONObject> observer) {
        mResponse.observe(owner, observer);
    }

    /**
     * Makes a request to the web service to remove the member from the
     * chat room.
     *
     * @param chatId the chat Id of the chat room
     * @param jwt the users signed JWT
     * @param email the email to remove
     */
    public void deleteMember(final int chatId, final String jwt, final String email) {
        String url = getApplication().getResources().getString(R.string.base_url) +
                "chats/" + chatId +
                "/" + email;

        Request request = new JsonObjectRequest(
                Request.Method.DELETE,
                url,
                null,
                mResponse::setValue,
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
