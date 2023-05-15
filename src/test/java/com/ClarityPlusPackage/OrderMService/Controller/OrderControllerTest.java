package com.ClarityPlusPackage.OrderMService.Controller;

import com.ClarityPlusPackage.OrderMService.Entity.Order;
import com.ClarityPlusPackage.OrderMService.Service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class OrderControllerTest {
    @Mock
    private RestTemplate restTemplate;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetOrderByInstituteID() {
        // Mock the restTemplate.getForObject() method
        List<String> recipientDetailsList = new ArrayList<>();
        when(restTemplate.getForObject(eq("http://recipient-microservice/recipient/search/{InstituteID}/"), eq(List.class)))
                .thenReturn(recipientDetailsList);

        // Mock the orderService.findOrderByOrderID() method
        List<String> orderList = new ArrayList<>();
        when(orderService.findOrderByOrderID(eq(recipientDetailsList), anyString())).thenReturn(orderList);

        // Call the controller method
        List<String> result = orderController.getOrderByInstituteID("Institute123");

        // Verify the results
        assertEquals(orderList, result);
    }

    @Test
    void testGetEmailOfInstituteID() {
        // Mock the restTemplate.getForObject() method
        String emailID = "example@example.com";
        when(restTemplate.getForObject(eq("http://recipient-microservice/recipient/getEmailID/{InstituteID}/"), eq(String.class)))
                .thenReturn(emailID);

        // Call the controller method
        String result = orderController.getEmailOfInstituteID("Institute123");

        // Verify the results
        assertEquals(emailID, result);
    }

    // Add more tests for the remaining controller methods
    // ...

    @Test
    void testSaveOrder() {
        // Mock the orderService.saveOrder() method
        String success = "Order saved successfully";
        when(orderService.saveOrder(any())).thenReturn(success);

        // Call the controller method
        Order[] orders = new Order[0];
        ResponseEntity<String> response = orderController.saveOrder(orders);

        // Verify the results
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(success, response.getBody());
    }

    // Add more tests for the remaining controller methods
    // ...
}

