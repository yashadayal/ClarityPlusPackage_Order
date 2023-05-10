package com.ClarityPlusPackage.OrderMService.Implementation;

import com.ClarityPlusPackage.OrderMService.Entity.LoginDetails;
import com.ClarityPlusPackage.OrderMService.Entity.Order;
import com.ClarityPlusPackage.OrderMService.Repository.LoginRepo;
import com.ClarityPlusPackage.OrderMService.Repository.OrderRepo;
import com.ClarityPlusPackage.OrderMService.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
@Service
public class OrderImplementation implements OrderService {

    private static final Logger logger = LogManager.getLogger(OrderImplementation.class);
    @Autowired
    OrderRepo orderRepo;

    @Autowired
    LoginRepo loginRepo;

    @Override
    public List<String> findOrderByOrderID(List<String> recipientDetailsList, String InstituteID) {
        logger.debug("Inside Implementation");
        List<String> orderExistOrNot = new ArrayList<>();
        //System.out.println(recipientDetailsList.get(0));
        if(recipientDetailsList.isEmpty())
        {
            orderExistOrNot.add("There is no pending orders for InstituteID "+InstituteID+". If there is any order yet to receive first fill the form for corresponding orderID.");
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
        logger.debug("Outside Implementation");
        return orderExistOrNot;
    }

    @Override
    public String saveOrder(Order[] orders)
    {
        logger.info("Saving data");
        logger.debug("Inside Implementation");
        for(Order order:orders)
            this.orderRepo.save(order);
        logger.debug("Outside Implementation");
        return "Orders saved successfully!";
    }

    @Override
    public List<String> findOrderByDate(String date) throws ParseException {
        logger.debug("Inside Implementation");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date stringToDate = dateFormat.parse(date);
        List<String> logs = this.orderRepo.findOrdersByDate(stringToDate);
        logger.debug("Outside Implementation");
        return logs;
    }

    @Override
    public String loginGuard(String emailID, String password) {
        logger.debug("Inside Implementation");
        String emailExistOrNot = this.orderRepo.findByEmailID(emailID);
        System.out.println(emailExistOrNot);
        logger.debug("EmailID: {}", emailExistOrNot);
        if(emailExistOrNot == null) {
            logger.warn("EmailID does not exist. Re-check the emailID or contact the admin.");
            return "EmailID does not exist. \n Re-check the emailID or contact the admin.";
        }
        String passwordWithEmailID = this.orderRepo.findPasswordByEmailID(emailExistOrNot);
        if(!passwordWithEmailID.equals(password)){
            logger.warn("Invalid Login");
            return "Invalid Login";
        }
        logger.debug("Valid Login");
        return "Valid Login";
    }

    @Override
    public String dataPopulate() {
        LoginDetails details1 = new LoginDetails("guard1@gmail.com","xyz123");
        this.loginRepo.save(details1);
        LoginDetails details2 = new LoginDetails("guard2@gmail.com","abc123");
        this.loginRepo.save(details2);
        return "Guard Details Registered!";
    }
}
