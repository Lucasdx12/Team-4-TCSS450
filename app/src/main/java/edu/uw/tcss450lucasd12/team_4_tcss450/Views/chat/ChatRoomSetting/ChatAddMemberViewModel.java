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

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import edu.uw.tcss450lucasd12.team_4_tcss450.R;
import edu.uw.tcss450lucasd12.team_4_tcss450.io.RequestQueueSingleton;

public class ChatAddMemberViewModel extends AndroidViewModel {
    private final MutableLiveData<JSONObject> mResponse;

    public ChatAddMemberViewModel(@NonNull Application application) {
        super(application);
        mResponse = new MutableLiveData<>();
        mResponse.setValue(new JSONObject());
    }

    public void addResponseObserver(@NonNull LifecycleOwner owner,
                                    @NonNull Observer<? super JSONObject> observer) {
        mResponse.observe(owner, observer);
    }

    public void addMember(final int chatId, final String jwt) {
        String url = getApplication().getResources().getString(R.string.base_url) +
            "chats/" + chatId;

        Request request = new JsonObjectRequest(
                Request.Method.PUT,
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

    public void addOtherMembers(final int chatId, final String jwt, final String email) {
        String url = getApplication().getResources().getString(R.string.base_url)
                + "chats/" + chatId
                + "/" + email;

        Request request = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                null,
                this::handleSuccess,
                this::handleError) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> header = new HashMap<>();

                // Add headers <key,value>
                header.put("Authorization", jwt);
                return header;
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
        try {
            int memberid = response.getInt("memberid");
            Log.e("CHATADDMEMBER", memberid + " ");
            boolean isTrue = response.getBoolean("success");
            Log.e("CHATADDMEMBERBOOLEAN", isTrue + " ");

            mResponse.setValue(response);
        } catch (JSONException e) {
            Log.e("JSON PARSE ERROR", "Found in handle Success ChatAddMemberViewModel");
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