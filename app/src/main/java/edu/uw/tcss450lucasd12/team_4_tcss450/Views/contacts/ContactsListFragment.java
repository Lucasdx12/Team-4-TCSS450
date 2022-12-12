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
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import edu.uw.tcss450lucasd12.team_4_tcss450.R;
import edu.uw.tcss450lucasd12.team_4_tcss450.Views.chat.ChatRoom.ChatRoomFragmentArgs;
import edu.uw.tcss450lucasd12.team_4_tcss450.databinding.FragmentContactsCardBinding;
import edu.uw.tcss450lucasd12.team_4_tcss450.databinding.FragmentContactsListBinding;
import edu.uw.tcss450lucasd12.team_4_tcss450.model.UserInfoViewModel;

public class ContactsListFragment extends Fragment {

    private ContactsViewModel mModel;
    private UserInfoViewModel mUserModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelProvider provider = new ViewModelProvider(getActivity());
        mModel = provider.get(ContactsViewModel.class);
        mUserModel = provider.get(UserInfoViewModel.class);

        mModel.connectGet(mUserModel.getJwt());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts_list, container, false);

//        if (view instanceof RecyclerView) {
//            ((RecyclerView) view).setAdapter(
//                    new ContactsRecyclerViewAdapter(ContactsListGenerator.getContactsList()));
//        }

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentContactsListBinding binding = FragmentContactsListBinding.bind(getView());

        mModel.addContactsObserver(getViewLifecycleOwner(), contactList -> {
            if (!contactList.isEmpty()) {
                binding.listRoot.setAdapter(
                        new ContactsRecyclerViewAdapter(contactList)
                );
                binding.layoutWait.setVisibility(View.GONE);
            }
        });
    }

}
