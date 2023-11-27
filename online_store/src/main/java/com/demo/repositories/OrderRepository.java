package com.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.entities.OrderDetails;

public interface OrderRepository extends JpaRepository<OrderDetails, Integer>{

}
