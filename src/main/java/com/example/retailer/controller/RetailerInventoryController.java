package com.example.retailer.controller;

import com.example.retailer.api.RetailersInventories;
import com.example.retailer.model.RetailerInventory;
import com.example.retailer.repository.RetailerInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RequestMapping("retailer/inventory/api/")
@RestController()
public class RetailerInventoryController {
    @Autowired
    RetailersInventories retailersInventories;

    @PostMapping("/addProductToRetailer")
    public String addProductToRetailer(@RequestBody RetailerInventory retailerInventory) {

        return retailersInventories.addProductToRetailer(retailerInventory);

    }

    @PutMapping("/updateProductToRetailer")
    public ResponseEntity<String> updateProductToRetailer(@RequestParam int inventoryId, @RequestParam int quantity , @RequestParam int price) {
        return retailersInventories.updateProductToRetailer(inventoryId,quantity,price);
    }


    @GetMapping("/getAllProductsByRetailerId")
    public ArrayList<RetailerInventory> getAllProductsByRetailerId(@RequestParam int retailerId) {
        ArrayList<RetailerInventory> retailerProductList = retailersInventories.getAllProductsByRetailerId(retailerId);
        return retailerProductList ;
    }


    @DeleteMapping("/deleteProductUsingInventoryId")
    public String deleteRetailer(@RequestParam int inventoryId) {
        return retailersInventories.deleteProductUsingInventoryId(inventoryId);
    }
}
