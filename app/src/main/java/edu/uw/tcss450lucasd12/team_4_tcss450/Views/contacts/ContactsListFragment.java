package edu.uw.tcss450lucasd12.team_4_tcss450.Views.contacts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import edu.uw.tcss450lucasd12.team_4_tcss450.R;

public class ContactsListFragment extends Fragment {

    public ContactsListFragment() {
        //empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts_list, container, false);
        if (view instanceof RecyclerView) {
            ((RecyclerView) view).setAdapter(
                    new ContactsRecyclerViewAdapter(ContactsListGenerator.getContactsList()));
        }
        return view;
    }

}
