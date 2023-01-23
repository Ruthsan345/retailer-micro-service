package com.example.retailer.api;

import com.example.retailer.model.RetailerInventory;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

public interface RetailersInventories {
    String addProductToRetailer(RetailerInventory retailerInventory);
    ResponseEntity<String> updateProductToRetailer(int inventoryId, int quantity , int price);

    ArrayList<RetailerInventory> getAllProductsByRetailerId(int retailerId);

    String deleteProductUsingInventoryId(int inventoryId);
}
