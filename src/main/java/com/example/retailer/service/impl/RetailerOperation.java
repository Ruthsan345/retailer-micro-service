package com.example.retailer.service.impl;

import com.example.retailer.api.Retailers;
import com.example.retailer.exception.UserDefinedException;
import com.example.retailer.kafka.KafkaPublishService;
import com.example.retailer.model.*;

import com.example.retailer.repository.RetailerRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;


@Service
public class RetailerOperation implements Retailers {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    RetailerRepository retailerRepository;

    @Autowired
    RetailerInventoryOperation retailerInventoryOperation;

    @Autowired
    KafkaPublishService kafkaPublishService;

    public static ArrayList<Retailer> retailersList = new ArrayList<>();

    @Override
    public Optional<Retailer> displayRetailer(int retailerId) {
        return retailerRepository.findById(retailerId);
    }


    @Override
    public String addRetailer(Retailer retailer) {
        retailerRepository.save(retailer);
        return "Successfully added the retailer";
    }

    @Override
    public String deleteRetailer(int retailerId) {
         retailerRepository.deleteById(retailerId);
        return("Delete Successfull");

    }

    @Override
    public ArrayList<Retailer> displayAllRetailer() {
        return (ArrayList<Retailer>)retailerRepository.findAll();
    }

    @Override
    public String allocateProductToRetailer(int retailerId, int wholesalerId, int proid, int quantity,int payingAmount, int price) {

        boolean productNotFound = true;
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);

//            if (wholesalerList.isEmpty()){return("\n Sorry !!No wholesalers to allocate\n\n");}
        if (quantity<0){return ("\n Please enter a valid number for quantity.\n\n");}

        String url = UriComponentsBuilder.fromHttpUrl("http://localhost:8001/wholesaler/inventory/api/getAllProductsByWholesalerId?wholesalerId="+wholesalerId)
                .toUriString();

        ArrayList<WholesalerInventory> listOfWholesalerInventory = restTemplate.exchange(url, HttpMethod.GET, entity,new ParameterizedTypeReference<ArrayList<WholesalerInventory>>(){}).getBody();

        for(WholesalerInventory wholesalerInventory: listOfWholesalerInventory) {
            if(wholesalerInventory.getProduct_id() ==proid) {
                productNotFound= false;
                if (wholesalerInventory.getStock() < quantity) {
                    return ("\n Stocks are low. kindly try a small number\n\n");
                }

                url = UriComponentsBuilder.fromHttpUrl("http://localhost:8000/product/api/getProductById/"+proid)
                        .toUriString();

                Product pro = restTemplate.exchange(url, HttpMethod.GET, entity, Product.class).getBody();

                int billAmount =  wholesalerInventory.getStock()*quantity;
                float discountPercentage=0;
                float totalAfterOffer= billAmount - billAmount*discountPercentage;
                float gstAmount = totalAfterOffer*(pro.gstPercentage/100f);
                float grandBillAmount= totalAfterOffer+gstAmount;


                RetailerInventory retailerInventory = new RetailerInventory(1, wholesalerId, retailerId, proid, quantity,price);

                retailerInventoryOperation.addProductToRetailer(retailerInventory);


                int reducedQuantity = wholesalerInventory.getStock()-quantity;

                url = UriComponentsBuilder.fromHttpUrl("http://localhost:8001/wholesaler/inventory/api/updateProductToWholesaler?inventoryId="+wholesalerInventory.getId()+"&quantity="+reducedQuantity)
                        .toUriString();

                restTemplate.exchange(url, HttpMethod.PUT, entity, String.class).getBody();


                System.out.print("\ngrand bill amount :: "+grandBillAmount);
                Product pros = new Product(1,pro.getProId(), pro.getProName(), price,pro.getGstPercentage());


                Bill bill = new Bill(false,retailerId, "A Retailer", quantity, billAmount,Math.round(discountPercentage), gstAmount, grandBillAmount,pros);

                String billid = RandomStringUtils.randomAlphanumeric(10);

                long millis=System.currentTimeMillis();
                java.sql.Date date=new java.sql.Date(millis);

                Float due_amount = grandBillAmount-payingAmount;
                if (due_amount<=0){ due_amount=0f; }

                Finance finance = new Finance(1,billid,"RETAILER",retailerId, wholesalerId,proid, grandBillAmount, due_amount, date);
                kafkaPublishService.sendBillingInformation(bill);
                kafkaPublishService.sendFinanceInformation(finance);

                return ("Succesfully Purchased. \n Your bill id for reference is : "+billid);

            }

        }
        if(productNotFound){ return ("\n Sorry !!Products not found!\n\n"); }

        System.out.println("\n <--------------------------------------------------> \n");
        return null;
    }

    @Override
    public String payPendingDue(String billId, int amount) {
        kafkaPublishService.sendDueInformation(billId,amount);
        return ("Kafka request published ");
    }

}


