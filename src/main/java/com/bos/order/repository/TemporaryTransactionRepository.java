package com.bos.order.repository;

import com.bos.order.model.TemporaryTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;

public interface TemporaryTransactionRepository extends JpaRepository<TemporaryTransaction, Integer> {
    @Query(value = "SELECT id_temp_transaction FROM temp_transaction WHERE order_time = :order_time", nativeQuery = true)
    int getTempTransactionIdByOrderTime(@Param("order_time") Timestamp order_time);
}
