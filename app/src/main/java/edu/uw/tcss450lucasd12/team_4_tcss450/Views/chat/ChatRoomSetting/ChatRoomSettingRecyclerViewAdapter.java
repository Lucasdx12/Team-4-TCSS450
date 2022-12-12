package edu.uw.tcss450lucasd12.team_4_tcss450.Views.chat.ChatRoomSetting;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450lucasd12.team_4_tcss450.R;
import edu.uw.tcss450lucasd12.team_4_tcss450.databinding.FragmentChatMemberCardBinding;

/**
 * Sets up the recycler view in chat room setting to get the list of emails
 * and a button to remove them from the chat room.
 *
 * @author Paul Lee
 * @version Fall 2022
 */
public class ChatRoomSettingRecyclerViewAdapter extends RecyclerView.Adapter<ChatRoomSettingRecyclerViewAdapter.ChatRoomSettingViewHolder> {
    private final List<String> mEmails;
    private final int mChatId;
    private final String mJwt;
    private final ChatRemoveMemberViewModel mDelMem;

    /**
     * Constructor
     *
     * @param emails list of emails
     * @param chatId the chat id of the chatroom
     * @param jwt the users signed JWT
     * @param delMem View Model to include the button to delete the member for each card.
     */
    public ChatRoomSettingRecyclerViewAdapter(List<String> emails, int chatId, String jwt, ChatRemoveMemberViewModel delMem) {
        this.mEmails = emails;
        this.mChatId = chatId;
        this.mJwt = jwt;
        this.mDelMem = delMem;
    }

    @Override
    public int getItemCount() {
        return mEmails.size();
    }

    @NonNull
    @Override
    public ChatRoomSettingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatRoomSettingViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_chat_member_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatRoomSettingViewHolder holder, int position) {
        holder.setEmails(mEmails.get(position));
    }

    /**
     * Objects from this class represent an Individual row View from the List
     * of rows in the Chat Room Setting Recycler View
     */
    public class ChatRoomSettingViewHolder extends RecyclerView.ViewHolder {
        private final View mView;
        private FragmentChatMemberCardBinding mBinding;

        /**
         * Constructor
         *
         * @param view View
         */
        public ChatRoomSettingViewHolder(@NonNull View view) {
            super(view);
            mView = view;
            mBinding = FragmentChatMemberCardBinding.bind(view);
        }

        /**
         * Properly set the email card
         *
         * @param email the email to set
         */
        void setEmails(final String email) {

            mBinding.textMemEmail.setText(email);
            mBinding.buttonDelete.setOnClickListener(button -> {
                mDelMem.deleteMember(mChatId, mJwt, email);
                mEmails.remove(email);
                notifyDataSetChanged();
            });
        }
    }
}
