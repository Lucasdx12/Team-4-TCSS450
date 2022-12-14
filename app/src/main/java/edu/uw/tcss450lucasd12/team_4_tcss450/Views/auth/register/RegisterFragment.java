package edu.uw.tcss450lucasd12.team_4_tcss450.Views.auth.register;

import static edu.uw.tcss450lucasd12.team_4_tcss450.utils.PasswordValidator.*;

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
import edu.uw.tcss450lucasd12.team_4_tcss450.databinding.FragmentRegisterBinding;
import edu.uw.tcss450lucasd12.team_4_tcss450.utils.PasswordValidator;

/**
 * A simple {@link Fragment} subclass.
 * @author Alexz Rosario
 */
public class RegisterFragment extends Fragment {
    /** Binding to xml */
    private FragmentRegisterBinding binding;
    /** View Model */
    private RegisterViewModel mRegisterModel;
    /** Name Verification */
    private PasswordValidator mNameValidator = checkPwdLength(1);
    /** Email Verification */
    private PasswordValidator mEmailValidator = checkPwdLength(2)
            .and(checkExcludeWhiteSpace())
            .and(checkPwdSpecialChar("@"));
    /** Password Verification */
    private PasswordValidator mPassWordValidator =
            checkClientPredicate(pwd -> pwd.equals(binding.confirmPassword.getText().toString()))
                    .and(checkPwdLength(7))
                    .and(checkPwdSpecialChar())
                    .and(checkExcludeWhiteSpace())
                    .and(checkPwdDigit())
                    .and(checkPwdLowerCase().or(checkPwdUpperCase()));
    /**
     * Required empty public constructor
     */
    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRegisterModel = new ViewModelProvider(getActivity())
                .get(RegisterViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonRegister.setOnClickListener(this::attemptRegister);
        mRegisterModel.addResponseObserver(getViewLifecycleOwner(),
                this::observeResponse);
    }
    /**
     * Attempt Register
     * @param button
     */
    private void attemptRegister(final View button) {
        validateFirst();
    }
    /**
     * Validate First Name
     */
    private void validateFirst() {
        mNameValidator.processResult(
                mNameValidator.apply(binding.editFirstName.getText().toString().trim()),
                this::validateLast,
                result -> binding.editFirstName.setError("Please enter a first name.\n"+
                                                         " * Must be of length 1"));
    }
    /**
     * Validate Last Name
     */
    private void validateLast() {
        mNameValidator.processResult(
                mNameValidator.apply(binding.editLastName.getText().toString().trim()),
                this::validateDisplay,
                result -> binding.editLastName.setError("Please enter a last name.\n"+
                                                        " * Must be of length 1"));
    }
    /**
     * Validate Display Name
     */
    private void validateDisplay() {
        mNameValidator.processResult(
                mNameValidator.apply(binding.editDisplayName.getText().toString().trim()),
                this::validateEmail,
                result -> binding.editDisplayName.setError("Please enter a display name of length 1\n"+
                                                           " * Must be unique."));
    }
    /**
     * Validate Email
     */
    private void validateEmail() {
        mEmailValidator.processResult(
                mEmailValidator.apply(binding.editEmail.getText().toString().trim()),
                this::validatePasswordsMatch,
                result -> binding.editEmail.setError("Please enter a valid Email address.\n"+
                                                     " * Must include an @\n"+
                                                     " * No spaces are allowed."));
    }

    /**
     * Validate Password Match
     */
    private void validatePasswordsMatch() {
        PasswordValidator matchValidator =
                checkClientPredicate(
                        pwd -> pwd.equals(binding.confirmPassword.getText().toString().trim()));

        mEmailValidator.processResult(
                matchValidator.apply(binding.editPassword.getText().toString().trim()),
                this::validatePassword,
                result -> binding.editPassword.setError("Passwords must match."));
    }
    /**
     * Validate Password
     */
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
    /**
     * Http call through View Model
     */
    private void verifyAuthWithServer() {
        mRegisterModel.connect(
                binding.editFirstName.getText().toString(),
                binding.editLastName.getText().toString(),
                binding.editDisplayName.getText().toString(),
                binding.editEmail.getText().toString(),
                binding.editPassword.getText().toString());
        //This is an Asynchronous call. No statements after should rely on the
        //result of connect().

    }
    /**
     * Helper to abstract the navigation
     */
    private void navigateToLogin() {
        RegisterFragmentDirections.ActionRegisterFragmentToSignInFragment directions =
                RegisterFragmentDirections.actionRegisterFragmentToSignInFragment();

        directions.setEmail(binding.editEmail.getText().toString());
        directions.setPassword(binding.editPassword.getText().toString());

        Navigation.findNavController(getView()).navigate(directions);

    }

    /**
     * An observer on the HTTP Response from the web server. This observer should be
     * attached to SignInViewModel.
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