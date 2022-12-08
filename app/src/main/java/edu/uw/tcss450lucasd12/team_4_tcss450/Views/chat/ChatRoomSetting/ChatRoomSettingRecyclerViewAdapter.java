package edu.uw.tcss450lucasd12.team_4_tcss450.Views.chat.ChatRoomSetting;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450lucasd12.team_4_tcss450.R;
import edu.uw.tcss450lucasd12.team_4_tcss450.databinding.FragmentChatMemberCardBinding;

public class ChatRoomSettingRecyclerViewAdapter extends RecyclerView.Adapter<ChatRoomSettingRecyclerViewAdapter.ChatRoomSettingViewHolder> {
    private final List<String> mEmails;
    private final int mChatId;
    private final String mJwt;
    private final ChatDeleteMemberViewModel mDelMem;

    public ChatRoomSettingRecyclerViewAdapter(List<String> emails, int chatId, String jwt, ChatDeleteMemberViewModel delMem) {
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
     *
     */
    public class ChatRoomSettingViewHolder extends RecyclerView.ViewHolder {
        private final View mView;
        private FragmentChatMemberCardBinding mBinding;

        public ChatRoomSettingViewHolder(@NonNull View view) {
            super(view);
            mView = view;
            mBinding = FragmentChatMemberCardBinding.bind(view);
        }

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
