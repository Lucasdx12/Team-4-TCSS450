package edu.uw.tcss450lucasd12.team_4_tcss450.Views.contacts;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

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

public class ContactsViewModel extends AndroidViewModel {

    private ContactsList mContactsList;

    private final MutableLiveData<ContactsList> selectedContact = new MutableLiveData<>();

    private Map<Integer, MutableLiveData<List<ContactsList>>> mContacts;

    private final MutableLiveData<JSONObject> mResponse;

    public ContactsViewModel(@NonNull Application application) {
        super(application);
        this.mContactsList = mContactsList;
//        mContacts = new Map<Integer, MutableLiveData<List<ContactsList>>>;
        mResponse = new MutableLiveData<>();
        mResponse.setValue(new JSONObject());
    }

    public void selectContact(ContactsList contacts) {
        selectedContact.setValue(contacts);
    }

    public LiveData<ContactsList> getSelectedContact() {
        return selectedContact;
    }

    public void addContactsObserver(int chatId,
                                   @NonNull LifecycleOwner owner,
                                   @NonNull Observer<? super List<ContactsList>> observer) {
        getOrCreateMapEntry(chatId).observe(owner, observer);
    }

    private MutableLiveData<List<ContactsList>> getOrCreateMapEntry(final int nickname) {
        if(mContacts.containsKey(nickname)) {
            mContacts.put(nickname, new MutableLiveData<>(new ArrayList<>()));
        }
        return mContacts.get(nickname);
    }

    public List<ContactsList> getMessageListByChatId(final int nickname) {
        return getOrCreateMapEntry(nickname).getValue();
    }

    //TODO: button functionality for contact profile in server
    /*
    Friend status -> 1 = friend, 0 = not-friend, 2 = blocked
     */
    public void friendStatusProfile(final String email, final String jwt, final int friendStatus) {
        //String url = "https://tcss450-team4-webservice.herokuapp.com/contacts";

        String url = getApplication().getResources().getString(R.string.base_url) +
                "contacts";

        JSONObject body = new JSONObject();
        try {
            body.put("email", email); //TODO: check email name in server
            body.put("friendStatus", friendStatus);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Request request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                body, // Push token found in the JSONObject body
                mResponse::setValue, // We get a response but do nothing with it
                this::handleError) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();

                // Add headers <key,value>
                headers.put("Authorization", jwt);
                return headers;
            }
        };

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
