package com.example.korresheader.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.korresheader.util.Contact;
import com.example.korresheader.R;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.util.List;

//This class is the Adapter for the List of Contacts in HomeActivity
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder>
        implements StickyRecyclerHeadersAdapter<ContactAdapter.HeaderViewHolder> {

    //A local value for the contacts
    private List<Contact> contactList;

    /**
     * Establish the value of the list of contacts
     * @param contactList The list of contacts
     */
    public void setContactList(List<Contact> contactList) {
        this.contactList = contactList;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_normal, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.contact.setText(contactList.get(position).getName());
    }

    @Override
    public long getHeaderId(int position) {
        return contactList.get(position).getName().toUpperCase().charAt(0);
    }

    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header, parent, false);
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(HeaderViewHolder headerViewHolder, int i) {
        headerViewHolder.header.setText(contactList.get(i).getName().toUpperCase().substring(0,1));
    }

    @Override
    public int getItemCount() {
        if (contactList == null) {
            return 0;
        } else {
            return contactList.size();
        }
    }

    //Inner class for the HeaderViewHolder
    protected class HeaderViewHolder extends RecyclerView.ViewHolder {

        private TextView header;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.headerText);
        }
    }

    //Inner class for the ContactName ViewHolder
    protected class ContactViewHolder extends RecyclerView.ViewHolder {

        private TextView contact;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            contact = itemView.findViewById(R.id.itemText);
        }
    }

}
