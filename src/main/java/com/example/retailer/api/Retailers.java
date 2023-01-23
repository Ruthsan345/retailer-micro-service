package com.example.retailer.api;

import com.example.retailer.model.Retailer;


import java.util.ArrayList;
import java.util.Optional;

public interface Retailers {
    Optional<Retailer> displayRetailer(int retailerId);
    String addRetailer(Retailer retailer);
    String deleteRetailer(int retailerId);
    ArrayList<Retailer> displayAllRetailer();
    String allocateProductToRetailer(int retailerId,int wholesalerId, int proid, int quantity,int payingAmount, int price);

    String payPendingDue(String billId, int amount);
}
