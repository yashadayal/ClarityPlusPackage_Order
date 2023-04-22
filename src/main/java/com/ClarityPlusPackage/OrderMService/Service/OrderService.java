package com.ClarityPlusPackage.OrderMService.Service;

import com.ClarityPlusPackage.OrderMService.Entity.Order;

import java.util.List;
import java.util.Map;

public interface OrderService {


    List findOrderByOrderID(List<String> recipientDetailsList, String InstituteID);

    String saveOrder(List<Order> orders);

}
