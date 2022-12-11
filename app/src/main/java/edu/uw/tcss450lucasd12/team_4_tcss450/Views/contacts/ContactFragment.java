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

import edu.uw.tcss450lucasd12.team_4_tcss450.R;
import edu.uw.tcss450lucasd12.team_4_tcss450.databinding.FragmentContactProfileBinding;
import edu.uw.tcss450lucasd12.team_4_tcss450.model.UserInfoViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_contact_profile, container, false);
        return v;
    }

    //initializes the Views w/ blog text
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ContactFragmentArgs args = ContactFragmentArgs.fromBundle(getArguments());
        FragmentContactProfileBinding binding = FragmentContactProfileBinding.bind(getView());

        binding.namep.setText(args.getContact().getNickname());
        int friendStatus = args.getContact().getFriendStatus();

        binding.friendButton.setOnClickListener(button ->
        {
            /*
            android:textOff="Status: Friend"
            android:textOn="Status: Not Friend"
             */
            //TODO: make field in UserInfoViewModel for getting friend status, name, & nickname
            if (friendStatus == 1) { //binding.friendButton.getText().equals("Status: Friend")
                args.getContact().setFriendStatus(0);
                binding.friendButton.setText(binding.friendButton.getTextOff());

            } else if (friendStatus == 0) { //binding.friendButton.getText().equals("Status: Not Friend")
                args.getContact().setFriendStatus(1);
                binding.friendButton.setText(binding.friendButton.getTextOn());
            }
//            binding.friendButton.setText();
        });

//        if(status == 1) {
//            contactStatus = "Friends";
//            binding.friendButton.setOnClickListener(button -> {
//                binding.buttonContactStatus.setText("Add Friend");
//                binding.buttonRemoveContact.setVisibility(View.GONE);
//                binding.buttonContactStatus.setOnClickListener(request ->{
//                    binding.buttonContactStatus.setText("Requested");
//                    binding.buttonRemoveContact.setText("Cancel Request");
//                    binding.buttonRemoveContact.setVisibility(View.VISIBLE);
//                });
//            });
//
//        } else {
//            contactStatus = "Add Friend";
//            binding.friendButton.setOnClickListener(button -> {
//                binding.friendButton.setText("Requested");
//                binding.friendButton.setText("Cancel Request");
//                binding.friendButton.setVisibility(View.VISIBLE);
//                binding.friendButton.setOnClickListener(request ->{
//                    binding.friendButton.setVisibility(View.GONE);
//                    binding.friendButton.setText("Add Friend");
//                });
//            });
//
//            binding.buttonRemoveContact.setVisibility(View.GONE);
//        }
//
//        binding.buttonContactStatus.setText(contactStatus);
    }


}