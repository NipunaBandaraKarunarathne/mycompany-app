package com.example.order.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.order.model.Orders;

@Repository
public interface OrderRepo extends JpaRepository<Orders, Integer> {

    @Query(value = "SELECT * FROM orders WHERE id = ?1", nativeQuery = true)
    Orders getOrderById(Integer orderId);
}