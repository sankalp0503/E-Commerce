package com.pintoo.ems.Service;

import com.pintoo.ems.Response.OrderHistoryResponse;
import com.pintoo.ems.Response.OrderListResponse;
import com.pintoo.ems.Response.OrdersResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderService  {

    Integer updateProductIntoCart(long cartId);
    Integer updateProductsIntoCart(long userId);
    List<OrderListResponse> getOrderList(long orderId);
    List<OrdersResponse> getOrdersByUserId(long userId);
    List<OrderHistoryResponse> getAll(long userId);

    ResponseEntity deleteProductsByOrderId(long orderId);

}
