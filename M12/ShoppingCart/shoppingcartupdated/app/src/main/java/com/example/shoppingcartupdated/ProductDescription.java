package com.example.shoppingcartupdated;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.HashMap;

public class ProductDescription extends AppCompatActivity {
    Button addToCart;
    Button goToCart;
    String productId;
    String email;
    User user;
    int quant;
    Button plusCart;
    Button minusCart;
    String imageString;
    TextView selectedQuant;
//    ImageView imageDesc;
    String productDescription;
    DatabaseReference databaseUserCart;
    TextView price;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {


        databaseUserCart = FirebaseDatabase.getInstance().getReference();

//        imageDesc = (ImageView) findViewById(R.id.descImage);
        System.out.println("Hey Whazzuup");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_description);
        price = (TextView) findViewById(R.id.price);
        addToCart = (Button) findViewById(R.id.addToCart);
        plusCart = (Button) findViewById(R.id.plusCart);
        minusCart = (Button) findViewById(R.id.minusCart);
        selectedQuant = (TextView) findViewById(R.id.selectedQuant);
        Log.d("Description ACTIVITY", "onCreate Started");
        getIncomingIntent();

    }

    public void addToCartClick(View v) {

        final String[] arr = email.split("\\.");

        final HashMap<String, Long> map = new HashMap<>();
        Log.d("Email Check", email);
        Log.d("Email Again Check", Arrays.toString(arr));
        map.put(productId, (long) 1);
//        Log.d("Checkkkkingggg -----   ",user.getmEmail()+user.getmPhnNo());
//        final userCart usercart = new userCart(user.getmPhnNo(), user.getmEmail(),user.getUsername(), map);

        databaseUserCart.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.child("users").child(arr[0]).getValue(User.class);

                userCart usercart = new userCart(user.getmPhnNo(), user.getmEmail(),user.getUsername(), map);
                String name = user.getUsername();
                if (dataSnapshot.child("userCartDetails").hasChild(arr[0])) {
                    Toast.makeText(ProductDescription.this, "User has cart Items", Toast.LENGTH_LONG).show();
                    if (dataSnapshot.child("userCartDetails").child(arr[0]).hasChild("map")) {
                            HashMap<String, Long> map = (HashMap<String, Long>) dataSnapshot.child("userCartDetails").child(arr[0]).child("map").getValue();
                            boolean contains = map.containsKey(productId);
//                            if (contains) {
//                                map.put(productId, map.get(productId)+1);
//                                databaseUserCart.child("userCartDetails").child(arr[0]).child("map").setValue(map);
//                            } else {
//                                map.put(productId, (long) 1);
//                                databaseUserCart.child("userCartDetails").child(arr[0]).child("map").setValue(map);
//                            }
                            Long quantity = Long.parseLong(selectedQuant.getText().toString());
                            map.put(productId, quantity);
                            databaseUserCart.child("userCartDetails").child(arr[0]).child("map").setValue(map);
                    }
                } else {
                    databaseUserCart.child("userCartDetails").child(arr[0]).setValue(usercart);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Toast.makeText(ProductDescription.this, "In ADD to Cart", Toast.LENGTH_LONG).show();

    }

    public void goToCartClick(View v) {
        Intent toCart = new Intent(ProductDescription.this, cartPage.class);
        toCart.putExtra("email", email);
        startActivity(toCart);
    }

    public void plusCart(View v) {

        int selQuant = Integer.parseInt(selectedQuant.getText().toString()) + 1;
        System.out.println(selQuant);
        minusCart.setEnabled(true);
        if (selQuant <= quant) {
            selectedQuant.setText(selQuant +"");
        } else {
            Toast.makeText(this, "Sorry, you have reached the Maximum Limit", Toast.LENGTH_SHORT).show();
        }
    }

    public void minusCart(View v) {

        int selQuant = Integer.parseInt(selectedQuant.getText().toString()) - 1;
        System.out.println(selQuant);
        if (selQuant >= 1) {
            selectedQuant.setText(selQuant +"");
        } else {
            minusCart.setEnabled(false);
        }
    }



    public void getIncomingIntent() {
        Log.d("getIncomingIntent", "Checking for Incoming intents");
        if (getIntent().hasExtra("productCategory") && getIntent().hasExtra("productID")) {
            Log.d("getIncomingIntent", "Found Incoming intent with extras");
            TextView availableQuant = (TextView) findViewById(R.id.availableQuant);
//            TextView price = (TextView) findViewById(R.id.price);
            productDescription = getIntent().getStringExtra("productCategory");
            email = getIntent().getStringExtra("email");
            productId = getIntent().getStringExtra("productID");
            imageString = getIntent().getStringExtra("imageURL");
            quant = Integer.parseInt(getIntent().getStringExtra("quant"));

            String price1 = getIntent().getStringExtra("price")+"";
            price.setText(price1);
            availableQuant.setText("Quantity: " + quant);
            TextView txtView = findViewById(R.id.sampleText);
            txtView.setText(productDescription + productId);
            System.out.println(imageString);
            ImageView imageDesc = (ImageView) findViewById(R.id.descImage);
            Glide.with(this).asBitmap().load(imageString).into(imageDesc);

        }
    }

//    public void getUserDetails() {
//
//        final String[] arr = email.split("\\.");
//        database.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    user = dataSnapshot.child(arr[0]).getValue(User.class);
//                    String name = user.getUsername();
//                    Log.d("Checkkkk",user.getmEmail()+user.getmPhnNo());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
}
