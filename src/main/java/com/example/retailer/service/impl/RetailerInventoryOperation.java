package com.example.retailer.service.impl;


import com.example.retailer.api.RetailersInventories;
import com.example.retailer.model.Retailer;
import com.example.retailer.model.RetailerInventory;
import com.example.retailer.model.Wholesaler;
import com.example.retailer.repository.RetailerInventoryRepository;
import com.example.retailer.repository.RetailerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class RetailerInventoryOperation implements RetailersInventories {
    @Autowired
    RetailerInventoryRepository retailerInventoryRepository;

    @Override
    public String addProductToRetailer(RetailerInventory retailerInventory) {
        retailerInventoryRepository.save(retailerInventory);
        return "Succesfully added";
    }


    @Override
    public ResponseEntity<String> updateProductToRetailer(int inventoryId, int quantity, int price) {
        RetailerInventory retailerInventory = retailerInventoryRepository.findById(inventoryId);
        retailerInventory.setStock(quantity);
        retailerInventory.setPrice(price);
        retailerInventoryRepository.save(retailerInventory);
        return new ResponseEntity<>( "Updated Product to retailer", HttpStatus.OK);

    }



    @Override
    public ArrayList<RetailerInventory> getAllProductsByRetailerId(int retailerId) {

        return retailerInventoryRepository.findByRetailerid(retailerId);
    }

    @Override
    public String deleteProductUsingInventoryId(int inventoryId) {
        retailerInventoryRepository.deleteById(inventoryId);
        return "Deleted product from retailers";
    }



}
