package com.pintoo.ems.Service.Impl;

import com.pintoo.ems.Entity.*;
import com.pintoo.ems.Repository.*;
import com.pintoo.ems.Response.OrderHistoryResponse;
import com.pintoo.ems.Response.OrderListResponse;
import com.pintoo.ems.Response.OrdersResponse;
import com.pintoo.ems.Service.OrderService;
import lombok.AllArgsConstructor;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private UserRepository userRepository;
    private CartRepository cartRepository;
    private CartProductRepository cartProductRepository;
    private ProductRepository productRepository;




    //create order
    // save and submit cart and create a new order by passing cart id
    public Integer updateProductIntoCart(long cartId) {

        Optional<Cart> cartOptional= cartRepository
                .findById(cartId);
        if (cartOptional.isPresent()) {

            Cart cart = cartOptional.get();
            Orders order = new Orders();
            order.setUsers(cart.getUser());
            orderRepository.save(order);

            return orderRepository.update(cart,order);

        }
        else{
            throw new RuntimeException("cart with " + cartId + " does not exist in cart table.");
        }
    }

    //create order
    // save and submit cart and create a new order by passing user id
    public Integer updateProductsIntoCart(long userId) {

        Optional<Cart> cartOptional= cartRepository
                .findByUserId(userId);
        if (cartOptional.isPresent()) {

            Cart cart = cartOptional.get();
            Orders order = new Orders();
            order.setUsers(cart.getUser());
            orderRepository.save(order);

            return orderRepository.update(cart,order);

        }
        else{
            throw new RuntimeException("cart with " + userId + " does not exist in cart table.");
        }
    }



    //get (finalize) order and modify the product & get the list by orderId
    public List<OrderListResponse> getOrderList(long orderId) {
        List<OrderListResponse> orderResponse = new ArrayList<>();

        Optional<List<CartProduct>> cp = cartProductRepository
                .findProductsByOrderId(orderId);
        if (!cp.isPresent()) {
            throw new RuntimeException("products with " + orderId+ " does not exist in cart-product table.");
        }
        for (CartProduct product1 : cp.get()) {
            OrderListResponse orderList = new OrderListResponse();

            Optional<Product> productOptional = productRepository
                    .findById(product1.getProduct().getId());

            if (productOptional.isPresent()) {
                Product prod = productOptional.get();

                if (prod.getQuantity() >= product1.getReqQuantity()) {
                    prod.setQuantity(prod.getQuantity() - (product1.getReqQuantity()));

                    productRepository.save(prod);
                }else{
                    throw new RuntimeException("something is wrong while reviewing the product quantity") ;
                }

            }
            orderList.setProductId(product1.getProduct().getId());
            orderList.setProductName(product1.getProduct().getProductName());
            orderList.setDescription(product1.getProduct().getDescription());
            orderList.setBrand(product1.getProduct().getBrand());
            orderList.setCategoryName(product1.getProduct().getCategory().getCategoryName());
            orderList.setReqQuantity(product1.getReqQuantity());
            orderList.setTotalPrice(product1.getPrice());
            orderList.setCreated(product1.getCreated_date());
            orderList.setModified(product1.getModified_date());
            orderResponse.add(orderList);
        }
        return orderResponse;
    }

    //get orders by userId
    public List<OrdersResponse> getOrdersByUserId(long userId) {
        List<OrdersResponse> orderResponse = new ArrayList<>();

        Optional<Cart> cartOptional = cartRepository.findCartByUserId(userId);
        if (!cartOptional.isPresent()) {
            throw new RuntimeException("cart with " + userId+ " does not exist in order table.");
        }
        Cart cart = cartOptional.get();

        Optional<List<Orders>> orderOptional = orderRepository.findOrdersByUserId(cart.getUser());
        if (!orderOptional.isPresent()) {
            throw new RuntimeException("orders with " + cart.getUser().getId()
                    + " does not exist in order table.");
        }

        for (Orders od : orderOptional.get()) {
            OrdersResponse orderList = new OrdersResponse();

            orderList.setId(od.getId());
            orderList.setEmail(od.getUsers().getEmail());
            orderList.setCreated(od.getCreated_date());
            orderList.setModified(od.getModified_date());

            orderResponse.add(orderList);
        }
        return orderResponse;
    }




    //order history
    // get all the list of ordered products by user by userId
    public List<OrderHistoryResponse> getAll(long userId) {
        List<OrderHistoryResponse> orderResponse = new ArrayList<>();

        Optional<User> userOptional = userRepository
                .findUserById(userId);
        if (!userOptional.isPresent()) {
            throw new RuntimeException("User with " + userId+ " does not exist.");
        }
        User user = userOptional.get();

        Optional<Cart> cartOptional = cartRepository
                .findCartByUserId(user.getId());
        if (!cartOptional.isPresent()) {
            throw new RuntimeException("cart with " + user.getId() + " does not exist in cart table.");
        }
        Cart cart = cartOptional.get();

        Optional<List<CartProduct>> cp = cartProductRepository
                .findProductsByCartIdWithOrderIdNotNull(cart);
        if (!cp.isPresent()) {
            throw new RuntimeException("products with " + cart.getId() + " does not exist in cart-product table.");
        }
                     for (CartProduct product1 : cp.get()) {
                        OrderHistoryResponse orderList = new OrderHistoryResponse();

                             orderList.setOrderId((product1.getOrder().getId()));
                             orderList.setProductId(product1.getProduct().getId());
                             orderList.setProductName(product1.getProduct().getProductName());
                             orderList.setReqQuantity(product1.getReqQuantity());
                             orderList.setTotalPrice(product1.getPrice());
                             orderList.setCreated(product1.getCreated_date());
                             orderList.setModified(product1.getModified_date());
                             orderResponse.add(orderList);
        }
        return orderResponse;
    }



    // order Cancellation
    public ResponseEntity deleteProductsByOrderId(long orderId){
        Optional<Orders> o = orderRepository.findById(orderId);
        if(!o.isPresent()){
            throw new RuntimeException("order with such orderId does not exist");
        }

        Optional<List<CartProduct>> p= cartProductRepository
                .findProductsByOrderId(orderId);
        if (!p.isPresent()) {
            throw new RuntimeException("product with orderId " + orderId + " does not exist in cart-product table.");
        }

        for (CartProduct product1 : p.get()) {


            Optional<Product> productOptional = productRepository
                    .findById(product1.getProduct().getId());

            if (productOptional.isPresent()) {
                Product prod = productOptional.get();

                prod.setQuantity(prod.getQuantity() + (product1.getReqQuantity()));
                productRepository.save(prod);

            }

            cartProductRepository.deleteById(product1.getId());
        }
        orderRepository.deleteById(orderId);
        return ResponseEntity.ok("Order is cancelled");

    }


}
