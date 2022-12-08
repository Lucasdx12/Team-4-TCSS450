package edu.uw.tcss450lucasd12.team_4_tcss450.Views.auth.changepassword;

import static edu.uw.tcss450lucasd12.team_4_tcss450.utils.PasswordValidator.checkClientPredicate;
import static edu.uw.tcss450lucasd12.team_4_tcss450.utils.PasswordValidator.checkExcludeWhiteSpace;
import static edu.uw.tcss450lucasd12.team_4_tcss450.utils.PasswordValidator.checkPwdDigit;
import static edu.uw.tcss450lucasd12.team_4_tcss450.utils.PasswordValidator.checkPwdLength;
import static edu.uw.tcss450lucasd12.team_4_tcss450.utils.PasswordValidator.checkPwdLowerCase;
import static edu.uw.tcss450lucasd12.team_4_tcss450.utils.PasswordValidator.checkPwdSpecialChar;
import static edu.uw.tcss450lucasd12.team_4_tcss450.utils.PasswordValidator.checkPwdUpperCase;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uw.tcss450lucasd12.team_4_tcss450.R;
import edu.uw.tcss450lucasd12.team_4_tcss450.Views.auth.forgotpassword.ForgotPasswordFragmentDirections;
import edu.uw.tcss450lucasd12.team_4_tcss450.Views.auth.forgotpassword.ForgotPasswordViewModel;
import edu.uw.tcss450lucasd12.team_4_tcss450.databinding.FragmentChangePasswordBinding;
import edu.uw.tcss450lucasd12.team_4_tcss450.databinding.FragmentForgotPasswordBinding;
import edu.uw.tcss450lucasd12.team_4_tcss450.utils.PasswordValidator;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePasswordFragment extends Fragment {

    private FragmentChangePasswordBinding binding;

    private ChangePasswordViewModel mChangePasswordViewModel;

    private PasswordValidator mEmailValidator = checkPwdLength(2)
            .and(checkExcludeWhiteSpace())
            .and(checkPwdSpecialChar("@"));

    private PasswordValidator mPassWordValidator =
            checkClientPredicate(pwd -> pwd.equals(binding.editPassword.getText().toString()))
                    .and(checkPwdLength(7))
                    .and(checkPwdSpecialChar())
                    .and(checkExcludeWhiteSpace())
                    .and(checkPwdDigit())
                    .and(checkPwdLowerCase().or(checkPwdUpperCase()));

    public ChangePasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mChangePasswordViewModel = new ViewModelProvider(getActivity())
                .get(ChangePasswordViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChangePasswordBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonChangePassword.setOnClickListener(this::attemptForgotPassword);
        mChangePasswordViewModel.addResponseObserver(getViewLifecycleOwner(),
                this::observeResponse);
    }
    private void attemptForgotPassword(final View button) {
        validateEmail();
    }

    private void validateEmail() {
        mEmailValidator.processResult(
                mEmailValidator.apply(binding.editEmail.getText().toString().trim()),
                this::validatePassword,
                result -> binding.editEmail.setError("Please enter a valid Email address.\n"+
                        " * Must include an @\n"+
                        " * No spaces are allowed."));
    }

    private void validatePassword() {
        mPassWordValidator.processResult(
                mPassWordValidator.apply(binding.editPassword.getText().toString()),
                this::verifyAuthWithServer,
                result -> binding.editPassword.setError("Please enter a valid Password.\n"+
                        " * Must be at least 7 characters long.\n"+
                        " * Must include a special character (@#$%&*!?)\n"+
                        " * Must include either a lower case or upper case letter\n"+
                        " * Must include a digit (0-9)\n"+
                        " * No spaces are allowed."));
    }
    private void verifyAuthWithServer() {
        mChangePasswordViewModel.connect(
                binding.editEmail.getText().toString(),
                binding.editPassword.getText().toString());
        //This is an Asynchronous call. No statements after should rely on the
        //result of connect().

    }
    private void navigateToLogin() {
        //TODO create nav for change password back to landing
        binding.editEmail.setText("");
        binding.editPassword.setText("");

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
                navigateToLogin();
            }
        } else {
            Log.d("JSON Response", "No Response");
        }
    }
}