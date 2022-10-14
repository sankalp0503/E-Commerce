package com.pintoo.ems.Request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartProductRequest {

 private long userId;

 private long productId;

 private int reqQuantity;

 private Date created;

 private Date modified;


}
