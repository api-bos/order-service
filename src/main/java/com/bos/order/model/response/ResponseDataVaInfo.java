package com.bos.order.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDataVaInfo {
    private int id_transaction;
    private String va_number;
    private Timestamp order_time;
}
