package edu.uw.tcss450lucasd12.team_4_tcss450.Views.contacts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.io.Serializable;

import edu.uw.tcss450lucasd12.team_4_tcss450.R;
import edu.uw.tcss450lucasd12.team_4_tcss450.databinding.FragmentContactProfileBinding;
import edu.uw.tcss450lucasd12.team_4_tcss450.model.UserInfoViewModel;

public class Contact implements Serializable {

    private final String mNickname;
    private final String mEmail;
    private int mFriendStatus;

    public static class Builder {
        private final String mNickname;
        private final String mEmail;
        private int mFriendStatus;

        public Builder(String nickName, String email, int friendStatus) {
            this.mNickname = nickName;
            this.mEmail = email;
            this.mFriendStatus = friendStatus; //0=not-friend, 1=friend, 2=blocked
        }

//        public Builder addEmail(final String val) {
//            mEmail = val;
//            return this;
//        }

        public Contact build() {
            return new Contact(this);
        }

    }

    private Contact(final Builder builder) {
        this.mNickname = builder.mNickname;
        this.mEmail = builder.mEmail;
    }

    public String getNickname() {
        return this.mNickname;
    }

    public String getEmail() {
        return this.mEmail;
    }

    public int getFriendStatus() {
        return this.mFriendStatus;
    }

    public void setFriendStatus(int newStatus) {
        this.mFriendStatus = newStatus;
    }
}
