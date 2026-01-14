package com.thejus.brown.springECOM.repository;

import com.thejus.brown.springECOM.model.Order;
import com.thejus.brown.springECOM.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepo  extends JpaRepository<Order,Integer> {
    Optional<Order>findByOrderId(String OrderId);
}
