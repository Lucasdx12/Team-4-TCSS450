package edu.uw.tcss450lucasd12.team_4_tcss450.Views.auth.forgotpassword;

import static edu.uw.tcss450lucasd12.team_4_tcss450.utils.PasswordValidator.checkExcludeWhiteSpace;
import static edu.uw.tcss450lucasd12.team_4_tcss450.utils.PasswordValidator.checkPwdLength;
import static edu.uw.tcss450lucasd12.team_4_tcss450.utils.PasswordValidator.checkPwdSpecialChar;

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

import edu.uw.tcss450lucasd12.team_4_tcss450.Views.auth.register.RegisterFragmentDirections;
import edu.uw.tcss450lucasd12.team_4_tcss450.databinding.FragmentForgotPasswordBinding;
import edu.uw.tcss450lucasd12.team_4_tcss450.utils.PasswordValidator;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForgotPasswordFragment extends Fragment {

    private FragmentForgotPasswordBinding binding;

    private ForgotPasswordViewModel mForgotPasswordModel;

    private PasswordValidator mEmailValidator = checkPwdLength(2)
            .and(checkExcludeWhiteSpace())
            .and(checkPwdSpecialChar("@"));

    public ForgotPasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mForgotPasswordModel = new ViewModelProvider(getActivity())
                .get(ForgotPasswordViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentForgotPasswordBinding.inflate(inflater);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonForgotPassword.setOnClickListener(this::attemptForgotPassword);
        mForgotPasswordModel.addResponseObserver(getViewLifecycleOwner(),
                this::observeResponse);
    }

    private void attemptForgotPassword(final View button) {
        validateEmail();
    }

    private void validateEmail() {
        mEmailValidator.processResult(
                mEmailValidator.apply(binding.editEmail.getText().toString().trim()),
                this::verifyAuthWithServer,
                result -> binding.editEmail.setError("Please enter a valid Email address.\n"+
                        " * Must include an @\n"+
                        " * No spaces are allowed."));
    }
    private void verifyAuthWithServer() {
        mForgotPasswordModel.connect(
                binding.editEmail.getText().toString());
        //This is an Asynchronous call. No statements after should rely on the
        //result of connect().

    }
    private void navigateToLogin() {
        ForgotPasswordFragmentDirections.ActionForgotPasswordFragmentToSignInFragment directions =
                ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToSignInFragment();

        directions.setEmail(binding.editEmail.getText().toString());
        directions.setPassword("");

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