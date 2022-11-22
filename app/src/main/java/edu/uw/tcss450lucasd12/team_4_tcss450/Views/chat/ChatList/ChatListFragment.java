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
        List<ChatList> lists = new ArrayList<>();
        ChatList first = new ChatList(1, "Global Chat", "untitled", "N/A");
        ChatList second = new ChatList(2, "Chat", "untitled2", "N/A2");


        lists.add(first);
        lists.add(second);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_list, container, false);
        if (view instanceof RecyclerView) {
            ((RecyclerView) view).setAdapter(
                    new ChatListRecyclerViewAdapter(lists));
        }
        return view;
    }
}