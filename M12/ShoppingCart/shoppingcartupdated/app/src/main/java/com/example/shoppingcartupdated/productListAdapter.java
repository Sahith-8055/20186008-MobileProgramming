package com.example.shoppingcartupdated;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class productListAdapter extends RecyclerView.Adapter<productListAdapter.productListViewHolder> {
    private Context mContext;
    private List<ProductCollection> productList;
    String email;
    RecyclerView cartList;
    public productListAdapter(List<ProductCollection> list, Context context, String userEmail) {
        this.productList = list;
        System.out.println(list);
        this.mContext = context;
        email = userEmail;
    }

    @NonNull
    @Override
    public productListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.product_list_item, viewGroup, false);
        return new productListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull productListViewHolder productListViewHolder, final int i) {
        Glide.with(mContext).asBitmap().load(productList.get(i).getProductPicUrl()).into(productListViewHolder.image);
        System.out.println(productList.get(i).getProductPicUrl());
        String data = productList.get(i).getName();
        productListViewHolder.txtview.setText(data);
        productListViewHolder.price.setText(productList.get(i).getCurrencyCode() +" "+ productList.get(i).getPrice());
        productListViewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProductDescription.class);
                intent.putExtra("productCategory", productList.get(i).getCategory());
                intent.putExtra("productID", productList.get(i).getProductId());
                intent.putExtra("imageURL", productList.get(i).getProductPicUrl());
                intent.putExtra("email", email);
                intent.putExtra("quant", productList.get(i).getQuantity()+"");
                intent.putExtra("price", productList.get(i).getPrice()+"");
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class productListViewHolder extends RecyclerView.ViewHolder {
        TextView txtview;
        LinearLayout layout;
        ImageView image;
        TextView price;
        public productListViewHolder(@NonNull View itemView) {
            super(itemView);
            txtview = (TextView) itemView.findViewById(R.id.prod);
            layout = itemView.findViewById(R.id.layout);
            image = (ImageView) itemView.findViewById(R.id.image);
            price = (TextView) itemView.findViewById(R.id.price);
        }
    }
}
