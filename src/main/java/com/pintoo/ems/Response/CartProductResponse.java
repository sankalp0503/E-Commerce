package com.pintoo.ems.Response;

import com.pintoo.ems.Entity.Orders;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartProductResponse {


    private long  cartProductId;

    private long productId;

    private String productName;

    private String category;

    private long cartId;


    private int reqQuantity;

private boolean checkDeleted;

    private double price;

    private Date created;

    private Date modified;


}
