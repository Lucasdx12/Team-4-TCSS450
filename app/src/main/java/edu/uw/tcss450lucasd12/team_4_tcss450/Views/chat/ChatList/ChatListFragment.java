package edu.uw.tcss450lucasd12.team_4_tcss450.Views.chat.ChatList;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450lucasd12.team_4_tcss450.R;

/**
 * A simple {@link Fragment} subclass.
 * @author Paul Lee
 * @version Fall 2022
 */
public class ChatListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_list, container, false);
        if (view instanceof RecyclerView) {
            ((RecyclerView) view).setAdapter(
                    new ChatListRecyclerViewAdapter(ChatListGenerator.getChatList()));
        }
        return view;
    }
}