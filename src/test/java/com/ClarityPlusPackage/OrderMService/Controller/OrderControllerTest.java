package com.ClarityPlusPackage.OrderMService.Controller;
import org.springframework.http.HttpStatus;
import com.ClarityPlusPackage.OrderMService.Entity.Order;
import com.ClarityPlusPackage.OrderMService.Service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import org.mockito.MockitoAnnotations;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;


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
        String InstituteID="Institute123";
        when(restTemplate.getForObject(eq("http://recipient-microservice/recipient/getEmailID/"+InstituteID+"/"), eq(String.class)))
                .thenReturn(emailID);

        // Call the controller method
        String result = orderController.getEmailOfInstituteID("Institute123");

        // Verify the results
        assertEquals(emailID, result);
        String emailID1 = "exampl1e@example.com";
        String InstituteID1="Institute456";
        when(restTemplate.getForObject(eq("http://recipient-microservice/recipient/getEmailID/"+InstituteID1+"/"), eq(String.class)))
                .thenReturn(emailID);

        // Call the controller method
        String result1 = orderController.getEmailOfInstituteID("Institute123");

        // Verify the results
        assertEquals(emailID, result1);
    }
    @Test
    void testGetLogsByInstituteID() {
        // Mock the restTemplate.getForObject() method
        List<String> logs = Arrays.asList("log1", "log2", "log3");
        when(restTemplate.getForObject("http://recipient-microservice/recipient/search/logs/Institute123/", List.class))
                .thenReturn(logs);

        // Call the controller method
        List<String> result = orderController.getLogsByInstituteID("Institute123");

        // Verify the results
        assertEquals(logs, result);
        List<String> logs1 = Arrays.asList("log11", "log22", "log32");
        when(restTemplate.getForObject("http://recipient-microservice/recipient/search/logs/Institute1234/", List.class))
                .thenReturn(logs1);

        // Call the controller method
        List<String> result1 = orderController.getLogsByInstituteID("Institute1234");

        // Verify the results
        assertEquals(logs1, result1);
    }
    @Test
    void testGetLogsByDate() throws ParseException {
        // Mock the orderService.findOrderByDate() method
        List<String> expectedLogs = Arrays.asList("log1", "log2", "log3");
        when(orderService.findOrderByDate(anyString())).thenReturn(expectedLogs);
        // Call the controller method
        List<String> result = orderController.getLogsByDate("2023-05-15");
        // Verify the results
        assertEquals(expectedLogs, result);
    }

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
    @Test
    void testVerifyOtp() {
        // Mock the restTemplate.postForObject() method
        String otpVerifyResponse = "OTP verified";
        when(restTemplate.postForObject("http://recipient-microservice/recipient/checkotp/{InstituteID}/{otp}", null, String.class, "Institute123", 456))
                .thenReturn(otpVerifyResponse);

        // Call the controller method
        ResponseEntity<String> result = orderController.verifyOtp("Institute123", 456);

        // Verify the results
        assertEquals(otpVerifyResponse, result.getBody());
        assertEquals(200, result.getStatusCodeValue());
    }
    @Test
    void testLoginGuard() {
        // Mock the orderService.loginGuard() method
        String response = "Login successful";
        when(orderService.loginGuard(anyString(), anyString())).thenReturn(response);

        // Call the controller method
        ResponseEntity<String> result1 = orderController.loginGuard("example1@example.com", "password1");
        ResponseEntity<String> result2 = orderController.loginGuard("example2@example.com", "password2");

        // Verify the results
        assertEquals(HttpStatus.OK, result1.getStatusCode());
        assertEquals(response, result1.getBody());
        assertEquals(HttpStatus.OK, result2.getStatusCode());
        assertEquals(response, result2.getBody());
    }
}

