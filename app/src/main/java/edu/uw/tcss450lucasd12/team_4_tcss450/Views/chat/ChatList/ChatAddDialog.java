package edu.uw.tcss450lucasd12.team_4_tcss450.Views.chat.ChatList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import edu.uw.tcss450lucasd12.team_4_tcss450.R;
import edu.uw.tcss450lucasd12.team_4_tcss450.databinding.FragmentAddChatCardBinding;
import edu.uw.tcss450lucasd12.team_4_tcss450.model.UserInfoViewModel;

public class ChatAddDialog extends DialogFragment {

    private ChatAddRoomViewModel mAddRoomModel;
    private UserInfoViewModel mUserModel;

    private FragmentAddChatCardBinding mBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelProvider provider = new ViewModelProvider(getActivity());

        mAddRoomModel = provider.get(ChatAddRoomViewModel.class);
        mUserModel = provider.get(UserInfoViewModel.class);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_add_chat_card, null);
        mBinding = FragmentAddChatCardBinding.bind(view);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
        // Add action buttons
                .setPositiveButton(R.string.create_room, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mAddRoomModel.createRoom(mUserModel.getJwt(),
                                mBinding.editTextRoomName.getText().toString());
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ChatAddDialog.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }
}
