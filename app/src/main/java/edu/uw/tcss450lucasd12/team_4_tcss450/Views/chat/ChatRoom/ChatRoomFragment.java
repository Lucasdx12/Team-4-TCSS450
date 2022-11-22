package edu.uw.tcss450lucasd12.team_4_tcss450.Views.chat.ChatRoom;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.uw.tcss450lucasd12.team_4_tcss450.R;
import edu.uw.tcss450lucasd12.team_4_tcss450.databinding.FragmentChatRoomBinding;
import edu.uw.tcss450lucasd12.team_4_tcss450.model.UserInfoViewModel;

/**
 * A simple {@link Fragment} subclass.
 * @author Paul Lee
 * @version Fall 2022
 */
public class ChatRoomFragment extends Fragment {

    private static final int HARD_CODED_CHAT_ID = 2;

    private ChatRoomViewModel mChatModel;
    private UserInfoViewModel mUserModel;
    private ChatSendViewModel mSendModel;

    private ChatRoomFragmentArgs mArgs;


    public ChatRoomFragment() {
        // Empty constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelProvider provider = new ViewModelProvider(getActivity());
        mUserModel = provider.get(UserInfoViewModel.class);
        mChatModel = provider.get(ChatRoomViewModel.class);
        mSendModel = provider.get(ChatSendViewModel.class);

        mArgs = ChatRoomFragmentArgs.fromBundle(getArguments());
        Log.e("MARGSTESTING", mArgs.getChat().getChatId() + "!!!");
        mChatModel.getFirstMessages(mArgs.getChat().getChatId(), mUserModel.getJwt());
        Log.e("CHATROOMFRAGMENT", mUserModel.getJwt());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat_room, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentChatRoomBinding binding = FragmentChatRoomBinding.bind(getView());


        Log.e("CHATROOMFRAGMENT", mArgs.getChat().getChatId() + "");

        // SetRefreshing shows the internal Swiper view progress bar. Show this until messages load.
        binding.swipeContainer.setRefreshing(true);

        final RecyclerView recyclerView = binding.recyclerMessageBoard;

            // Set the Adapter to hold a reference to the list FOR THIS chat ID that the ViewModel
            // holds.
            recyclerView.setAdapter(new ChatRoomRecyclerViewAdapter(
                    mChatModel.getMessageListByChatId(mArgs.getChat().getChatId()),
                    mUserModel.getEmail()));

            // Send button was clicked. Send the message via the SendViewModel.
            binding.buttonSendMessage.setOnClickListener(button -> {
                mSendModel.sendMessage(mArgs.getChat().getChatId(),
                        mUserModel.getJwt(),
                        binding.editTextMessage.getText().toString());
            });

            // When the user scrolls to the to of the recycler view, the swiper list will "refresh"
            // The user is out of messages, go out to the service and get more.
            binding.swipeContainer.setOnRefreshListener(() ->
                    mChatModel.getNextMessages(mArgs.getChat().getChatId(), mUserModel.getJwt()));

            mChatModel.addMessageObserver(mArgs.getChat().getChatId(), getViewLifecycleOwner(),
                    list -> {
                        /*
                         * This solution needs work on the scroll position. As a group,
                         * you will need to come up with some solution to manage the
                         * recyclerview scroll position. You also should consider a
                         * solution for when the keyboard is on the screen.
                         */
                        recyclerView.getAdapter().notifyDataSetChanged();
                        recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
                        binding.swipeContainer.setRefreshing(false);
                    });

        // When we get the response back from the server, clear the edittext.
        mSendModel.addResponseObserver(getViewLifecycleOwner(), response ->
                binding.editTextMessage.setText(""));
    }
}