package com.bos.order.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private String buyer_name;
    private String phone;
    private double shipping_fee;
    private String shipping_agent;
    private int id_kelurahan;
    private String address_detail;
    private double total_payment;
    private int id_seller;
    private ArrayList<ProductOrdered> product;
}
