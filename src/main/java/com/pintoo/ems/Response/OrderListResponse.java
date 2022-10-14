package com.pintoo.ems.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderListResponse {

private long productId;

private String productName;

private String description;

private String brand;

private String categoryName;

private int reqQuantity;

private double totalPrice;

    private Date created;

    private Date modified;



}
