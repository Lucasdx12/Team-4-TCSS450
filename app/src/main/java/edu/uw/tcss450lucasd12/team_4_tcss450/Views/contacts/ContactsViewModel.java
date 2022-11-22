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

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.IntFunction;

import edu.uw.tcss450lucasd12.team_4_tcss450.R;
import edu.uw.tcss450lucasd12.team_4_tcss450.io.RequestQueueSingleton;

public class ContactsViewModel extends AndroidViewModel {
    private MutableLiveData<List<Contact>> mContacts;

//    private final MutableLiveData<JSONObject> mResponse;

    public ContactsViewModel(@NonNull Application application) {
        super(application);
        mContacts = new MutableLiveData<>();
        mContacts.setValue(new ArrayList<>());
    }

    public void addContactsObserver(@NonNull LifecycleOwner owner,
                                   @NonNull Observer<? super List<Contact>> observer) {
        mContacts.observe(owner, observer);
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


    //TODO: connect to contact info in server
//    //handle non-error response (JSON parsed & placed into list of BlogPost obj)
//    private void handleResult(final JSONObject result) {
//        IntFunction<String> getString =
//                getApplication().getResources()::getString;
//        try {
//            JSONObject root = result;
//            if (root.has(getString.apply(R.string.keys_json_blogs_response))) {
//                JSONObject response =
//                        root.getJSONObject(getString.apply(
//                                R.string.keys_json_blogs_response));
//                if (response.has(getString.apply(R.string.keys_json_blogs_data))) {
//                    JSONArray data = response.getJSONArray(
//                            getString.apply(R.string.keys_json_blogs_data));
//                    for(int i = 0; i < data.length(); i++) {
//                        JSONObject jsonBlog = data.getJSONObject(i);
//                        BlogPost post = new BlogPost.Builder(
//                                jsonBlog.getString(
//                                        getString.apply(
//                                                R.string.keys_json_blogs_pubdate)),
//                                jsonBlog.getString(
//                                        getString.apply(
//                                                R.string.keys_json_blogs_title)))
//                                .addTeaser(jsonBlog.getString(
//                                        getString.apply(
//                                                R.string.keys_json_blogs_teaser)))
//                                .addUrl(jsonBlog.getString(
//                                        getString.apply(
//                                                R.string.keys_json_blogs_url)))
//                                .build();
//                        if (!mBlogList.getValue().contains(post)) {
//                            mBlogList.getValue().add(post);
//                        }
//                    }
//                } else {
//                    Log.e("ERROR!", "No data array");
//                }
//            } else {
//                Log.e("ERROR!", "No response");
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//            Log.e("ERROR!", e.getMessage());
//        }
//        mBlogList.setValue(mBlogList.getValue());
//    }

//    public void connectGet() {
//        String url =
//                "https://tcss450-team4-webservice.herokuapp.com/contacts";
//        Request request = new JsonObjectRequest(
//                Request.Method.GET,
//                url,
//                null, //no body for this get request
//                this::handleResult,
//                this::handleError) {
//            @Override
//            public Map<String, String> getHeaders() {
//                Map<String, String> headers = new HashMap<>();
//                // add headers <key,value>
//                headers.put(
//                        //hard-coded JWT (JSON Web Token) - authenticate after user signed in
//
//                        "Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImNmYjMxQGZha2UuZW1haWwuY29tIiwibWVtYmVyaWQiOjI4OCwiaWF0IjoxNjY2NTU3ODQ2LCJleHAiOjE2NzUxOTc4NDZ9.f7IVbAzJbX72vjRUbadIksMzNm6xkPi1l_R_C4O9zb4");
//                return headers;
//            }
//        };
//        request.setRetryPolicy(new DefaultRetryPolicy(
//                10_000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        //Instantiate the RequestQueue and add the request to the queue
//        Volley.newRequestQueue(getApplication().getApplicationContext())
//                .add(request);
//    }

    /*
    Friend status -> 1 = friend, 0 = not-friend, 2 = blocked
     */
//    public void friendStatusProfile(final String email, final String jwt, final int friendStatus) {
//        //String url = "https://tcss450-team4-webservice.herokuapp.com/contacts";
//
//        String url = getApplication().getResources().getString(R.string.base_url) +
//                "contacts";
//
//        JSONObject body = new JSONObject();
//        try {
//            body.put("email", email); //TODO: check email name in server
//            body.put("friendStatus", friendStatus);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        Request request = new JsonObjectRequest(
//                Request.Method.POST,
//                url,
//                body, // Push token found in the JSONObject body
//                mResponse::setValue, // We get a response but do nothing with it
//                this::handleError) {
//
//            @Override
//            public Map<String, String> getHeaders() {
//                Map<String, String> headers = new HashMap<>();
//
//                // Add headers <key,value>
//                headers.put("Authorization", jwt);
//                return headers;
//            }
//        };
//
//        // Instantiate the RequestQueue and add the request to the queue.
//        RequestQueueSingleton.getInstance(getApplication().getApplicationContext())
//                .addToRequestQueue(request);
//    }
}
