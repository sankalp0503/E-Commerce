package com.pintoo.ems.Repository;

import com.pintoo.ems.Entity.Cart;
import com.pintoo.ems.Entity.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long> {

    Optional<Cart> findByUserId(Long userId);
    Optional<Cart> findCartByUserId(Long userId);
    Optional<Cart> findById(Long cartId);
}
