package com.example.slider;

import android.content.Context;
import android.graphics.Movie;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends ArrayAdapter<Contact> {

    private Context mContext;
    private List<Contact> contactList = new ArrayList<>();

    public ContactAdapter(@NonNull Context context, ArrayList<Contact> list) {
        super(context, 0 , list);
        mContext = context;
        contactList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.contacts_list,parent,false);

        Contact currentContact = contactList.get(position);

        TextView name = (TextView) listItem.findViewById(R.id.name);
        name.setText(currentContact.getName());

        TextView release = (TextView) listItem.findViewById(R.id.phnNo);
        release.setText(currentContact.getPhnNO());

        return listItem;
    }
}
