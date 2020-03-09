package com.bos.order.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "temp_detail_transaction")
public class TemporaryDetailTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_temp_detail_transaction;
    private int id_temp_transaction;
    private int id_product;
    private int quantity;
}
