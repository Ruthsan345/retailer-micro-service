package com.example.retailer.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;

@Entity
public class Retailer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReference_id() {
        return reference_id;
    }

    public void setReference_id(String reference_id) {
        this.reference_id = reference_id;
    }

    public String getRetailer_name() {
        return retailer_name;
    }

    public void setRetailer_name(String retailer_name) {
        this.retailer_name = retailer_name;
    }

    public String getContact_mail() {
        return contact_mail;
    }

    public void setContact_mail(String contact_mail) {
        this.contact_mail = contact_mail;
    }

    public String getContact_mobile_no() {
        return contact_mobile_no;
    }

    public void setContact_mobile_no(String contact_mobile_no) {
        this.contact_mobile_no = contact_mobile_no;
    }

    @Column(name = "reference_id")
    public String reference_id;

    @Column(name = "name")
    public String retailer_name;

    @Column(name = "contact_mail")
    public String contact_mail;

    @Column(name = "contact_mobile_no")
    public String contact_mobile_no;



}
