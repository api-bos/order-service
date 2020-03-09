package com.bos.order.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseData {
    private Optional<TemporaryTransaction> temp_transaction;
    private ArrayList<TemporaryDetailTransaction> temp_detail_transaction;
}
