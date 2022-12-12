package edu.uw.tcss450lucasd12.team_4_tcss450.Views.contacts;

import static edu.uw.tcss450lucasd12.team_4_tcss450.utils.PasswordValidator.checkExcludeWhiteSpace;
import static edu.uw.tcss450lucasd12.team_4_tcss450.utils.PasswordValidator.checkPwdLength;
import static edu.uw.tcss450lucasd12.team_4_tcss450.utils.PasswordValidator.checkPwdSpecialChar;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uw.tcss450lucasd12.team_4_tcss450.databinding.FragmentSearchBinding;
import edu.uw.tcss450lucasd12.team_4_tcss450.model.UserInfoViewModel;
import edu.uw.tcss450lucasd12.team_4_tcss450.utils.PasswordValidator;

/**
 * A simple {@link Fragment} subclass.
 * @author Alexz Rosario
 */
public class SearchFragment extends Fragment {
    /** Binding to xml */
    private FragmentSearchBinding binding;
    /** User Model */
    private UserInfoViewModel mUserModel;
    /** View Model */
    private SearchViewModel mSearchViewModel;
    /** Email Verification */
    private PasswordValidator mEmailValidator = checkPwdLength(2)
            .and(checkExcludeWhiteSpace())
            .and(checkPwdSpecialChar("@"));

    /**
     * Required empty public constructor
     */
    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelProvider provider = new ViewModelProvider(getActivity());
        mUserModel = provider.get(UserInfoViewModel.class);
        mSearchViewModel = provider.get(SearchViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonSearch.setOnClickListener(this::attemptSearch);
        mSearchViewModel.addResponseObserver(getViewLifecycleOwner(),
                this::observeResponse);
    }

    /**
     * Attempt Search
     * @param button
     */
    private void attemptSearch(final View button) {
        validateEmail();
    }

    /**
     * Validate Email
     */
    private void validateEmail() {
        mEmailValidator.processResult(
                mEmailValidator.apply(binding.editEmail.getText().toString().trim()),
                this::verifyAuthWithServer,
                result -> binding.editEmail.setError("Please enter a valid Email address.\n"+
                        " * Must include an @\n"+
                        " * No spaces are allowed."));
    }

    /**
     * Http call through View Model
     */
    private void verifyAuthWithServer() {
        mSearchViewModel.connect(
                mUserModel.getEmail(),
                binding.editEmail.getText().toString());
        //This is an Asynchronous call. No statements after should rely on the
        //result of connect().

    }

    /**
     * Reset Email text to blank
     */
    private void resetEmail() {
        binding.editEmail.setText("");
    }

    /**
     * An observer on the HTTP Response from the web server. This observer should be
     * attached to ChangePasswordViewModel.
     *
     * @param response the Response from the server
     */
    private void observeResponse(final JSONObject response) {
        if (response.length() > 0) {
            if (response.has("code")) {
                try {
                    binding.editEmail.setError(
                            "Error Authenticating: " +
                                    response.getJSONObject("data").getString("message"));
                } catch (JSONException e) {
                    Log.e("JSON Parse Error", e.getMessage());
                }
            } else {
                resetEmail();
            }
        } else {
            Log.d("JSON Response", "No Response");
        }
    }


}