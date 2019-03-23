package com.example.shoppingcartupdated;

import android.support.annotation.FontRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String URL = "http://msitmp.herokuapp.com/getproducts/20186034";
    String userEmail;
    TextView textview;
    DatabaseReference databaseProducts;
    List<ProductCollection> collection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(MainActivity.this);
        setContentView(R.layout.activity_main);
        collection = new ArrayList<>();
        databaseProducts = FirebaseDatabase.getInstance().getReference("products");
//        textview = (TextView) findViewById(R.id.data);
        final RecyclerView productList = (RecyclerView) findViewById(R.id.productList);
        productList.setLayoutManager(new LinearLayoutManager(this));
        extractIntent();
        StringRequest request = new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("JSON", response);
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                Products prods = gson.fromJson(response, Products.class);
//                for (ProductCollection product : prods.getProductCollection()) {
//                    databaseProducts.child(product.getProductId()).setValue(product);
//
//                    Log.d("PRODUCT", product.getCategory() + " -- " + product.getProductId());
////                    textview.setText(product.getProductId());
//                }
//                Toast.makeText(MainActivity.this, "Products Added", Toast.LENGTH_SHORT).show();

                databaseProducts.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            System.out.println(ds.getValue());
                            System.out.println(ds.getChildren());
                            System.out.println(ds.getValue(ProductCollection.class));
                            collection.add(ds.getValue(ProductCollection.class));
                        }
                        productList.setAdapter(new productListAdapter(collection, MainActivity.this, userEmail));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                System.out.println(collection);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    public void extractIntent() {
        if (getIntent().hasExtra("email")) {
            userEmail = getIntent().getStringExtra("email");
        }
    }
}