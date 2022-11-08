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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.uw.tcss450lucasd12.team_4_tcss450.R;
import edu.uw.tcss450lucasd12.team_4_tcss450.databinding.FragmentChatRoomBinding;

/**
 * A simple {@link Fragment} subclass.
 * @author Paul Lee
 * @version Fall 2022
 */
public class ChatRoomFragment extends Fragment {

    private ChatRoomViewModel mModel;

    public ChatRoomFragment() {
        // Empty constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = new ViewModelProvider(getActivity()).get(ChatRoomViewModel.class);
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

        final RecyclerView recyclerView = binding.recyclerMessageBoard;

        recyclerView.setAdapter(new ChatRoomRecyclerViewAdapter(
                ChatRoomGenerator.getChatRoomList(),
                ChatRoomGenerator.getCHATS()[0].getSender()));

        binding.buttonSendMessage.setOnClickListener(button -> {
            if (!binding.editTextMessage.getText().toString().matches("")) {
                ChatRoom newMessage = new ChatRoom(0,
                        ChatRoomGenerator.getCHATS()[0].getUserName(),
                        ChatRoomGenerator.getCHATS()[0].getSender(),
                        binding.editTextMessage.getText().toString(),
                        getCurrentTime());

                ChatRoomGenerator.addMessage(newMessage);
                recyclerView.setAdapter(new ChatRoomRecyclerViewAdapter(
                        ChatRoomGenerator.getChatRoomList(),
                        ChatRoomGenerator.getCHATS()[0].getSender()));

                binding.editTextMessage.setText("");
            }
        });
    }

    private String getCurrentTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("H:mm");

        return simpleDateFormat.format(cal.getTime());
    }
}