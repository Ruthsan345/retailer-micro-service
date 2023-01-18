package com.example.retailer.api;

import com.example.retailer.model.Retailer;


import java.util.ArrayList;

public interface Retailers {
    Retailer displayRetailer(int retailerId);
    String addRetailer(Retailer retailer);
    String deleteRetailer(int retailerId);
    String allocateProductToRetailer(int retailerId,int wholesalerId, int proid, int quantity, int price);

}
