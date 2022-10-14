package com.pintoo.ems.Service;

import com.pintoo.ems.Entity.CartProduct;
import com.pintoo.ems.Entity.Product;
import com.pintoo.ems.Request.CartProductRequest;
import com.pintoo.ems.Request.ProductRequest;
import com.pintoo.ems.Response.CartProductResponse;
import com.pintoo.ems.Response.ProductResponse;
import com.pintoo.ems.Response.TrendResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {

    Product addProduct(long categoryId, ProductRequest productRequest);
    List<ProductResponse> getAllProduct();
    List<ProductResponse> getProductById(long productId);
    List<ProductResponse> getProductsById(long categoryId);
    List<Product> getProductByCategoryId(long categoryId);
    ResponseEntity deleteProductById(long productId);
    Product updateProduct(ProductRequest productRequest ,long categoryId , long productId);
    CartProduct addIntoCart(long userId , long productId ,CartProductRequest cartProductRequest);
    List<CartProduct> getAllProductsInCartProduct();
    List<CartProductResponse> getAllProductInCartProduct();
    List<TrendResponse> getTrendingProductInCartProduct();
    List<CartProductResponse> getPreviewProductInCartProduct(long userId);
    List<CartProductResponse> getProductsByCartId(long cartId);
    List<CartProductResponse> getProductsByUserId(long userId);
    List<CartProductResponse> getProductByUserIdWithNullOrderId(long userId);
    List<CartProductResponse> getProductByUserId(long userId);
    ResponseEntity deleteProductsByCartId( long cartId);
    ResponseEntity deleteProductsByCartProductId( long cartProductId);
    ResponseEntity deleteProductsByUserId( long userId);
    Page<Product> findPaginated(int pageNo, int pageSize);
    Page<CartProduct> findCartProductPaginated(int pageNo, int pageSize);
    //Iterable<Product> findAll(boolean isDeleted);


}
