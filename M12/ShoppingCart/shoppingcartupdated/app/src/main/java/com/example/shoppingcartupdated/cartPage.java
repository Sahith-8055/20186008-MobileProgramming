package com.example.shoppingcartupdated;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class cartPage extends AppCompatActivity {
    DatabaseReference databaseReference;
    String email;
    TextView txtView;
    List<ProductCollection> list;
    RecyclerView viewCart;
    String[] arr;
    Button placeOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        placeOrder = (Button) findViewById(R.id.placeOrder);
        list = new ArrayList<>();
        setContentView(R.layout.activity_cart_page);
        txtView = (TextView) findViewById(R.id.testing);
        final RecyclerView cartList = (RecyclerView) findViewById(R.id.cartList);
        cartList.setLayoutManager(new LinearLayoutManager(this));
//        viewCart = (RecyclerView) findViewById(R.id.cartList);
//        viewCart.setLayoutManager(new LinearLayoutManager(this));
        getIncomingIntent();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("userCartDetails").hasChild(arr[0])) {
                    for (DataSnapshot ds: dataSnapshot.child("userCartDetails").child(arr[0]).child("map").getChildren()) {
//                        txtView.setText(ds.getValue() + ds.getKey());
                        Log.d("KEY", ds.getKey());
                        ProductCollection pc = dataSnapshot.child("products").child(ds.getKey()).getValue(ProductCollection.class);
                        Log.d("Checker", pc.getCategory());
                        pc.setQuantity((Long)ds.getValue());
                        list.add(pc);
                    }
                } else {
                    txtView.setText("Your Cart is Empty");
                }
                System.out.println(list);
                cartList.setAdapter(new cartPageAdapter(list,cartPage.this, arr[0]));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void placeOrderClick(View v) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Toast.makeText(cartPage.this, "Booyeah", Toast.LENGTH_LONG).show();
                if (list.isEmpty()) {
                    Toast.makeText(cartPage.this, "Your Cart is Empty, please add Items to checkout", Toast.LENGTH_LONG).show();
                } else {
                    if (dataSnapshot.hasChild("products")) {
//                    int size = list.size();
                        for (int i = 0; i < list.size(); i++) {
                            String prodID = list.get(i).getProductId();
                            if (dataSnapshot.child("products").hasChild(prodID)) {
                                ProductCollection pro = dataSnapshot.child("products").child(prodID).getValue(ProductCollection.class);
                                Toast.makeText(cartPage.this, list.get(i).getQuantity() + "", Toast.LENGTH_LONG).show();
                                long quantity = pro.getQuantity() - list.get(i).getQuantity();
                                databaseReference.child("products").child(prodID).child("quantity").setValue(quantity);
                                System.out.println(prodID + " - " + i);
                            }
                            System.out.println(i);
                        }
                    }
                    clearCartClick();
                    Intent intent = new Intent(cartPage.this, MainActivity.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public void clearCartClick() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("userCartDetails").hasChild(arr[0])) {

                    databaseReference.child("userCartDetails").child(arr[0]).removeValue();
                    Toast.makeText(cartPage.this, "Booyeah", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void clearCartClick(View v) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("userCartDetails").hasChild(arr[0])) {

                    databaseReference.child("userCartDetails").child(arr[0]).removeValue();
                    Toast.makeText(cartPage.this, "Booyeah", Toast.LENGTH_LONG).show();
                }
                finish();
                startActivity(getIntent());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getIncomingIntent() {
        if (getIntent().hasExtra("email")) {
            arr = getIntent().getStringExtra("email").split("\\.");
            email = arr[0];

        }
    }


}
