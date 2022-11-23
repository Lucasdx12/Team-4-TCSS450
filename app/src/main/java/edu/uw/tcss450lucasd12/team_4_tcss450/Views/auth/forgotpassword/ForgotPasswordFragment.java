package edu.uw.tcss450lucasd12.team_4_tcss450.Views.auth.forgotpassword;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450lucasd12.team_4_tcss450.R;
import edu.uw.tcss450lucasd12.team_4_tcss450.Views.auth.register.RegisterViewModel;
import edu.uw.tcss450lucasd12.team_4_tcss450.databinding.FragmentForgotPasswordBinding;
import edu.uw.tcss450lucasd12.team_4_tcss450.databinding.FragmentRegisterBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForgotPasswordFragment extends Fragment {

    private FragmentForgotPasswordBinding binding;

    private ForgotPasswordViewModel mForgotPasswordModel;

    public ForgotPasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
}