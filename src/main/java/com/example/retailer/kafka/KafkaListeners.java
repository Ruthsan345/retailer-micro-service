package com.example.retailer.kafka;


import com.example.retailer.model.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

//@Service
//
//public class KafkaListeners {
//    @Autowired
//    ObjectMapper objectMapper;
//
//    @KafkaListener(topics = "SEND.PRODUCT.INFO", groupId = "warehouse")
//    public void getProductDetail(ConsumerRecord<?,String> consumerRecord){
//        Product product = null;
//        try{
//            product = objectMapper.readValue(consumerRecord.value(), new TypeReference<Product>() {
//            });
//        }catch(Exception e){
//            System.out.print(e);
//        }
//        System.out.print("\n\n--------------------->"+product.getProName()+" "+product.getProId()+" "+product.getProName());
//    }
//}
