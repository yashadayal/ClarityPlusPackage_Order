package com.ClarityPlusPackage.OrderMService.Implementation;

import com.ClarityPlusPackage.OrderMService.Repository.OrderRepo;
import com.ClarityPlusPackage.OrderMService.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderImplementation implements OrderService {

    @Autowired
    OrderRepo orderRepo;

    @Override
    public List<String> findOrderByOrderID(List<String> recipientDetailsList, String InstituteID) {
        System.out.println("Inside Implementation");
        List<String> orderExistOrNot = new ArrayList<>();
        if(recipientDetailsList.isEmpty())
        {
            orderExistOrNot.add("There is no pending orders for InstituteID "+InstituteID+". If there is any order yet to receive first fill the form for corresponding orderID.");
            System.out.println("Outside Implementation");
            return orderExistOrNot;
        }
        for(String orderID : recipientDetailsList)
        {
            //take one orderID came from recipient and check it if it exists here or not
            if(this.orderRepo.findOrderByOrderID(orderID) == null)
                orderExistOrNot.add("This order ID "+orderID+" does not exist in Order Database.");
            else
                orderExistOrNot.add(orderID);
        }
//        orderExistOrNot.add("If there is any order yet to receive first fill the form for corresponding orderID.");
        System.out.println("Outside Implementation");
        return orderExistOrNot;
    }
}
