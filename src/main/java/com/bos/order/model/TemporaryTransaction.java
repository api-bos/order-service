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
@Table(name = "temp_transaction")
public class TemporaryTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_temp_transaction;
    private int id_seller;
    private int origin_city;
    private Timestamp order_time;
    private int status;
}
