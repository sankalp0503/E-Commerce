package com.pintoo.ems.Response;


import com.pintoo.ems.Entity.Orders;
import com.pintoo.ems.Entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderHistoryResponse {


    private long productId;

    private String productName;

    private int reqQuantity;

    private double totalPrice;

    private long  orderId;

    private Date created;

    private Date modified;
}
