package edu.uw.tcss450lucasd12.team_4_tcss450.Views.chat.ChatRoomSetting;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import edu.uw.tcss450lucasd12.team_4_tcss450.R;
import edu.uw.tcss450lucasd12.team_4_tcss450.databinding.FragmentAddMemberCardBinding;
import edu.uw.tcss450lucasd12.team_4_tcss450.model.UserInfoViewModel;

public class ChatAddMemDialog extends DialogFragment {

    private ChatAddMemberViewModel mAddMemberModel;
    private UserInfoViewModel mUserModel;

    private FragmentAddMemberCardBinding mBinding;
    private final int mChatId;

    public ChatAddMemDialog(int chatId) {
        mChatId = chatId;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelProvider provider = new ViewModelProvider(getActivity());

        mUserModel = provider.get(UserInfoViewModel.class);
        mAddMemberModel = provider.get(ChatAddMemberViewModel.class);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_add_member_card, null);
        mBinding = FragmentAddMemberCardBinding.bind(view);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.add_member, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mBinding.editTextEmail.getText().toString();
                        mAddMemberModel.addOtherMembers(mChatId,
                                mUserModel.getJwt(),
                                mBinding.editTextEmail.getText().toString());
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ChatAddMemDialog.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }
}
