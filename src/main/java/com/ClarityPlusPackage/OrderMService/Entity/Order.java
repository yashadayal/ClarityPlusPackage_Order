package com.ClarityPlusPackage.OrderMService.Entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name="orderdetails")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @Column(name = "OrderID")
    String OrderID;

    @Column(nullable=false,name="FirstName")
    String FirstName;

    @Column(nullable=false,name="LastName")
    String  LastName;

    @Column(nullable=false,name="DateOfDelivery")
    Date DateOfDelivery;

    @Column(nullable=false,name="Retailer")
    String Retailer;
}
