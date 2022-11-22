package edu.uw.tcss450lucasd12.team_4_tcss450.Views.chat.ChatList;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import Helpers.ChatHelper;
import edu.uw.tcss450lucasd12.team_4_tcss450.R;
import edu.uw.tcss450lucasd12.team_4_tcss450.databinding.FragmentChatCardBinding;
import edu.uw.tcss450lucasd12.team_4_tcss450.databinding.FragmentChatListBinding;
import edu.uw.tcss450lucasd12.team_4_tcss450.model.UserInfoViewModel;

/**
 * A simple {@link Fragment} subclass.
 * @author Paul Lee
 * @version Fall 2022
 */
public class ChatListFragment extends Fragment {

    public ChatListFragment() {
        // Empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ChatHelper chatHelper = new ChatHelper();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_list, container, false);
        if (view instanceof RecyclerView) {
            ((RecyclerView) view).setAdapter(
                    new ChatListRecyclerViewAdapter(chatHelper.getChatList()));
        }
        return view;
    }
}