package com.ClarityPlusPackage.OrderMService.Controller;

import com.ClarityPlusPackage.OrderMService.Entity.LoginDetails;
import com.ClarityPlusPackage.OrderMService.Entity.Order;
import com.ClarityPlusPackage.OrderMService.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RestController
@RequestMapping("/order")
public class OrderController {

    private static final Logger logger=LogManager.getLogger(OrderController.class);
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    OrderService orderService;

    @GetMapping("/ordersOfInstituteID/{InstituteID}/")
    public List<String> getOrderByInstituteID(@PathVariable("InstituteID") String InstituteID){
        logger.debug("Inside Controller");
        List<String> recipientDetailsList = this.restTemplate.getForObject("http://recipient-microservice/recipient/search/"+InstituteID+"/", List.class);
        List<String> orderList = this.orderService.findOrderByOrderID(recipientDetailsList, InstituteID);
        logger.debug("Outside Controller");
        return orderList;
    }

    @GetMapping("/emailOfInstituteID/{InstituteID}/")
    public String getEmailOfInstituteID(@PathVariable ("InstituteID") String InstituteID){
        logger.debug("Inside Controller");
        String emailID = this.restTemplate.getForObject("http://recipient-microservice/recipient/getEmailID/"+InstituteID+"/",String.class);
        logger.debug("Outside Controller");
        return emailID;
    }

    @GetMapping("/search/logsbyID/{InstituteID}/")
    public List<String> getLogsByInstituteID(@PathVariable("InstituteID") String InstituteID) {
        logger.debug("Inside Controller");
        logger.info("Searching orders by Institute Id");
        List<String> logs = this.restTemplate.getForObject("http://recipient-microservice/recipient/search/logs/" + InstituteID + "/", List.class);
        logger.debug("Outside Controller");
        return logs;
    }

    @GetMapping("/search/logsbydate/{date}/")
    public List<String> getLogsByDate(@PathVariable("date") String date) throws ParseException {
        logger.debug("Inside Controller");
        logger.info("Searching orders by date");
        List<String> logs = this.orderService.findOrderByDate(date);
        logger.debug("Outside Controller");
        return logs;
    }

    @PostMapping("/saveorderdata")
    public ResponseEntity<String> saveOrder(@RequestBody Order[] orders) {
        System.out.println(orders[0]);
        logger.debug(orders[0]);
        logger.info("Saving data of orders");
        String success=this.orderService.saveOrder(orders);
        return ResponseEntity.ok(success);
    }

    @PostMapping("/verifyOtp/{InstituteID}/{otp}")
    public ResponseEntity<String> verifyOtp(@PathVariable("InstituteID") String InstituteID, @PathVariable("otp") int otp){
        String otpVerifyResponse = this.restTemplate.postForObject("http://recipient-microservice/recipient/checkotp/{InstituteID}/{otp}",null,String.class,InstituteID,otp);
        logger.debug(otpVerifyResponse);
        return ResponseEntity.ok(otpVerifyResponse);
    }

    @PostMapping("/login/guard/{emailID}/{password}/")
    public ResponseEntity<String> loginGuard(@PathVariable("emailID") String emailID, @PathVariable("password") String password) {
        //to avoid writing custom deserialization logic
        //sending email and password as pathvariables as @RequestBody is receiving NULL
        String response = this.orderService.loginGuard(emailID,password);
        return ResponseEntity.ok(response);
    }
}
