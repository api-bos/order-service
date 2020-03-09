package com.bos.order.repository;

import com.bos.order.model.TransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionDetailRepository extends JpaRepository<TransactionDetail, Integer> {
}
