package edu.uw.tcss450lucasd12.team_4_tcss450.Views.contacts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import edu.uw.tcss450lucasd12.team_4_tcss450.R;
import edu.uw.tcss450lucasd12.team_4_tcss450.Views.chat.ChatRoom.ChatRoomRecyclerViewAdapter;
import edu.uw.tcss450lucasd12.team_4_tcss450.Views.chat.ChatRoom.ChatRoomViewModel;
import edu.uw.tcss450lucasd12.team_4_tcss450.Views.chat.ChatRoom.ChatSendViewModel;
import edu.uw.tcss450lucasd12.team_4_tcss450.databinding.FragmentChatRoomBinding;
import edu.uw.tcss450lucasd12.team_4_tcss450.databinding.FragmentContactsCardBinding;
import edu.uw.tcss450lucasd12.team_4_tcss450.model.UserInfoViewModel;

public class ContactsListFragment extends Fragment {
    //for invites page:
//    private ContactsViewModel contactsViewModel;

    private ContactsViewModel mContactsModel;
    private UserInfoViewModel mUserModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts_list, container, false);
        if (view instanceof RecyclerView) {
            ((RecyclerView) view).setAdapter(
                    new ContactsRecyclerViewAdapter(ContactsListGenerator.getContactsList()));
        }
        return view;
    }


//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        //Local access to the ViewBinding object. No need to create as Instance Var as it is only
//        //used here.
//        FragmentContactsCardBinding binding = FragmentContactsCardBinding.bind(getView());
//        //Note argument sent to the ViewModelProvider constructor. It is the Activity that
//        //holds this fragment.
//        UserInfoViewModel model = new ViewModelProvider(getActivity())
//                .get(UserInfoViewModel.class);
//        binding.emailp.setText(getString(R.string.contacts, model.getEmail()));
//        //On button click, navigate to Second Home
//        binding.imagep.setOnClickListener(button ->
//                Navigation.findNavController(getView()).navigate(
//                        ContactsListFragment
//                                .actionHomeFragmentToSecondHomeFragment()));
//    }



    //    @Override
//    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        contactsViewModel = new ViewModelProvider(requireActivity()).get(ContactsViewModel.class);
//
//        //...
//
////        contacts.setOnClickListener(contact -> {
////            // Set a new item
////            contactsViewModel.selectContact(contact);
////        });
//    }


}
