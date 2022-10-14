package com.pintoo.ems.Controller;


import com.pintoo.ems.Entity.CartProduct;
import com.pintoo.ems.Response.CartProductResponse;
import com.pintoo.ems.Response.OrderHistoryResponse;
import com.pintoo.ems.Response.OrderListResponse;
import com.pintoo.ems.Response.OrdersResponse;
import com.pintoo.ems.Service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/api/order")
@AllArgsConstructor
public class OrderController {

    private OrderService orderService;


    // create/ place order
    // save and submit the cart to generate an order id by using cartId.
    @PutMapping("/saveAndSubmitAndCreateOrder/{cartId}")
    public String  updateProductIntoCart (@PathVariable long cartId){
        return orderService.updateProductIntoCart(cartId)+" products of ORDER IS CREATED";

    }

    // create/ place order
    // save and submit the cart to generate an order id by using userId.
    @PutMapping("/saveAndSubmitAndCreateOrderWithUserId/{userId}")
    public String  updateProductsIntoCart (@PathVariable long userId){
        return orderService.updateProductsIntoCart(userId)+" products of ORDER IS CREATED";

    }

    // order accepted , order list
    //get ordered products ka list and modified product quantity by order Id
    @GetMapping("/orderProductDetails/{orderId}")
    public List<OrderListResponse> getOrderList(@PathVariable long orderId){
        return orderService.getOrderList(orderId);

    }


    // get orders list from orders table by userID
    @GetMapping("/allOrdersInOrdersByUserId/{userId}")
    public ResponseEntity<OrdersResponse> getOrdersByUserId(@PathVariable long userId){
        return new ResponseEntity(orderService.getOrdersByUserId(userId),HttpStatus.OK);

    }



    // order history
    //get all the list of products ordered by  a user  (  order created and net prod quantity modified)
    @GetMapping("/orderDetails/{userId}")
    public List<OrderHistoryResponse> getOrderedProducts(@PathVariable long userId){
        return orderService.getAll(userId);

    }

    //order cancellation
    @DeleteMapping("/cancelAllProductsByOrderId/{orderId}")
    public ResponseEntity<CartProduct> deleteProductsByOrderId(@PathVariable long orderId) {
        return new ResponseEntity(orderService.deleteProductsByOrderId(orderId), HttpStatus.OK);

    }




}
