
package com.example.shoppingcartupdated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Products {

    @SerializedName("ProductCollection")
    @Expose
    private List<ProductCollection> productCollection = null;

    public List<ProductCollection> getProductCollection() {
        return productCollection;
    }

    public void setProductCollection(List<ProductCollection> productCollection) {
        this.productCollection = productCollection;
    }

    public ProductCollection[] getproductsArr() {

        productCollection = getProductCollection();
        ProductCollection[] products = new ProductCollection[productCollection.size()];
        for (int i = 0; i < productCollection.size(); i++) {
            products[i] = productCollection.get(i);
        }
        return products;
    }

}
