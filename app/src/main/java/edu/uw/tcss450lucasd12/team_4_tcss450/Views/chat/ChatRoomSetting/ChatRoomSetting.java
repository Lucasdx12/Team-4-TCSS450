package edu.uw.tcss450lucasd12.team_4_tcss450.Views.chat.ChatRoomSetting;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450lucasd12.team_4_tcss450.R;
import edu.uw.tcss450lucasd12.team_4_tcss450.Views.chat.ChatRoom.ChatAddMemberViewModel;
import edu.uw.tcss450lucasd12.team_4_tcss450.Views.chat.ChatRoom.ChatDeleteMemberViewModel;
import edu.uw.tcss450lucasd12.team_4_tcss450.Views.chat.ChatRoom.ChatGetEmailViewModel;
import edu.uw.tcss450lucasd12.team_4_tcss450.databinding.FragmentChatRoomBinding;
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

//    private ChatRoomSettingArgs mArgs;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelProvider provider = new ViewModelProvider(getActivity());

        mAddMemModel = provider.get(ChatAddMemberViewModel.class);
        mDelMemModel = provider.get(ChatDeleteMemberViewModel.class);
        mEmailModel = provider.get(ChatGetEmailViewModel.class);
        mUserModel = provider.get(UserInfoViewModel.class);

//        mArgs = ChatRoomSettingArgs.fromBundle(getArguments());
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

//        mDelMemModel.deleteMember(mArgs.getChat().getMessageId(), mUserModel.getJwt(), mUserModel.getEmail());

    }
}