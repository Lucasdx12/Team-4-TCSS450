package edu.uw.tcss450lucasd12.team_4_tcss450.Views.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450lucasd12.team_4_tcss450.R;
import edu.uw.tcss450lucasd12.team_4_tcss450.databinding.FragmentSignInBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {

    public SignInFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentSignInBinding binding = FragmentSignInBinding.bind(requireView());
        binding.buttonLogin.setOnClickListener(button ->

            Navigation.findNavController(requireView()).navigate(
                SignInFragmentDirections
                        .actionSignInFragmentToMainActivity()
            )
        );
    }
}