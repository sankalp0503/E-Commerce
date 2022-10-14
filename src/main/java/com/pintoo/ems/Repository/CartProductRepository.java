package com.pintoo.ems.Repository;
import com.pintoo.ems.Entity.Cart;
import com.pintoo.ems.Entity.CartProduct;
import com.pintoo.ems.Response.TrendResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;




public interface CartProductRepository extends JpaRepository<CartProduct,Long> {

    Optional<List<CartProduct>> findProductsByCartId(Long cartId);
    Optional<CartProduct> findProductsById(Long cartProductId);
    Optional<List<CartProduct>> findProductsByOrderId(Long orderId);


    @Query("select cp from CartProduct cp where cp.cart = :#{#cart} and cp.order is null")
    Optional<List<CartProduct>> findProductsByCartIdWithNullOrderId(Cart cart);

    @Query("select cp from CartProduct cp where cp.cart = :cart and cp.order is not null")
    Optional<List<CartProduct>> findProductsByCartIdWithOrderIdNotNull( Cart cart);

    @Query("select cp from CartProduct cp where cp.cart = :cart ")
    Optional<List<CartProduct>> findByCartId(Cart cart);

    @Query("select cp from CartProduct cp where cp.cart = :cart and cp.deleted=false")
    Optional<List<CartProduct>> findProductsByCartIdWithDeleted(Cart cart);


    @Query( value="Select products.product_id ,products.product_name ,products.brand , sum(cart_product.quantity)\n" +
            "From  products\n" +
            "INNER JOIN cart_product \n" +
            "on products.product_id = cart_product.product_id\n" +
            "group by product_id\n" +
            " Order by sum(quantity) \n" +
            " desc limit 7" ,nativeQuery = true )
    List<TrendResponse> findTrend();


    //"Select Product.id ,Product.productName ,Product.brand , sum(CartProduct.reqQuantity)\n" +
    //            "From  Product\n" +
    //            "INNER JOIN CartProduct \n" +
    //            "on Product.id = CartProduct.product\n" +
    //            "group by CartProduct.product\n" +
    //            " Order by sum(CartProduct.reqQuantity) \n" +
    //            " desc limit 6"

    //"SELECT c.year, COUNT(c.year) FROM Comment AS c GROUP BY c.year ORDER BY c.year DESC"

//    @Query(value = "select top 5 p.*, sum(po.quantity) as total_quantity from product p " +
//            "inner join productorder po " +
//            "on p.id = po.product_id " +
//            "group by p.id, p.name " +
//            "order by total_quantity desc", nativeQuery = true)
}
