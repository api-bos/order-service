package com.bos.order.repository;

import com.bos.order.model.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface BuyerRepository extends JpaRepository<Buyer, Integer> {
    @Query(value = "SELECT * FROM buyer WHERE phone = :phone", nativeQuery = true)
    Optional<Buyer> existByPhone(@Param("phone") String phone);

    @Query(value = "SELECT id_buyer FROM buyer WHERE phone = :phone", nativeQuery = true)
    int getBuyerIdByPhone(@Param("phone") String phone);

    @Transactional
    @Modifying
    @Query(value = "UPDATE buyer SET buyer_name = :buyer_name WHERE phone = :phone", nativeQuery = true)
    void updateNameByPhone(@Param("buyer_name") String buyer_name, @Param("phone") String phone);
}
