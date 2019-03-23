
package com.example.shoppingcartupdated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductCollection {

    @SerializedName("ProductId")
    @Expose
    private String productId;
    @SerializedName("Category")
    @Expose
    private String category;
    @SerializedName("MainCategory")
    @Expose
    private String mainCategory;
    @SerializedName("TaxTarifCode")
    @Expose
    private String taxTarifCode;
    @SerializedName("SupplierName")
    @Expose
    private String supplierName;
    @SerializedName("WeightMeasure")
    @Expose
    private Double weightMeasure;
    @SerializedName("WeightUnit")
    @Expose
    private String weightUnit;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("DateOfSale")
    @Expose
    private String dateOfSale;
    @SerializedName("ProductPicUrl")
    @Expose
    private String productPicUrl;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Quantity")
    @Expose
    private Long quantity;
    @SerializedName("UoM")
    @Expose
    private String uoM;
    @SerializedName("CurrencyCode")
    @Expose
    private String currencyCode;
    @SerializedName("Price")
    @Expose
    private Double price;
    @SerializedName("Width")
    @Expose
    private Double width;
    @SerializedName("Depth")
    @Expose
    private Double depth;
    @SerializedName("Height")
    @Expose
    private Double height;
    @SerializedName("DimUnit")
    @Expose
    private String dimUnit;
    public ProductCollection() {

    }
    public ProductCollection(String productId, String category, String mainCategory, String taxTarifCode, String supplierName, Double weightMeasure, String weightUnit, String description, String name, String dateOfSale, String productPicUrl, String status, Long quantity, String uoM, String currencyCode, Double price, Double width, Double depth, Double height, String dimUnit) {
        this.productId = productId;
        this.category = category;
        this.mainCategory = mainCategory;
        this.taxTarifCode = taxTarifCode;
        this.supplierName = supplierName;
        this.weightMeasure = weightMeasure;
        this.weightUnit = weightUnit;
        this.description = description;
        this.name = name;
        this.dateOfSale = dateOfSale;
        this.productPicUrl = productPicUrl;
        this.status = status;
        this.quantity = quantity;
        this.uoM = uoM;
        this.currencyCode = currencyCode;
        this.price = price;
        this.width = width;
        this.depth = depth;
        this.height = height;
        this.dimUnit = dimUnit;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMainCategory() {
        return mainCategory;
    }

    public void setMainCategory(String mainCategory) {
        this.mainCategory = mainCategory;
    }

    public String getTaxTarifCode() {
        return taxTarifCode;
    }

    public void setTaxTarifCode(String taxTarifCode) {
        this.taxTarifCode = taxTarifCode;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Double getWeightMeasure() {
        return weightMeasure;
    }

    public void setWeightMeasure(Double weightMeasure) {
        this.weightMeasure = weightMeasure;
    }

    public String getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(String weightUnit) {
        this.weightUnit = weightUnit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfSale() {
        return dateOfSale;
    }

    public void setDateOfSale(String dateOfSale) {
        this.dateOfSale = dateOfSale;
    }

    public String getProductPicUrl() {
        return "http://msitmp.herokuapp.com"+ productPicUrl;
    }

    public void setProductPicUrl(String productPicUrl) {
        this.productPicUrl = productPicUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getUoM() {
        return uoM;
    }

    public void setUoM(String uoM) {
        this.uoM = uoM;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getDepth() {
        return depth;
    }

    public void setDepth(Double depth) {
        this.depth = depth;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public String getDimUnit() {
        return dimUnit;
    }

    public void setDimUnit(String dimUnit) {
        this.dimUnit = dimUnit;
    }

}
