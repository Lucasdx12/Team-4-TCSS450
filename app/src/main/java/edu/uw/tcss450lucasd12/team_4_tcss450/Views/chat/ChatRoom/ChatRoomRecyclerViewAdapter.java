package edu.uw.tcss450lucasd12.team_4_tcss450.Views.chat.ChatRoom;

import android.content.res.Resources;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.ColorUtils;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.shape.CornerFamily;

import java.util.List;

import edu.uw.tcss450lucasd12.team_4_tcss450.R;
import edu.uw.tcss450lucasd12.team_4_tcss450.databinding.FragmentChatRoomBinding;
import edu.uw.tcss450lucasd12.team_4_tcss450.databinding.FragmentChatRoomCardBinding;
import edu.uw.tcss450lucasd12.team_4_tcss450.databinding.FragmentSignInBinding;

/**
 *
 * @author Paul Lee
 * @version Fall 2022
 */
public class ChatRoomRecyclerViewAdapter extends RecyclerView.Adapter<ChatRoomRecyclerViewAdapter.ChatRoomViewHolder> {

    private final List<ChatRoom> mMessages;
    private final String mEmail;

    public ChatRoomRecyclerViewAdapter(List<ChatRoom> messages, String email) {
        this.mMessages = messages;
        this.mEmail = email;
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    @NonNull
    @Override
    public ChatRoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatRoomViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_chat_room_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatRoomViewHolder holder, int position) {
        holder.setMessage(mMessages.get(position));
    }

    public class ChatRoomViewHolder extends RecyclerView.ViewHolder {
        private final View mView;
        private FragmentChatRoomCardBinding mBinding;

        public ChatRoomViewHolder(@NonNull View view) {
            super(view);
            mView = view;
            mBinding = FragmentChatRoomCardBinding.bind(view);
        }

        void setMessage(final ChatRoom message) {
            final Resources res = mView.getContext().getResources();

            int standard = (int) res.getDimension(R.dimen.chat_margin);
            int extended = (int) res.getDimension(R.dimen.chat_margin_sided);

            if (mEmail.equals(message.getSender())) {
                // This message is from the user. Format it so that the message
                // sent by this user is on the right side.
                mBinding.textMessage.setText(message.getMessage());

                ViewGroup.MarginLayoutParams layoutParams =
                        (ViewGroup.MarginLayoutParams) mBinding.messageRoot.getLayoutParams();

                layoutParams.setMargins(extended, standard, standard, standard);

                ((FrameLayout.LayoutParams) mBinding.messageRoot.getLayoutParams()).gravity =
                        Gravity.END;

            } else {
                // This message is from another user. Format it so that the message
                // sent by other users is on the left side.
                mBinding.textMessage.setText(message.getSender() +
                        ": " + message.getMessage());

                ViewGroup.MarginLayoutParams layoutParams =
                        (ViewGroup.MarginLayoutParams) mBinding.messageRoot.getLayoutParams();

                layoutParams.setMargins(standard, standard, extended, standard);

                ((FrameLayout.LayoutParams) mBinding.messageRoot.getLayoutParams()).gravity =
                        Gravity.START;

            }
        }
    }

}
