package com.example.retailer.controller;

import com.example.retailer.api.Retailers;
import com.example.retailer.helper.CsvReader;
import com.example.retailer.model.Retailer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.*;
import com.example.retailer.service.impl.RetailerOperation;

import java.io.IOException;

@RequestMapping("product/api/")
@RestController()
public class productController {
    @Autowired
    Retailers retailerOp;
    @Autowired
    CsvReader csvRead;

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() throws IOException {
        csvRead.readRetailerCsv(RetailerOperation.retailersList);

        System.out.print("<--------------Retailer read from CSV-------------->");
    }

    @GetMapping("/displayRetailer")
    public Retailer displayRetailer(@RequestParam int retailerId) {
        return retailerOp.displayRetailer(retailerId);
    }


    @PostMapping("/addRetailer")
    public String addRetailer(@RequestBody Retailer retailer) {
        return retailerOp.addRetailer(retailer);
    }

    @DeleteMapping("/deleteRetailer")
    public String deleteRetailer(@RequestParam int retailerId) {
        return retailerOp.deleteRetailer(retailerId);
    }
//
//    @PutMapping("/updateWholesalerProduct")
//    public String updateWholesaler(@RequestParam int wholersalerId ,@RequestParam int productId, @RequestParam int quantity) {
//        return wholesalerOp.updateWholesalerProduct(wholersalerId, productId, quantity);
//    }

    @PostMapping("/allocateProductToRetailer")
    public String allocateProductToRetailer(@RequestParam int retailerId, @RequestParam int wholesalerId, @RequestParam int proid, @RequestParam int quantity, @RequestParam int price) {
        return retailerOp.allocateProductToRetailer(retailerId, wholesalerId, proid, quantity, price);
    }

}