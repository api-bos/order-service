package com.bos.order.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_transaction;
    private int id_buyer;
    private Timestamp order_time;
    private double shipping_fee;
    private String shipping_agent;
    private double total_payment;
    private int status;
    private int id_seller;
    private int id_kelurahan;
    private String address_detail;
    private String va_number;
}
