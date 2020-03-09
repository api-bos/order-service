package com.bos.order.controller;

import bca.bit.proj.library.base.ResultEntity;
import com.bos.order.model.Order;
import com.bos.order.model.RequestData;
import com.bos.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/bos", produces = "application/json")
@CrossOrigin(origins = {"*"})
public class OrderController {
    @Autowired
    OrderService g_orderService;

    @PostMapping(value = "/form", consumes = "application/json")
    public ResultEntity sendForm(@RequestBody RequestData p_requestData){
        return g_orderService.sendForm(p_requestData);
    }

    @GetMapping(value = "/form/{id_temp_transaction}")
    public ResultEntity openForm(@PathVariable("id_temp_transaction") int id_temp_transaction){
        return g_orderService.getTempTransaction(id_temp_transaction);
    }

    @PostMapping(value = "/order", consumes = "application/json")
    public ResultEntity submitForm(@RequestBody Order p_order){
        return g_orderService.submitOrder(p_order);
    }
}
