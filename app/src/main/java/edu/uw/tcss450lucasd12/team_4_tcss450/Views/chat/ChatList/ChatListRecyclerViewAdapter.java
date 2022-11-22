package edu.uw.tcss450lucasd12.team_4_tcss450.Views.chat.ChatList;

import android.content.res.Resources;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450lucasd12.team_4_tcss450.R;
import edu.uw.tcss450lucasd12.team_4_tcss450.databinding.FragmentChatCardBinding;

/**
 *
 * @author Paul Lee
 * @version Fall 2022
 */
public class ChatListRecyclerViewAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<ChatListRecyclerViewAdapter.ChatListViewHolder> {

    // Store all of the chats to present
    private final List<ChatList> mChats;

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

        private ChatList mChatList;

        public ChatListViewHolder(View view) {
            super(view);
            mView = view;
            mBinding = FragmentChatCardBinding.bind(view);
        }

        void setChat(final ChatList chat) {
//            final Resources res = mView.getContext().getResources();
//            final CardView card = mBinding.cardRoot;
//
//            int standard = (int) res.getDimension(R.dimen.chat_margin);
//            int extended = (int) res.getDimension(R.dimen.chat_margin_sided);
//            mChatList = chat;
//            Log.i("CHATLISTRECYCLERVIEW", chat.getChatId() + "");
            mBinding.cardRoot.setOnClickListener(button -> {
                Navigation.findNavController(mView).navigate(
                        ChatListFragmentDirections
                                .actionChatToChatRoomFragment(chat));
            });
//
            mBinding.textChatTitle.setText(chat.getTitle());
            mBinding.textRecentMessage.setText(chat.getRecentMessage());
            mBinding.textTimeOfMessage.setText(chat.getTimeStamp());
//
//            ViewGroup.MarginLayoutParams layoutParams =
//                    (ViewGroup.MarginLayoutParams) card.getLayoutParams();
//
//            layoutParams.setMargins(extended, extended, extended, extended);
//
//            card.setCardBackgroundColor(res.getColor(R.color.purple_700, null));
//            card.setMinimumWidth(standard / 5);
//
//            mBinding.textChatTitle.setTextColor(
//                    res.getColor(R.color.white, null));
//            mBinding.textRecentMessage.setTextColor(
//                    res.getColor(R.color.black, null));
//            mBinding.textTimeOfMessage.setTextColor(
//                    res.getColor(R.color.black, null));
//
//            card.requestLayout();
        }
    }
}
