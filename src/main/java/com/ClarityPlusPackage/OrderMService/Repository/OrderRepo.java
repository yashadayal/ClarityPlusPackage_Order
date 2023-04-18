package com.ClarityPlusPackage.OrderMService.Repository;

import com.ClarityPlusPackage.OrderMService.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order, String> {

    @Query("SELECT p.OrderID FROM Order p WHERE p.OrderID = :orderID")
    String findOrderByOrderID(@Param("orderID")String orderID);

    @Query("SELECT o.OrderID FROM Order o")
    List<String> getAllOrderIDByInstituteID();

}
