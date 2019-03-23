package com.example.slider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ContactsActivity extends Activity implements
        OnClickListener {
    Button button;
    DatabaseReference database;
    String[] contact;
    ListView listView;
    String email;
    ArrayList<String> contacts;
    ArrayAdapter<String> adapter;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        database = FirebaseDatabase.getInstance().getReference();
        setContentView(R.layout.activity_contacts);

        findViewsById();
        getIncomingIntent();
        String[] sports = {"Nehru", "Newton", "Manikanta", "Ratna babu"};
        ArrayList<Contact> arr = new ArrayList<>();
        Contact ct = new Contact("Nehru", "9494055550");
        Contact ct1 = new Contact("Newton", "9666039793");
        arr.add(ct);
        arr.add(ct1);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, contacts);
        System.out.println(contacts);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(adapter);


        button.setOnClickListener(this);
    }

    private void findViewsById() {
        listView = (ListView) findViewById(R.id.list);
        button = (Button) findViewById(R.id.testbutton);
    }

    public void getIncomingIntent() {
        if (getIntent().hasExtra("myList")) {
            Bundle bundle = getIntent().getExtras();
            email = getIntent().getStringExtra("email");
            contacts = (ArrayList<String>) bundle.getStringArrayList("myList");
            contact = new String[contacts.size()];
            System.out.println(contacts);
            for (int i = 0; i < contacts.size(); i++) {
                contact[i] = contacts.get(i);
            }
        }
    }

    public void onClick(View v) {
        SparseBooleanArray checked = listView.getCheckedItemPositions();
        ArrayList<String> selectedItems = new ArrayList<String>();
        for (int i = 0; i < checked.size(); i++) {
            // Item position in adapter
            int position = checked.keyAt(i);
            // Add sport if it is checked i.e.) == TRUE!
            if (checked.valueAt(i))
                selectedItems.add(adapter.getItem(position));
        }

        final String[] outputStrArr = new String[selectedItems.size()];

        for (int i = 0; i < selectedItems.size(); i++) {
            outputStrArr[i] = selectedItems.get(i);

        }
        System.out.println(Arrays.toString(outputStrArr));

        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<String, String> map = new HashMap<>();
                for (int i = 0; i < outputStrArr.length; i++) {
                    String[] arr = outputStrArr[i].split(" -- ");
                    map.put(arr[0], arr[1]);
                }
                database.child("userContacts").child(email).setValue(map);
                Intent intent = new Intent(ContactsActivity.this, SOSActivity.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        database.child("userContacts").child(email).


//
//        Intent intent = new Intent(getApplicationContext(),
//                ResultActivity.class);
//
//        // Create a bundle object
//        Bundle b = new Bundle();
//        b.pu
//        b.putStringArray("selectedItems", outputStrArr);
//
//        // Add the bundle to the intent.
//        intent.putExtras(b);
//
//        // start the ResultActivity
//        startActivity(intent);
    }
}