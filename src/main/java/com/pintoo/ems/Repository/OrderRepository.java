package com.pintoo.ems.Repository;

import com.pintoo.ems.Entity.Cart;
import com.pintoo.ems.Entity.Orders;
import com.pintoo.ems.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


public interface OrderRepository extends JpaRepository<Orders, Long> {


    @Query("select u from Orders u where u.users = :#{#user}")
    Optional<List<Orders>> findOrdersByUserId(User user);

    Optional<Orders> findById(Long orderId);

    @Query("select u from Orders u where u.users = :#{#user}")
    Optional<List<Orders>> findByUserId(User user);

    @Transactional
    @Modifying
    @Query("Update CartProduct cp set cp.order = :order where cp.cart= :cart and cp.order is null")
    int update(Cart cart , Orders order);


}
