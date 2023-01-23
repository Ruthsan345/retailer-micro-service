package com.example.retailer.repository;

import com.example.retailer.model.Retailer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RetailerRepository extends JpaRepository<Retailer, Integer> {
    Optional<Retailer> findById(int Id);

}
