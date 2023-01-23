package com.example.retailer.repository;

import com.example.retailer.model.RetailerInventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface RetailerInventoryRepository  extends JpaRepository<RetailerInventory, Integer> {
    RetailerInventory findById(int Id);
    ArrayList<RetailerInventory> findByRetailerid(int wholesalerId);

}
