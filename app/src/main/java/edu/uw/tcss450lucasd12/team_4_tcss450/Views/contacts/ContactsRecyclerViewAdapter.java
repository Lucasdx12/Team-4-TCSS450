package edu.uw.tcss450lucasd12.team_4_tcss450.Views.contacts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450lucasd12.team_4_tcss450.R;
import edu.uw.tcss450lucasd12.team_4_tcss450.databinding.FragmentContactsCardBinding;
/**
@author stephanie gibbs
 */
public class ContactsRecyclerViewAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<ContactsRecyclerViewAdapter.ContactsListViewHolder> {

    // Store all of the blogs to present
    public final List<Contact> mContacts;

    public ContactsRecyclerViewAdapter(List<Contact> items) {
        this.mContacts = items;
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    @NonNull
    @Override
    public ContactsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ContactsListViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_contacts_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsListViewHolder holder, int position) {
        holder.setContact(mContacts.get(position));
    }

    /**
     * Objects from this class represent an Individual row View from the List
     * of rows in the Chat List Recycler View.
     */
    public class ContactsListViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public FragmentContactsCardBinding mBinding;
        ImageView profileImage;
        TextView name, email;

        private Contact mContact;

        public ContactsListViewHolder(View view) {
            super(view);
            mView = view;
            mBinding = FragmentContactsCardBinding.bind(view);
            profileImage = itemView.findViewById(R.id.imagep);
            name = itemView.findViewById(R.id.namep);
            email = itemView.findViewById(R.id.emailp);

        }

        void setContact(final Contact contact) {
            mContact = contact;

            // TODO: Take the user to specific user's profile.
            mBinding.contactCard.setOnClickListener(view -> {
                Navigation.findNavController(mView).navigate(
                        ContactsListFragmentDirections
                                .actionContactsToContactFragment(contact));
            });

            mBinding.namep.setText(contact.getNickname());
            mBinding.emailp.setText(contact.getEmail());

        }
    }
}
