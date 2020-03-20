package com.bos.order.service;

import bca.bit.proj.library.base.ResultEntity;
import bca.bit.proj.library.enums.ErrorCode;
import com.bos.order.model.*;
import com.bos.order.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private TemporaryTransactionRepository g_temporaryTransactionRepository;
    @Autowired
    private TemporaryDetailTransactionRepository g_temporaryDetailTransactionRepository;
    @Autowired
    private ProductRepository g_productRepository;
    @Autowired
    private BuyerRepository g_buyerRepository;
    @Autowired
    private TransactionRepository g_transactionRepository;
    @Autowired
    private TransactionDetailRepository g_transactionDetailRepository;

    public ResultEntity sendForm(RequestData p_requestData){
        ResultEntity l_output = null;
        int l_stock;
        int l_quantity;
        int l_productId;
        ArrayList<String> l_arrayErrorMessage = new ArrayList<>();
        boolean l_transactionOccured = false;

        try{
            for (int i = 0; i<p_requestData.getProduct().size(); i++){
                l_productId = p_requestData.getProduct().get(i).getId_product();
                l_stock = g_productRepository.getStockByProductId(l_productId);
                l_quantity = p_requestData.getProduct().get(i).getQuantity();

                if (l_stock < l_quantity){
                    String tmp_productName = g_productRepository.getProductNameByProductId(l_productId);
                    String tmp_errorMessage = "Stok produk " + tmp_productName + " tidak mencukupi";

                    l_arrayErrorMessage.add(tmp_errorMessage);

                    l_transactionOccured = true;
                }
            }

            if (!l_transactionOccured){
                //Save data to temp_transaction table
                TemporaryTransaction tmp_tempTransaction = new TemporaryTransaction();
                Timestamp l_timestamp = new Timestamp(System.currentTimeMillis());
                tmp_tempTransaction.setOrder_time(l_timestamp);
                tmp_tempTransaction.setId_seller(p_requestData.getId_seller());
                tmp_tempTransaction.setOrigin_city(p_requestData.getOrigin_city());
                g_temporaryTransactionRepository.save(tmp_tempTransaction);

                //Get id_temp_transaction from temp_transaction table
                int tmp_tempTransactionId = g_temporaryTransactionRepository.getTempTransactionIdByOrderTime(l_timestamp);

                //Save data as much as product ordered to temp_detail_transaction table
                for (int i = 0; i<p_requestData.getProduct().size(); i++){
                    l_productId = p_requestData.getProduct().get(i).getId_product();

                    //Decrease stock
                    l_quantity = p_requestData.getProduct().get(i).getQuantity();
                    l_stock = g_productRepository.getStockByProductId(l_productId);
                    int tmp_currentStock = l_stock - l_quantity;
                    g_productRepository.updateStockByProductId(tmp_currentStock, l_productId);

                    //Save data to transaction_detail table
                    TemporaryDetailTransaction tmp_tempDetailTransaction = new TemporaryDetailTransaction();
                    tmp_tempDetailTransaction.setId_temp_transaction(tmp_tempTransactionId);
                    tmp_tempDetailTransaction.setId_product(l_productId);
                    tmp_tempDetailTransaction.setQuantity(l_quantity);
                    g_temporaryDetailTransactionRepository.save(tmp_tempDetailTransaction);

                    l_output = new ResultEntity(tmp_tempTransactionId, ErrorCode.BIT_000);
                }

            }else {
                l_output = new ResultEntity(l_arrayErrorMessage, ErrorCode.BIT_999);
            }

        }catch (Exception e){
            e.printStackTrace();
            l_output = new ResultEntity(e.toString(), ErrorCode.BIT_999);
        }

        return l_output;
    }

    public ResultEntity getTempTransaction(int p_tempTransactionId){
        ResultEntity l_output;

        try {
            Optional<TemporaryTransaction> tmp_tempTransaction = g_temporaryTransactionRepository.findById(p_tempTransactionId);
            ArrayList<TemporaryDetailTransaction> tmp_arrayTempDetailTransaction = g_temporaryDetailTransactionRepository.getAllDetailTransactionByTempTransactionId(p_tempTransactionId);

            ResponseData tmp_responseData = new ResponseData();
            tmp_responseData.setTemp_transaction(tmp_tempTransaction);
            tmp_responseData.setTemp_detail_transaction(tmp_arrayTempDetailTransaction);

            l_output = new ResultEntity(tmp_responseData, ErrorCode.BIT_000);

        }catch (Exception e){
            e.printStackTrace();
            l_output = new ResultEntity(e.toString(), ErrorCode.BIT_999);
        }

        return l_output;
    }

    public ResultEntity submitOrder(Order p_order){
        ResultEntity l_output = null;
        int l_stock;
        int l_quantity;
        int l_productId;
        ArrayList<String> l_arrayErrorMessage = new ArrayList<>();
        boolean l_transactionOccured = false;

        try {
            for (int i = 0; i<p_order.getProduct().size(); i++){
                l_productId = p_order.getProduct().get(i).getId_product();
                l_stock = g_productRepository.getStockByProductId(l_productId);
                l_quantity = p_order.getProduct().get(i).getQuantity();

                if (l_stock < l_quantity){
                    String tmp_productName = g_productRepository.getProductNameByProductId(l_productId);
                    String tmp_errorMessage = "Stok produk " + tmp_productName + " tidak mencukupi";

                    l_arrayErrorMessage.add(tmp_errorMessage);

                    l_transactionOccured = true;
                }
            }

            if (!l_transactionOccured){
                //Save or update data to buyer table
                if (!g_buyerRepository.existByPhone(p_order.getPhone()).equals(Optional.empty())){
                    g_buyerRepository.updateNameByPhone(p_order.getBuyer_name(), p_order.getPhone());

                }else {
                    Buyer tmp_buyer = new Buyer();
                    tmp_buyer.setBuyer_name(p_order.getBuyer_name());
                    tmp_buyer.setPhone(p_order.getPhone());
                    g_buyerRepository.save(tmp_buyer);
                }

                //Save data to transaction table
                Timestamp l_timestamp = new Timestamp(System.currentTimeMillis());
                Transaction tmp_transaction = new Transaction();
                tmp_transaction.setId_buyer(g_buyerRepository.getBuyerIdByPhone(p_order.getPhone()));
                tmp_transaction.setOrder_time(l_timestamp);
                tmp_transaction.setShipping_fee(p_order.getShipping_fee());
                tmp_transaction.setShipping_agent(p_order.getShipping_agent());
                tmp_transaction.setTotal_payment(p_order.getTotal_payment());
                tmp_transaction.setStatus(0);
                tmp_transaction.setId_seller(p_order.getId_seller());
                tmp_transaction.setId_kelurahan(p_order.getId_kelurahan());
                tmp_transaction.setAddress_detail(p_order.getAddress_detail());
                g_transactionRepository.save(tmp_transaction);

                //Get id_transaction from transaction table
                int tmp_transactionId = g_transactionRepository.getTransactionIdByOrderTime(l_timestamp);

                //Save data as much as product oredered to transaction_detail table
                for (int i = 0; i<p_order.getProduct().size(); i++){
                    l_productId = p_order.getProduct().get(i).getId_product();

                    //Decrease stock
                    l_quantity = p_order.getProduct().get(i).getQuantity();
                    l_stock = g_productRepository.getStockByProductId(l_productId);
                    int tmp_currentStock = l_stock - l_quantity;
                    g_productRepository.updateStockByProductId(tmp_currentStock, l_productId);

                    //Save data to transaction_detail table
                    TransactionDetail tmp_transactionDetail = new TransactionDetail();
                    tmp_transactionDetail.setId_transaction(tmp_transactionId);
                    tmp_transactionDetail.setId_product(l_productId);
                    tmp_transactionDetail.setQuantity(l_quantity);
                    tmp_transactionDetail.setSell_price(p_order.getProduct().get(i).getSell_price());
                    g_transactionDetailRepository.save(tmp_transactionDetail);

                    l_output = new ResultEntity("Y", ErrorCode.BIT_000);
                }

            }else {
                l_output = new ResultEntity(l_arrayErrorMessage, ErrorCode.BIT_999);
            }

        }catch (Exception e){
            e.printStackTrace();
            l_output = new ResultEntity(e.toString(), ErrorCode.BIT_999);
        }

        return l_output;
    }
}
