package com.pintoo.ems.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {


    private String productName;


    private  String description;


    private String brand;


    private int quantity;


    private double price;
}
