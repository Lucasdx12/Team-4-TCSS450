package edu.uw.tcss450lucasd12.team_4_tcss450.Views.auth.signin;

import static edu.uw.tcss450lucasd12.team_4_tcss450.utils.PasswordValidator.*;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uw.tcss450lucasd12.team_4_tcss450.R;
import edu.uw.tcss450lucasd12.team_4_tcss450.databinding.FragmentSignInBinding;
import edu.uw.tcss450lucasd12.team_4_tcss450.model.PushyTokenViewModel;
import edu.uw.tcss450lucasd12.team_4_tcss450.model.UserInfoViewModel;
import edu.uw.tcss450lucasd12.team_4_tcss450.utils.PasswordValidator;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {

    private FragmentSignInBinding mBinding;
    private SignInViewModel mSignInModel;

    private PushyTokenViewModel mPushyTokenViewModel;
    private UserInfoViewModel mUserViewModel;

    private PasswordValidator mEmailValidator = checkPwdLength(2)
            .and(checkExcludeWhiteSpace())
            .and(checkPwdSpecialChar("@"));

    private PasswordValidator mPassWordValidator = checkPwdLength(1)
            .and(checkExcludeWhiteSpace());

    public SignInFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSignInModel = new ViewModelProvider(getActivity())
                .get(SignInViewModel.class);
        mPushyTokenViewModel = new ViewModelProvider(getActivity())
                .get(PushyTokenViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentSignInBinding.inflate(inflater);
        // Inflate the layout for this fragment
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBinding.buttonRegister.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(
                        SignInFragmentDirections.actionSignInFragmentToRegisterFragment()
                ));
        //add when register is working
        mBinding.buttonLogin.setOnClickListener(this::attemptSignIn);

        mSignInModel.addResponseObserver(getViewLifecycleOwner(),
                this::observeSignInResponse);

//        mPushyTokenViewModel.addResponseObserver(getViewLifecycleOwner(),
//                this::observePushyPutResponse);

        SignInFragmentArgs args = SignInFragmentArgs.fromBundle(getArguments());
        mBinding.editEmail.setText(args.getEmail().equals("default") ? "" : args.getEmail());
        mBinding.editPassword.setText(args.getPassword().equals("default") ? "" : args.getPassword());

    }

    /**
     * Helper to abstract the request to send the pushy token to the web service.
     */
    private void sendPushyToken() {
        mPushyTokenViewModel.sendTokenToWebservice(mUserViewModel.getJwt());
    }

    private void observePushyPutResponse(final JSONObject response) {
        if (response.length() > 0) {
            if (response.has("code")) {
                // This error cannot be fixed by the user changing credentials...
                mBinding.editEmail.setError(
                        "Error Authenticating on Push Token. Please contact support.");
            } else {
                navigateToSuccess(
                        mBinding.editEmail.getText().toString(),
                        mUserViewModel.getJwt()
                );
            }
        }
    }

    private void attemptSignIn(final View button) {
        validateEmail();
    }

    private void validateEmail() {
        mEmailValidator.processResult(
                mEmailValidator.apply(mBinding.editEmail.getText().toString().trim()),
                this::validatePassword,
                result -> mBinding.editEmail.setError("Please enter a valid Email address."));
    }

    private void validatePassword() {
        mPassWordValidator.processResult(
                mPassWordValidator.apply(mBinding.editPassword.getText().toString()),
                this::verifyAuthWithServer,
                result -> mBinding.editPassword.setError("Please enter a valid Password."));
    }

    private void verifyAuthWithServer() {
        mSignInModel.connect(
                mBinding.editEmail.getText().toString(),
                mBinding.editPassword.getText().toString());
        //This is an Asynchronous call. No statements after should rely on the
        //result of connect().

    }

    /**
     * Helper to abstract the navigation to the Activity past Authentication.
     * @param email users email
     * @param jwt the JSON Web Token supplied by the server
     */
    private void navigateToSuccess(final String email, final String jwt) {
        Navigation.findNavController(getView())
                .navigate(SignInFragmentDirections
                        .actionSignInFragmentToMainActivity(email,jwt));
    }

    /**
     * An observer on the HTTP Response from the web server. This observer should be
     * attached to SignInViewModel.
     *
     * @param response the Response from the server
     */
//    private void observeResponse(final JSONObject response) {
//        if (response.length() > 0) {
//            if (response.has("code")) {
//                try {
//                    mBinding.editEmail.setError(
//                            "Error Authenticating: " +
//                                    response.getJSONObject("data").getString("message"));
//                } catch (JSONException e) {
//                    Log.e("JSON Parse Error", e.getMessage());
//                }
//            } else {
//                try {
//                    navigateToSuccess(
//                            mBinding.editEmail.getText().toString(),
//                            response.getString("token")
//                    );
//                } catch (JSONException e) {
//                    Log.e("JSON Parse Error", e.getMessage());
//                }
//            }
//        } else {
//            Log.d("JSON Response", "No Response");
//        }
//
//    }

    /**
     * An observer on the HTTP Response from the web server. This observer should be
     * attached to SignInViewModel.
     *
     * @param response the Response from the server.
     */
    private void observeSignInResponse(final JSONObject response) {
        if (response.length() > 0) {
            if (response.has("code")) {
                try {
                    mBinding.editEmail.setError(
                            "Error Authenticating: " +
                                    response.getJSONObject("data").getString("message"));
                } catch (JSONException e) {
                    Log.e("JSON PARSE ERROR", e.getMessage());
                }
            } else {
                try {
                    mUserViewModel = new ViewModelProvider(getActivity(),
                            new UserInfoViewModel.UserInfoViewModelFactory(
                                    mBinding.editEmail.getText().toString(),
                                    response.getString("token")
                            )).get(UserInfoViewModel.class);
                    navigateToSuccess(mBinding.editEmail.getText().toString(),
                            mUserViewModel.getJwt());
                } catch (JSONException e) {
                    Log.e("JSON PARSE ERROR", e.getMessage());
                }
            }
        } else {
            Log.d("JSON RESPONSE", "NO RESPONSE");
        }
    }
}