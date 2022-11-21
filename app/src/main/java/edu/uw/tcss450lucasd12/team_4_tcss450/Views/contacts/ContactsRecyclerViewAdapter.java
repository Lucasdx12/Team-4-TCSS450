package edu.uw.tcss450lucasd12.team_4_tcss450.Views.contacts;

import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450lucasd12.team_4_tcss450.R;
import edu.uw.tcss450lucasd12.team_4_tcss450.databinding.FragmentContactsCardBinding;

public class ContactsRecyclerViewAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<ContactsRecyclerViewAdapter.ContactsListViewHolder> {

    // Store all of the blogs to present
    public final List<ContactsList> mContacts;

    public ContactsRecyclerViewAdapter(List<ContactsList> items) {
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
//        String userImage = mContacts.get(position).getImage(); //TODO: add image for contact
        String username = mContacts.get(position).getNickname();
        String usermail = mContacts.get(position).getEmail();
        holder.name.setText(username);
        holder.email.setText(usermail);

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

        private ContactsList mContact;

        public ContactsListViewHolder(View view) {
            super(view);
            mView = view;
            mBinding = FragmentContactsCardBinding.bind(view);
            profileImage = itemView.findViewById(R.id.imagep);
            name = itemView.findViewById(R.id.namep);
            email = itemView.findViewById(R.id.emailp);
        }

        void setContact(final ContactsList contact) {
            mContact = contact;
            mBinding.cardRoot.setOnClickListener(view -> {
                // TODO: Take the user to specific user's profile.
            });

//            final String preview1 = Html.fromHtml(
//                            contact.getNickname(),
//                            Html.FROM_HTML_MODE_COMPACT)
//                    .toString().substring(0,30);
//
//            final String preview2 = Html.fromHtml(
//                            contact.getEmail(),
//                            Html.FROM_HTML_MODE_COMPACT)
//                    .toString().substring(0,30);

            mBinding.namep.setText(contact.getNickname());
            mBinding.emailp.setText(contact.getEmail());

        }
    }
}
