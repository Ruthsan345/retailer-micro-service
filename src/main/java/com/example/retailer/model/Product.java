package com.example.retailer.model;



public class Product {
    public int getId() {
        return id;
    }
    public Product(){}


    public void setId(int id) {
        this.id = id;
    }

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getGstPercentage() {
        return gstPercentage;
    }

    public void setGstPercentage(int gstPercentage) {
        this.gstPercentage = gstPercentage;
    }

    public int id;
    public String proId;
    public String proName;
    public int price;
    public int gstPercentage;

    public Product(int id, String proId, String proName, int price, int gstPercentage) {
        this.id = id;
        this.proId = proId;
        this.proName = proName;
        this.price = price;
        this.gstPercentage = gstPercentage;
    }
}
