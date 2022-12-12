package edu.uw.tcss450lucasd12.team_4_tcss450.Views.chat.ChatList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450lucasd12.team_4_tcss450.R;
import edu.uw.tcss450lucasd12.team_4_tcss450.databinding.FragmentChatCardBinding;

/**
 * Recycler view for the chat list in which each card contains different
 * chat rooms.
 *
 * @author Paul Lee
 * @version Fall 2022
 */
public class ChatListRecyclerViewAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<ChatListRecyclerViewAdapter.ChatListViewHolder> {

    // Store all of the chats to present
    private final List<ChatList> mChats;

    /**
     * Constructor
     *
     * @param items list of ChatList
     */
    public ChatListRecyclerViewAdapter(List<ChatList> items) {

        this.mChats = items;
    }

    @Override
    public int getItemCount() {
        return mChats.size();
    }

    @NonNull
    @Override
    public ChatListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatListViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_chat_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListViewHolder holder, int position) {
        holder.setChat(mChats.get(position));
    }

    /**
     * Objects from this class represent an Individual row View from the List
     * of rows in the Chat List Recycler View.
     */
    public class ChatListViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public FragmentChatCardBinding mBinding;

        /**
         * Constructor
         *
         * @param view View
         */
        public ChatListViewHolder(View view) {
            super(view);
            mView = view;
            mBinding = FragmentChatCardBinding.bind(view);
        }

        /**
         * Properly set the chat list card
         *
         * @param chat the chat list card to set
         */
        void setChat(final ChatList chat) {
            mBinding.cardRoot.setOnClickListener(button -> {
                Navigation.findNavController(mView).navigate(
                        ChatListFragmentDirections
                                .actionChatToChatRoomFragment(chat));
            });

            mBinding.textChatTitle.setText(chat.getTitle());
        }
    }
}
