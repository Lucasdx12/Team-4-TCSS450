package edu.uw.tcss450lucasd12.team_4_tcss450.Views.chat.ChatList;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450lucasd12.team_4_tcss450.R;
import edu.uw.tcss450lucasd12.team_4_tcss450.Views.chat.ChatRoomSetting.ChatAddMemberViewModel;
import edu.uw.tcss450lucasd12.team_4_tcss450.databinding.FragmentChatListBinding;
import edu.uw.tcss450lucasd12.team_4_tcss450.model.UserInfoViewModel;

/**
 * A simple {@link Fragment} subclass.
 * @author Paul Lee
 * @version Fall 2022
 */
public class ChatListFragment extends Fragment {

    private ChatListViewModel mListModel;
    private UserInfoViewModel mUserModel;
    private ChatAddRoomViewModel mAddRoomModel;
    private ChatAddMemberViewModel mAddMemModel;

    public ChatListFragment() {
        // Empty constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelProvider provider = new ViewModelProvider(getActivity());
        mUserModel = provider.get(UserInfoViewModel.class);
        mListModel = provider.get(ChatListViewModel.class);
        mAddRoomModel = provider.get(ChatAddRoomViewModel.class);

        mAddMemModel = provider.get(ChatAddMemberViewModel.class);

        mListModel.getChats(mUserModel.getJwt());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentChatListBinding binding = FragmentChatListBinding.bind(getView());

        final RecyclerView recyclerView = binding.listRoot;
        recyclerView.setAdapter(new ChatListRecyclerViewAdapter(
                mListModel.getChatList()
        ));

        binding.addChatRoomButton.setOnClickListener(button -> openDialog());


        mListModel.addChatListObserver(getViewLifecycleOwner(), list -> {
            if (!list.isEmpty()) {
                recyclerView.getAdapter().notifyDataSetChanged();
                binding.layoutChatListWait.setVisibility(View.GONE);
            }

        });

        mAddRoomModel.addResponseObserver(getViewLifecycleOwner(), response -> {
            mAddMemModel.addMember(mAddRoomModel.getChatId(),
                    mUserModel.getJwt());
        });

        mAddMemModel.addResponseObserver(getViewLifecycleOwner(), response -> {
            mListModel.getChats(mUserModel.getJwt());
            recyclerView.getAdapter().notifyDataSetChanged();
        });
    }

    /**
     * Helper method to get the custom dialog box for adding
     * new chat rooms.
     */
    private void openDialog() {
        DialogFragment dialogFragment = new ChatAddDialog();
        dialogFragment.show(getChildFragmentManager(), "addroom");
    }
}