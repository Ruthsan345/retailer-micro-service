package com.example.retailer.controller;

import com.example.retailer.api.Retailers;
//import com.example.retailer.helper.CsvReader;
import com.example.retailer.model.Retailer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RequestMapping("retailer/api/")
@RestController()
public class RetailerController {
    @Autowired
    Retailers retailerOp;

    @GetMapping("/displayAllRetailer")
    public ArrayList<Retailer> displayAllRetailer() {
        return retailerOp.displayAllRetailer();
    }
    @GetMapping("/displayRetailer")
    public Optional<Retailer> displayRetailer(@RequestParam int retailerId) {
        Optional<Retailer> pro =retailerOp.displayRetailer(retailerId);
        return pro;
    }

    @PostMapping("/addRetailer")
    public String addRetailer(@RequestBody Retailer retailer) {
        return retailerOp.addRetailer(retailer);
    }

    @DeleteMapping("/deleteRetailer")
    public String deleteRetailer(@RequestParam int retailerId) {
        return retailerOp.deleteRetailer(retailerId);
    }


    @PostMapping("/allocateProductToRetailer")
    public String allocateProductToRetailer(@RequestParam int retailerId, @RequestParam int wholesalerId, @RequestParam int proid, @RequestParam int quantity,@RequestParam int payingAmount, @RequestParam int price) {
        return retailerOp.allocateProductToRetailer(retailerId, wholesalerId, proid, quantity, payingAmount, price);
    }

    @PostMapping("/payPendingDue")
    public String payPendingDue(@RequestParam String billId, @RequestParam int amount) {
        return retailerOp.payPendingDue(billId, amount);
    }

}