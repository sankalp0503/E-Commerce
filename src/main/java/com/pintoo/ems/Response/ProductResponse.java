package com.pintoo.ems.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

    private String productName;


    private  String description;


    private String brand;


    private int quantity;


    private double price;

    private Date created;

    private Date modified;
}
