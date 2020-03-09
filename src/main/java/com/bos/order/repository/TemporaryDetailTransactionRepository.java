package com.bos.order.repository;

import com.bos.order.model.TemporaryDetailTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface TemporaryDetailTransactionRepository extends JpaRepository<TemporaryDetailTransaction, Integer> {
    @Query(value = "SELECT * FROM temp_detail_transaction WHERE id_temp_transaction = :id_temp_transaction", nativeQuery = true)
    ArrayList<TemporaryDetailTransaction> getAllDetailTransactionByTempTransactionId(@Param("id_temp_transaction") int id_temp_transaction);
}
