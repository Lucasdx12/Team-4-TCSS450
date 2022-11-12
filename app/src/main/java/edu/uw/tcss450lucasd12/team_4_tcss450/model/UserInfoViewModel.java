package edu.uw.tcss450lucasd12.team_4_tcss450.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class UserInfoViewModel extends ViewModel {

    private final String mEmail;
    private final String mJwt;

    private UserInfoViewModel(String email, String jwt) {
        this.mEmail = email;
        this.mJwt = jwt;
    }

    public String getEmail() {
        return this.mEmail;
    }

    public String getJwt() {
        return this.mJwt;
    }

    public static class UserInfoViewModelFactory implements ViewModelProvider.Factory {
        private final String mEmail;
        private final String mJwt;

        public UserInfoViewModelFactory(String email, String jwt) {
            this.mEmail = email;
            this.mJwt = jwt;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass == UserInfoViewModel.class) {
                return (T) new UserInfoViewModel(mEmail, mJwt);
            }
            throw new IllegalArgumentException(
                    "Argument must be: " + UserInfoViewModel.class);
        }
    }
}
