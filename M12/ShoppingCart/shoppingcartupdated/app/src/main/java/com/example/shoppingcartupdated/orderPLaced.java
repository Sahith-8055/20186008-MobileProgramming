package com.example.shoppingcartupdated;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class orderPLaced extends AppCompatActivity {
    TextView txtView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        txtView = (TextView) findViewById(R.id.successMessage);
        setContentView(R.layout.activity_order_placed);
//        try {
//            // Simulate network access.
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//
//        }
        start();



//        View.getContext().startActivty(inten);

    }

    public void start() {
        Intent inten = new Intent(orderPLaced.this, MainActivity.class);
        startActivity(inten);
    }
}

