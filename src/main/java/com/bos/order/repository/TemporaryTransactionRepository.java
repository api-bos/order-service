package com.bos.order.repository;

import com.bos.order.model.TemporaryTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.sql.Timestamp;

public interface TemporaryTransactionRepository extends JpaRepository<TemporaryTransaction, Integer> {
    @Query(value = "SELECT id_temp_transaction FROM temp_transaction WHERE order_time = :order_time", nativeQuery = true)
    int getTempTransactionIdByOrderTime(@Param("order_time") Timestamp order_time);

    @Transactional
    @Modifying
    @Query(value = "UPDATE temp_transaction SET status=1 WHERE id_temp_transaction = :id_temp_transaction", nativeQuery = true)
    void updateStatusByTempTransactionId(@Param("id_temp_transaction") int p_tempTransactionId);

    @Query(value = "SELECT status FROM temp_transaction WHERE id_temp_transaction = :id_temp_transaction", nativeQuery = true)
    int getStatusByTempTransactionId(@Param("id_temp_transaction") int p_tempTransactionId);
}
