package edu.uw.tcss450lucasd12.team_4_tcss450.Views.landing;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450lucasd12.team_4_tcss450.R;
import edu.uw.tcss450lucasd12.team_4_tcss450.Views.chat.ChatList.ChatList;
import edu.uw.tcss450lucasd12.team_4_tcss450.databinding.FragmentChatCardBinding;

public class LandingRecyclerViewAdapter extends RecyclerView.Adapter<LandingRecyclerViewAdapter.LandingViewHolder> {
    public List<ChatList> mChats;

    public LandingRecyclerViewAdapter(List<ChatList> items) {
        this.mChats = items;
    }

    @Override
    public int getItemCount() {
        return mChats.size();
    }

    @NonNull
    @Override
    public LandingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LandingViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_chat_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LandingViewHolder holder, int position) {
        holder.setChat(mChats.get(position));
    }

    public class LandingViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public FragmentChatCardBinding mBinding;

        public LandingViewHolder(View view) {
            super(view);
            mView = view;
            mBinding = FragmentChatCardBinding.bind(view);
        }

        void setChat(final ChatList chat) {
            mBinding.cardRoot.setOnClickListener(button -> {
                Navigation.findNavController(mView).navigate(
                        LandingFragmentDirections
                                .actionLandingToChatRoomFragment(chat));
            });
        }
    }
}
