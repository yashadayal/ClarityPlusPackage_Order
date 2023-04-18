package com.ClarityPlusPackage.OrderMService.Service;

import java.util.List;
import java.util.Map;

public interface OrderService {


    List findOrderByOrderID(List<String> recipientDetailsList, String InstituteID);
}
