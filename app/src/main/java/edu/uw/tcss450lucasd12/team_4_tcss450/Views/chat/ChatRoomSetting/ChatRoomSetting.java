package edu.uw.tcss450lucasd12.team_4_tcss450.Views.chat.ChatRoomSetting;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450lucasd12.team_4_tcss450.R;
import edu.uw.tcss450lucasd12.team_4_tcss450.databinding.FragmentChatRoomSettingBinding;
import edu.uw.tcss450lucasd12.team_4_tcss450.model.UserInfoViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatRoomSetting extends Fragment {

    private ChatAddMemberViewModel mAddMemModel;
    private ChatDeleteMemberViewModel mDelMemModel;
    private ChatGetEmailViewModel mEmailModel;
    private UserInfoViewModel mUserModel;

    private ChatRoomSettingArgs mArgs;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelProvider provider = new ViewModelProvider(getActivity());

        mAddMemModel = provider.get(ChatAddMemberViewModel.class);
        mDelMemModel = provider.get(ChatDeleteMemberViewModel.class);
        mEmailModel = provider.get(ChatGetEmailViewModel.class);
        mUserModel = provider.get(UserInfoViewModel.class);

        mArgs = ChatRoomSettingArgs.fromBundle(getArguments());
        mEmailModel.getAllEmails(mUserModel.getJwt(), mArgs.getChatId());
        Log.e("CHATIDSETTING", mArgs.getChatId() + " ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat_room_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentChatRoomSettingBinding binding = FragmentChatRoomSettingBinding.bind(getView());

        final RecyclerView recyclerView = binding.memberList;

        recyclerView.setAdapter(new ChatRoomSettingRecyclerViewAdapter(
                mEmailModel.getListOfEmailsByChatId(mArgs.getChatId()),
                mArgs.getChatId(),
                mUserModel.getJwt(),
                mDelMemModel
        ));

        binding.buttonAddMember.setOnClickListener(button -> {
            openDialog();
        });

        mEmailModel.addResponseObserver(mArgs.getChatId(), getViewLifecycleOwner(), list -> {
            recyclerView.getAdapter().notifyDataSetChanged();
        });

        mDelMemModel.addResponseObserver(getViewLifecycleOwner(), observer -> {
            recyclerView.getAdapter().notifyDataSetChanged();
        });
    }

    private void openDialog() {
        DialogFragment dialogFragment = new ChatAddMemDialog(mArgs.getChatId());
        dialogFragment.show(getChildFragmentManager(), "addmember");
    }
}