package com.pintoo.ems.Controller;


import com.pintoo.ems.Entity.CartProduct;
import com.pintoo.ems.Entity.Product;
import com.pintoo.ems.Entity.User;
import com.pintoo.ems.Request.CartProductRequest;
import com.pintoo.ems.Request.ProductRequest;
import com.pintoo.ems.Response.CartProductResponse;
import com.pintoo.ems.Response.ProductResponse;
import com.pintoo.ems.Response.TrendResponse;
import com.pintoo.ems.Service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/api/product")
@AllArgsConstructor
public class ProductController {

    private ProductService productService;

    // add product as per categoryId in product table
    @PostMapping("/{categoryId}")
    public ResponseEntity<Product> saveProduct (@Valid @PathVariable long categoryId, @RequestBody ProductRequest productRequest){
        return new ResponseEntity (productService.addProduct(categoryId,productRequest), HttpStatus.CREATED);

    }

    // add product into cart-product table
    @PostMapping("/addIntoCart/{userId}/{productId}")
    public ResponseEntity<CartProduct> saveProductIntoCart (@Valid @PathVariable long userId , @PathVariable long productId, @RequestBody CartProductRequest cartProductRequest){
        return new ResponseEntity (productService.addIntoCart(userId , productId ,cartProductRequest), HttpStatus.CREATED);

    }

   // get all products from product table
    @GetMapping("/all")
    public List<ProductResponse> getAllProduct(){
        return productService.getAllProduct();

    }

    // get product by productId from product table
    @GetMapping("/productByProductId/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable long productId){
        return new ResponseEntity(productService.getProductById(productId),HttpStatus.OK);

    }

    // get products by categoryId from product table
    @GetMapping("/productByCategoryId/{categoryId}")
    public ResponseEntity<Product> getProductByCategoryId(@PathVariable long categoryId){
        return new ResponseEntity(productService.getProductsById(categoryId),HttpStatus.OK);

    }

    // get products by categoryId from product table
    @GetMapping("/withIdProductByCategoryId/{categoryId}")
    public ResponseEntity<Product> getProductsByCategoryId(@PathVariable long categoryId){
        return new ResponseEntity(productService.getProductByCategoryId(categoryId),HttpStatus.OK);

    }

    // get products from the cart-product table with cartID
    @GetMapping("/productsInCartProductByCartId/{cartId}")
    public ResponseEntity<CartProductResponse> getProducts(@PathVariable long cartId){
        return new ResponseEntity(productService.getProductsByCartId(cartId),HttpStatus.OK);

    }

    // get products from the cart-product table with userID
    @GetMapping("/productsInCartProductByUserId/{userId}")
    public ResponseEntity<CartProductResponse> getProductsByUserId(@PathVariable long userId){
        return new ResponseEntity(productService.getProductsByUserId(userId),HttpStatus.OK);

   }

    // get products with id from the cart-product table with userID
    @GetMapping("/productsWithIdInCartProductByUserId/{userId}")
    public ResponseEntity<CartProductResponse> getProductByUserId(@PathVariable long userId){
        return new ResponseEntity(productService.getProductByUserId(userId),HttpStatus.OK);

    }

    // get products with id from the cart-product table with userID whose orderId is null
    @GetMapping("/productsWithIdInCartProductByUserIdWith NullOrderId/{userId}")
    public ResponseEntity<CartProductResponse> getProductByUserIdWithNullOrderId(@PathVariable long userId){
        return new ResponseEntity(productService.getProductByUserIdWithNullOrderId(userId),HttpStatus.OK);

    }

    // get all products in cart-product table
    @GetMapping("/getAllProductsInCartProduct")
    public List<CartProduct> getAllProductsInCartProduct(){
        return productService.getAllProductsInCartProduct();

    }

    //get all products in cart-product
    // get all products in cart-product table with its id
    @GetMapping("/getAllProductsInCartProductWithId")
    public List<CartProductResponse> getAllProductInCartProduct(){
        return productService.getAllProductInCartProduct();
    }

    //get trending products from cart-product
    @GetMapping("/getTrendingProductsInCartProduct")
    public List<TrendResponse> getTrendingProductInCartProduct(){
        return productService.getTrendingProductInCartProduct();
    }

//    @GetMapping
//    public Iterable<Product> findAll(@RequestParam(value = "isDeleted", required = false, defaultValue = "false") boolean isDeleted) {
//        return productService.findAll(isDeleted);
//    }


    //get all @sqlDeleted and non deleted products
    // get all products as preview in cart-product table with its Userid
    @GetMapping("/getPreviewItemsByUserId/{userId}")
    public ResponseEntity<CartProductResponse> getAllPreviewProductInCartProduct(@PathVariable long userId){
        return new ResponseEntity(productService.getPreviewProductInCartProduct(userId),HttpStatus.OK);
    }


    //delete products from card-product table by cartId
    @DeleteMapping("/cartProducts/{cartId}")
    public ResponseEntity<CartProduct> deleteProductsByCartId(@PathVariable long cartId) {
        return new ResponseEntity(productService.deleteProductsByCartId(cartId),HttpStatus.OK);

    }

    //delete products from card-product table by cartproductId
    @DeleteMapping("/cartProductsByCartProductId/{cartProductId}")
    public ResponseEntity<CartProduct> deleteProductsByCartProductId(@PathVariable long cartProductId) {
        return new ResponseEntity(productService.deleteProductsByCartProductId(cartProductId),HttpStatus.OK);

    }


    // delete products from card-product table by userId
    @DeleteMapping("/cartProductsByUserId/{userId}")
    public ResponseEntity<CartProduct> deleteProductsByCartUserId(@PathVariable long userId) {
        return new ResponseEntity(productService.deleteProductsByUserId(userId),HttpStatus.OK);

    }


    // delete product by productId from product table
    @DeleteMapping("/{productId}")
    public ResponseEntity<Product> deleteProductById(@PathVariable long productId) {
        return new ResponseEntity(productService.deleteProductById(productId),HttpStatus.OK);

    }

    // update product By productId in product table
    @PutMapping("{categoryId}/{productId}")
    public ResponseEntity<Product> updateProduct(@RequestBody ProductRequest productRequest ,@PathVariable long categoryId, @PathVariable long productId){
        return new ResponseEntity(productService.updateProduct(productRequest,categoryId ,productId),HttpStatus.OK);

    }

    @GetMapping("/page/{pageNo}/{pageSize}")
    public Page<Product> getPaginated(@PathVariable int pageNo, @PathVariable int pageSize ) {
        return  productService.findPaginated(pageNo,pageSize);
    }

    @GetMapping("/cartProductPage/{pageNo}/{pageSize}")
    public Page<CartProduct> getCartProductPaginated(@PathVariable int pageNo, @PathVariable int pageSize ) {
        return  productService.findCartProductPaginated(pageNo,pageSize);
    }

}
