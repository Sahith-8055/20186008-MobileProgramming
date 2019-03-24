package com.example.shoppingcartupdated;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class cartPageAdapter extends RecyclerView.Adapter<cartPageAdapter.cartPageViewHolder>{

    private Context mContext;
    private List<ProductCollection> list;
    private String mail;
    private DatabaseReference database;
    public cartPageAdapter(List<ProductCollection> lis, Context context, String email) {
        this.list = lis;
        this.mContext = context;
        this.mail = email;
    }


    @NonNull
    @Override
    public cartPageAdapter.cartPageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.cart_list_item, viewGroup, false);
        return new cartPageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull cartPageAdapter.cartPageViewHolder cartPageViewHolder, final int i) {
        final String dataa = list.get(i).getCategory();
        cartPageViewHolder.txtView.setText(dataa + list.get(i).getProductId());
        long quant = list.get(i).getQuantity();
        cartPageViewHolder.selectedQuant.setText("Quantity: 1 X " + list.get(i).getQuantity() + " = " + list.get(i).getQuantity());
        cartPageViewHolder.priceDisp.setText(list.get(i).getCurrencyCode() + " " + quant * list.get(i).getPrice());
        cartPageViewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, dataa, Toast.LENGTH_LONG).show();
            }
        });
        cartPageViewHolder.removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String prodID = list.get(i).getProductId();
                Toast.makeText(mContext, mail+"", Toast.LENGTH_LONG).show();
                database.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Toast.makeText(mContext, "Inside Database", Toast.LENGTH_LONG).show();
                        if(dataSnapshot.child("userCartDetails").child(mail).child("map").hasChild(prodID)) {
                            Toast.makeText(mContext, "Inside IF", Toast.LENGTH_LONG).show();
                            database.child("userCartDetails").child(mail).child("map").child(prodID).removeValue();
                            Intent intent =new Intent(mContext,cartPage.class);
                            intent.putExtra("email", mail);
                            mContext.startActivity(intent);
                            ((Activity)mContext).finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class cartPageViewHolder extends RecyclerView.ViewHolder {
        TextView txtView;
        RelativeLayout layout;
        Button removeItem;
        TextView selectedQuant;
        TextView priceDisp;
        public cartPageViewHolder(@NonNull View itemView) {
            super(itemView);
            txtView = (TextView) itemView.findViewById(R.id.cartProd);
            layout = (RelativeLayout) itemView.findViewById(R.id.linearLayout);
            removeItem = (Button) itemView.findViewById(R.id.deleteFromCart);
            database = FirebaseDatabase.getInstance().getReference();
            selectedQuant = (TextView) itemView.findViewById(R.id.quantDisp);
            priceDisp = (TextView) itemView.findViewById(R.id.Price);

        }

    }
}
