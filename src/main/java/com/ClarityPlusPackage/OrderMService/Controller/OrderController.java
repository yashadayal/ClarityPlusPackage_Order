package com.ClarityPlusPackage.OrderMService.Controller;

import com.ClarityPlusPackage.OrderMService.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    //API Call to Recipient Details

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    OrderService orderService;

    @GetMapping("/ordersOfInstituteID/{InstituteID}/")
    public List<String> getOrderByInstituteID(@PathVariable("InstituteID") String InstituteID){
        System.out.println("Inside Controller");
        List<String> recipientDetailsList = this.restTemplate.getForObject("http://localhost:9002/recipient/search/"+InstituteID+"/", List.class);
        for(String o : recipientDetailsList)
            System.out.println(o);
        if(recipientDetailsList.isEmpty())
        {
            List<String> response = new ArrayList<>();
            response.add("There is no pending orders for InstituteID "+InstituteID+". If there is any order yet to receive first fill the form for corresponding orderID.");
        }
        List<String> orderList = this.orderService.findOrderByOrderID(recipientDetailsList, InstituteID);
        orderList.add("If there is any order yet to receive first fill the form for corresponding orderID.");
        System.out.println("Outside Controller");
        return orderList;
    }

    //Will be implemented by Double A
    //@PostMapping("")
}
