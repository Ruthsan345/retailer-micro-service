package com.example.retailer.service.impl;

import com.example.retailer.api.Retailers;
import com.example.retailer.exception.UserDefinedException;
import com.example.retailer.model.Product;
import com.example.retailer.model.Retailer;

import com.example.retailer.model.Wholesaler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NavigableMap;
import java.util.TreeMap;


@Service
public class RetailerOperation implements Retailers {
    @Autowired
    RestTemplate restTemplate;
    public static ArrayList<Retailer> retailersList = new ArrayList<>();


    @Override
    public Retailer displayRetailer(int retailerId) {
        try{
            if (retailersList.isEmpty()){throw new UserDefinedException("\n Sorry !!No retailer to Display\n\n");}
            for(Retailer n : retailersList){
                if(n.getRetail_id() ==retailerId){
                    return n;
                }
                System.out.println("\n <--------------------------------------------------> \n");
            }
        }catch(UserDefinedException ud){
            System.out.println(ud.getMessage());
        }
        return null;
    }

    @Override
    public String addRetailer(Retailer retailer) {
        retailersList.add(retailer);
        return "Successfully added the retailer";
    }

    @Override
    public String deleteRetailer(int retailerId) {
        if (retailersList.isEmpty()){return ("\n Sorry !!No Retailer to delete\n\n");}
        int pos = 0;
        int trigger=-1;
        for(Retailer n : retailersList){
            if(n.getRetail_id() ==retailerId){
                trigger=pos;
            }pos++;
        }
        if(trigger>=0){
            retailersList.remove(trigger);
            return("delete Successfull");
        }else return("\n Sorry !!retailer id not found enter an another id\n\n");
    }


    @Override
    public String allocateProductToRetailer(int retId, int whoid, int proid, int quantity, int price) {
        boolean retailerNotFound = true;
        boolean productNotFound = true;
        boolean wholesalerNotFound = true;

        ArrayList<Product> productsList = new ArrayList<Product>();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        if (retailersList.isEmpty()){return("\n Sorry !!No wholesalers to Display\n\n");}
        if (quantity<0){return("\n Please enter a valid number for quantity.\n\n");}
        System.out.println("-1-------->");
        for(Retailer n : retailersList){
            if(n.getRetail_id() == retId){
                System.out.println("-2-------->");

                retailerNotFound=false;
                String url = UriComponentsBuilder.fromHttpUrl("http://localhost:8001/product/api/displayWholesaler")
                        .queryParam("wholesalerId",whoid).toUriString();


                Wholesaler whole = restTemplate.exchange(url, HttpMethod.GET, entity, Wholesaler.class).getBody();

                System.out.println("-3-------->");

                    if(whole.getWholesale_id()==whoid){
                        wholesalerNotFound =false;
                        for(Product pro:whole.getWholesale_products()) {
                            if (pro.getProId() == proid) {
                                productNotFound=false;
                                if (pro.getStock()< quantity) {
                                    return("\n Stocks are low. kindly try a small number\n\n");
                                }
//                                NavigableMap<Integer,Integer> discount=new TreeMap<Integer, Integer>();

                                quantity = pro.getStock() - quantity;
                                url = UriComponentsBuilder.fromHttpUrl("http://localhost:8001/product/api/updateWholesalerProduct")
                                        .queryParam("wholesalerId",whoid)
                                        .queryParam("productId",proid)
                                        .queryParam("quantity",quantity)
                                        .toUriString();
                                restTemplate.exchange(url, HttpMethod.PUT, entity, String.class).getBody();



                                int billAmount =  pro.getPrice()*quantity;
//                                float discountPercentage = pro.discount.get(pro.discount.floorKey(quantity))/100f;
                                float discountPercentage =0;
                                float totalAfterOffer= billAmount - billAmount*discountPercentage;
                                float gstAmount = totalAfterOffer*(18/100f);
                                float grandBillAmount= totalAfterOffer+gstAmount;


                                System.out.println("\n\n\n\t\t\t<------Bill Amount----->");
                                System.out.println("\n\t\tProduct :: "+ pro.getProName() + "\n\t\tQuantity :: "+quantity);
//                                System.out.print("\n\t\tBill amount :: "+billAmount+"\n\t\tOffer percentage :: "+pro.discount.get(pro.discount.floorKey(quantity))+"%");
                                System.out.print("\n\t\tGST Percentage :: 18%\n\t\tGST Amount :: "+gstAmount);

                                System.out.print("\n\t\tGrand bill amount :: "+grandBillAmount);






                                Product pros = new Product(pro.getProId(), pro.getProName(), quantity, price,pro.getGstPercentage());

                                productsList =n.getRetail_products().getOrDefault(whole.getWholesale_id(),productsList );
                                productsList.add(pros);

                                n.setRetail_products(whole.getWholesale_id(),productsList);


                                return("Sucessfully Purchased");
                            }
                        }



                }

            }
        }
        if(wholesalerNotFound){ return("\n Sorry !!Wholesaler not found!\n\n"); }
        if(productNotFound){ return("\n Sorry !!Products not found!\n\n"); }
        if(retailerNotFound){ return("\n Sorry !!Retailers not found!\n\n"); }

        return("Error due to some reason. try again later");

    }
}


