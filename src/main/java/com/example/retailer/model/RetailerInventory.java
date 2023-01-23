package com.example.retailer.model;

import javax.persistence.*;


@Entity
@Table(name = "retailer_inventory")
public class RetailerInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;

    @Column(name = "wholesaler_id")
    public int wholesalerid;

    @Column(name = "retailer_id")
    public int retailerid;
    public RetailerInventory(){}

    public RetailerInventory(int id, int wholesalerid, int retailerid, int product_id, int stock, int price) {
        this.id = id;
        this.wholesalerid = wholesalerid;
        this.retailerid = retailerid;
        this.product_id = product_id;
        this.stock = stock;
        this.price = price;
    }

    @Column(name = "product_id")
    public int product_id;
    @Column(name = "stock")
    public int stock;
    @Column(name = "price")
    public int price;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWholesalerid() {
        return wholesalerid;
    }

    public void setWholesalerid(int wholesalerid) {
        this.wholesalerid = wholesalerid;
    }

    public int getRetailerid() {
        return retailerid;
    }

    public void setRetailerid(int retailerid) {
        this.retailerid = retailerid;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }






}
