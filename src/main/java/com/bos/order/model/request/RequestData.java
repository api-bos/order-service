package com.bos.order.model.request;

import com.bos.order.model.ProductOrdered;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestData {
    private int id_seller;
    private int origin_city;
    private ArrayList<ProductOrdered> product;
}
