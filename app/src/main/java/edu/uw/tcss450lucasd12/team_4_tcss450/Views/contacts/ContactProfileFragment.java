package edu.uw.tcss450lucasd12.team_4_tcss450.Views.contacts;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450lucasd12.team_4_tcss450.R;
import edu.uw.tcss450lucasd12.team_4_tcss450.databinding.FragmentContactProfileBinding;
import edu.uw.tcss450lucasd12.team_4_tcss450.model.UserInfoViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactProfileFragment extends Fragment {

    private ContactsViewModel mContactsModel;
    private UserInfoViewModel mUserModel;

    public ContactProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelProvider provider = new ViewModelProvider(getActivity());
        mUserModel = provider.get(UserInfoViewModel.class);
        mContactsModel = provider.get(ContactsViewModel.class);
//        mContactsModel.getFirstMessages(HARD_CODED_CHAT_ID, mUserModel.getmJwt());

//        setContentView(R.layout.fragment_contacts_profile_info);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_profile, container, false);
        if (view instanceof RecyclerView) {
            ((RecyclerView) view).setAdapter(
                    new ContactsRecyclerViewAdapter(ContactsListGenerator.getContactsList()));
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        FragmentContactsListBinding binding = FragmentContactsListBinding.bind(getView());
        FragmentContactProfileBinding binding = FragmentContactProfileBinding.bind(getView());
//        binding.namep.equals(binding2.namep);
//        binding.namep.setText((CharSequence) binding2.namep);
//        binding.imagep.equals(binding2.imagep);
        binding.friendButton.setOnClickListener(button ->
        {
            /*
            android:textOff="Status: Friend"
            android:textOn="Status: Not Friend"
             */
            //TODO: make field in UserInfoViewModel for getting friend status, name, & nickname
            int status = 0;
            if (binding.friendButton.getText().equals("Status: Friend")) {
                status = 1;
            } else if (binding.friendButton.getText().equals("Status: Not Friend")) {
                status = 0;
            }
            binding.friendButton.getId();

            mContactsModel.friendStatusProfile(mUserModel.getEmail(), mUserModel.getJwt(), status);
        });



//        setContentView(R.layout.fragment_contacts_profile_info);

//        final RecyclerView rv = binding.recyclerContacts;
//        //Set the Adapter to hold a reference to the list FOR THIS contact that the ViewModel
//        //holds.
//        rv.setAdapter(new ContactsRecyclerViewAdapter(
//                mContactsModel.selectContact(contactsList);
//
//        mContactsModel.addContactsObserver(HARD_CODED_CONTACT, getViewLifecycleOwner(),
//                list -> {
//                    /*
//                     * This solution needs work on the scroll position. As a group,
//                     * you will need to come up with some solution to manage the
//                     * recyclerview scroll position. You also should consider a
//                     * solution for when the keyboard is on the screen.
//                     */
//                    //inform the RV that the underlying list has (possibly) changed
//                    rv.getAdapter().notifyDataSetChanged();
//                    rv.scrollToPosition(rv.getAdapter().getItemCount() - 1);
//                });

//when we get the response back from the server, clear the edittext
//        mSendModel.addResponseObserver(getViewLifecycleOwner(), response ->
//                binding.editMessage.setText(""));
    }
}