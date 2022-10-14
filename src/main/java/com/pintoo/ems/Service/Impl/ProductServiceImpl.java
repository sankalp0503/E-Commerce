package com.pintoo.ems.Service.Impl;

import com.pintoo.ems.Entity.*;
import com.pintoo.ems.Repository.*;
import com.pintoo.ems.Request.CartProductRequest;
import com.pintoo.ems.Request.ProductRequest;
import com.pintoo.ems.Response.CartProductResponse;
import com.pintoo.ems.Response.ProductResponse;
import com.pintoo.ems.Response.TrendResponse;
import com.pintoo.ems.Service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private UserRepository userRepository;
    private CartProductRepository cartProductRepository;
    private CartRepository cartRepository;
    private OrderRepository orderRepository;

    @Autowired
    private EntityManager entityManager;

    //post for product table
    public Product addProduct(long categoryId, ProductRequest productRequest)  {
        Optional<Category> categoryOptional= categoryRepository
                .findCategoryById(categoryId); // taking anothr field as unique key so that if same with another goes it doe not add as new data
        if(!categoryOptional.isPresent()){
            throw new RuntimeException("Category with "+categoryId+" does not exist.");
        }

        Product product= new Product();
        product.setProductName(productRequest.getProductName());
        product.setDescription(productRequest.getDescription());
        product.setBrand(productRequest.getBrand());
        product.setPrice(productRequest.getPrice());
        product.setQuantity(productRequest.getQuantity());
        product.setCategory(categoryOptional.get());
        return productRepository.save(product);
    }

    // get all PRODUCTS from product table
    public List<ProductResponse> getAllProduct() {
        List<Product> product = productRepository.findAll();
        List<ProductResponse> productResponse = new ArrayList<>();
        for (Product product1 : product) {
            ProductResponse prod = new ProductResponse();
            prod.setProductName(product1.getProductName());
            prod.setDescription(product1.getDescription());
            prod.setBrand(product1.getBrand());
            prod.setPrice(product1.getPrice());
            prod.setQuantity(product1.getQuantity());
            prod.setCreated(product1.getCreated_date());
            prod.setModified(product1.getModified_date());
            productResponse.add(prod);
        }
        return productResponse;
    }

    // get products by productsId from product table
    public List<ProductResponse> getProductById(long productId){
        List<ProductResponse> prodResponseList= new ArrayList<>();
        Optional<List<Product>> product = productRepository.findProductById(productId);
        if(product.isPresent()) {
            for (Product product1 : product.get()) {
                ProductResponse prod = new ProductResponse();
                prod.setProductName(product1.getProductName());
                prod.setDescription(product1.getDescription());
                prod.setBrand(product1.getBrand());
                prod.setPrice(product1.getPrice());
                prod.setQuantity(product1.getQuantity());
                prodResponseList.add(prod);
            }
            return prodResponseList;
        }
        else{
            throw new RuntimeException("User with "+productId+" does not exist.");
        }

    }

    // get product by categoryId from product table
    public List<ProductResponse> getProductsById(long categoryId) {
        List<ProductResponse> prodResponseList= new ArrayList<>();
        Optional<List<Product>> productAddress= productRepository
                .findProductsByCategoryId(categoryId);
        if (!productAddress.isPresent()) {
            throw new RuntimeException("User with " + categoryId + " does not exist.");
        }
        for (Product product1 :     productAddress.get()) {
            ProductResponse prod = new ProductResponse();
            prod.setProductName(product1.getProductName());
            prod.setDescription(product1.getDescription());
            prod.setBrand(product1.getBrand());
            prod.setPrice(product1.getPrice());
            prod.setQuantity(product1.getQuantity());
            prodResponseList.add(prod);
        }
        return prodResponseList;

    }
    // get product by categoryId from product table
    public List<Product> getProductByCategoryId(long categoryId) {
        List<Product> prodList= new ArrayList<>();
        Optional<List<Product>> productAddress= productRepository
                .findProductsByCategoryId(categoryId);
        if (!productAddress.isPresent()) {
            throw new RuntimeException("User with " + categoryId + " does not exist.");
        }
        for (Product product1 :     productAddress.get()) {
            Product prod = new Product();
            prod.setId(product1.getId());
            prod.setProductName(product1.getProductName());
            prod.setDescription(product1.getDescription());
            prod.setBrand(product1.getBrand());
            prod.setPrice(product1.getPrice());
            prod.setQuantity(product1.getQuantity());
            prod.setCreated_date(product1.getCreated_date());
            prod.setModified_date(product1.getModified_date());
            prodList.add(prod);
        }
        return prodList;

    }


    //delete product by productId from product table
    public ResponseEntity deleteProductById( long productId){
        Optional<Product> product = productRepository.findById(productId);
        if(product.isPresent()){
            productRepository.deleteById(productId);
            return ResponseEntity.ok("Success");
        }else{
            throw new RuntimeException("product does not exist");
        }

    }


    //update product in product table
    public Product updateProduct(ProductRequest productRequest ,long categoryId,  long productId) {
        Product existingProduct = productRepository.findById(productId).orElseThrow(
                () -> new RuntimeException("product not found"));
        Category c = categoryRepository.findById(categoryId).orElseThrow(
                () -> new RuntimeException("category not exist"));

        existingProduct.setProductName(productRequest.getProductName());
        existingProduct.setDescription(productRequest.getDescription());
        existingProduct.setBrand(productRequest.getBrand());
        existingProduct.setPrice(productRequest.getPrice());
        existingProduct.setQuantity(productRequest.getQuantity());
        existingProduct.setCategory(c);


        productRepository.save(existingProduct);
        return existingProduct;

    }







    //POST into cartProduct table
    public CartProduct addIntoCart(long userId , long productId ,CartProductRequest cartProductRequest) {

        Optional<User> userOptional = userRepository
                .findById(userId);
        if (userOptional.isPresent()) {

            Optional<Product> productOptional = productRepository
                    .findById(productId);
            if (productOptional.isPresent()) {

                CartProduct cp = new CartProduct();
                Product prod = productOptional.get();

                if (prod.getQuantity() > cartProductRequest.getReqQuantity()) {

//                User user = userOptional.get();
//                Cart cart = new Cart();
//
//                cart.setUser(user);                      iss time pe agar jab cart-product table form ho rahi to tab cart me bhi user entry karne ke liye ye lnes ha
//                cartRepository.save(cart);                otherwisw jab user define karte ho post ke through tab hi cart bhi form kar lo

                    Optional<Cart> cart = cartRepository.findByUserId(userId);
                    if (!cart.isPresent()) {
                        throw new RuntimeException("Cart for the userid " + userId+ "  does not exist.");
                    }

                    cp.setCart(cart.get());
                    cp.setProduct(prod);
                    cp.setPrice(prod.getPrice() * cartProductRequest.getReqQuantity());
                    cp.setReqQuantity(cartProductRequest.getReqQuantity());


                    return cartProductRepository.save(cp);
                }
                else{
                    throw new RuntimeException("Required quantity is not available");
                }
            }
            else{
                throw new RuntimeException("product is not available");
            }
        }
        else{
            throw new RuntimeException("user is not available");
        }

    }


    //get products in cartproduct table
    public List<CartProduct> getAllProductsInCartProduct(){
        return cartProductRepository.findAll() ;

    }

    //get products in cartproduct table
    public List<CartProductResponse> getAllProductInCartProduct(){
        List<CartProduct> cp = cartProductRepository.findAll();
        List<CartProductResponse> cartProductResponse = new ArrayList<>();
        for (CartProduct c1 : cp) {
            CartProductResponse cartProduct= new CartProductResponse();
            cartProduct.setCartProductId(c1.getId());
            cartProduct.setProductId(c1.getProduct().getId());
            cartProduct.setProductName(c1.getProduct().getProductName());
            cartProduct.setCategory(c1.getProduct().getCategory().getCategoryName());
            cartProduct.setCartId(c1.getCart().getId());
            cartProduct.setReqQuantity(c1.getReqQuantity());
            cartProduct.setPrice(c1.getPrice());
            cartProduct.setCreated(c1.getCreated_date());
            cartProduct.setModified(c1.getModified_date());
            cartProductResponse.add(cartProduct);
        }
        return cartProductResponse;

    }


    //get trending products
    public List<TrendResponse> getTrendingProductInCartProduct() {
        List<TrendResponse> trend = cartProductRepository.findTrend();
        List<TrendResponse> trendResponse = new ArrayList<>();
        for (TrendResponse t : trend) {
            TrendResponse trendProduct = new TrendResponse();

            trendProduct.setProductName(t.getProductName());
            trendProduct.setBrand(t.getBrand());

            trendResponse.add(trendProduct);
        }
        return trendResponse;
    }
//        List<TrendResponse> cp = cartProductRepository.findTrendProducts();
//        List<CartProductResponse> cartProductResponse = new ArrayList<>();
//        for (TrendResponse c1 : cp) {
//            CartProductResponse cartProduct= new CartProductResponse();
//
//            cartProduct.setProductName(c1.getProductId());
//            cartProduct.setCategory(c1.getProduc);
//
//            cartProductResponse.add(cartProduct);
//        }
//        return cartProductResponse;





//    public Iterable<Product> findAll(boolean isDeleted){
//        Session session = entityManager.unwrap(Session.class);
//        Filter filter = session.enableFilter("deletedProductFilter");
//        filter.setParameter("isDeleted", isDeleted);
//        Iterable<Product> products =  productRepository.findAll();
//        session.disableFilter("deletedProductFilter");
//        return products;
//    }

    //get preview products for a user
    public List<CartProductResponse> getPreviewProductInCartProduct( long userId){

        List<CartProductResponse> cartProductResponse = new ArrayList<>();

        Optional<Cart> cart= cartRepository
                .findCartByUserId(userId);
        if (!cart.isPresent()) {
            throw new RuntimeException("cart with " + userId + " does not exist.");
        }
        Cart c=cart.get();
        Optional<List<CartProduct>> cp= cartProductRepository
                .findProductsByCartIdWithDeleted(c);
        if (!cp.isPresent()) {
            throw new RuntimeException("product with " + c.getId()+ " does not exist in cart-product table.");
        }
        for (CartProduct c1 : cp.get()) {
            CartProductResponse cartProduct= new CartProductResponse();
            cartProduct.setCartProductId(c1.getId());
            cartProduct.setProductId(c1.getProduct().getId());
            cartProduct.setProductName(c1.getProduct().getProductName());
            cartProduct.setCategory(c1.getProduct().getCategory().getCategoryName());
            cartProduct.setCartId(c1.getCart().getId());
            cartProduct.setReqQuantity(c1.getReqQuantity());
            cartProduct.setPrice(c1.getPrice());

            cartProduct.setCreated(c1.getCreated_date());
            cartProduct.setModified(c1.getModified_date());
            cartProductResponse.add(cartProduct);
        }
        return cartProductResponse;

    }

    //get products by cartId in cartproduct table
    public List<CartProductResponse> getProductsByCartId(long cartId) {
        List<CartProductResponse> cartProductResponse= new ArrayList<>();
        Optional<List<CartProduct>> cp= cartProductRepository
                .findProductsByCartId(cartId);
        if (!cp.isPresent()) {
            throw new RuntimeException("product with " + cartId + " does not exist in cart-product table.");
        }
        for (CartProduct product1 :     cp.get()) {
            CartProductResponse cartprod = new CartProductResponse();
            cartprod.setCartId(product1.getCart().getId());
            cartprod.setProductId(product1.getProduct().getId());
            cartprod.setReqQuantity(product1.getReqQuantity());
            cartprod.setPrice(product1.getPrice());

            cartProductResponse.add(cartprod);
        }
        return cartProductResponse;

    }


    //get products by userId in cartproduct table
    public List<CartProductResponse> getProductsByUserId(long userId) {
        List<CartProductResponse> cartProductResponse= new ArrayList<>();
        Optional<Cart> c= cartRepository
                .findCartByUserId(userId);
         if (!c.isPresent()) {
            throw new RuntimeException("cart with " + userId + " does not exist in cart table.");
        }
           Cart cart= c.get();
           Optional<List<CartProduct>> cp= cartProductRepository
                .findProductsByCartId(cart.getId());
                if (!cp.isPresent()) {
              throw new RuntimeException("product with " + c + " does not exist in cart-product table.");
              }

             for (CartProduct product1 : cp.get()) {
              CartProductResponse cartprod = new CartProductResponse();
              cartprod.setProductId(product1.getProduct().getId());
              cartprod.setCartId(product1.getCart().getId());
              cartprod.setReqQuantity(product1.getReqQuantity());
              cartprod.setPrice(product1.getPrice());

               cartProductResponse.add(cartprod);
           }
           return cartProductResponse;

    }

    //get products by userId in cartproduct table with its cartproductId
    public List<CartProductResponse> getProductByUserId(long userId) {
        List<CartProductResponse> cartProductResponse= new ArrayList<>();
        Optional<Cart> c= cartRepository
                .findCartByUserId(userId);
        if (!c.isPresent()) {
            throw new RuntimeException("cart with " + userId + " does not exist in cart table.");
        }
        Cart cart= c.get();
        Optional<List<CartProduct>> cp= cartProductRepository
                .findProductsByCartId(cart.getId());
        if (!cp.isPresent()) {
            throw new RuntimeException("product with " + c + " does not exist in cart-product table.");
        }

        for (CartProduct product1 : cp.get()) {
            CartProductResponse cartprod = new CartProductResponse();
            cartprod.setCartProductId(product1.getId());
            cartprod.setProductId(product1.getProduct().getId());
            cartprod.setProductName(product1.getProduct().getProductName());
            cartprod.setCartId(product1.getCart().getId());
            cartprod.setReqQuantity(product1.getReqQuantity());
            cartprod.setPrice(product1.getPrice());

            cartProductResponse.add(cartprod);
        }
        return cartProductResponse;

    }

    //get products by userId in cartproduct table with its cartproductId with null orderId
    public List<CartProductResponse> getProductByUserIdWithNullOrderId(long userId) {
        List<CartProductResponse> cartProductResponse= new ArrayList<>();
        Optional<Cart> c= cartRepository
                .findCartByUserId(userId);
        if (!c.isPresent()) {
            throw new RuntimeException("cart with " + userId + " does not exist in cart table.");
        }
        Cart cart= c.get();
        Optional<List<CartProduct>> cp= cartProductRepository
                .findProductsByCartIdWithNullOrderId(cart);
        if (!cp.isPresent()) {
            throw new RuntimeException("product with " + c + " does not exist in cart-product table.");
        }

        for (CartProduct product1 : cp.get()) {
            CartProductResponse cartprod = new CartProductResponse();
            cartprod.setCartProductId(product1.getId());
            cartprod.setProductId(product1.getProduct().getId());
            cartprod.setProductName(product1.getProduct().getProductName());
            cartprod.setCartId(product1.getCart().getId());
            cartprod.setReqQuantity(product1.getReqQuantity());
            cartprod.setPrice(product1.getPrice());
            cartprod.setCreated(product1.getCreated_date());
            cartprod.setModified(product1.getModified_date());

            cartProductResponse.add(cartprod);
        }
        return cartProductResponse;

    }

    //delete products from cartproduct table by cartId
    public ResponseEntity deleteProductsByCartId( long cartId){
        Optional<List<CartProduct>> p= cartProductRepository
                .findProductsByCartId(cartId);
        if (!p.isPresent()) {
            throw new RuntimeException("product with " + cartId + " does not exist in cart-product table.");
        }

        for (CartProduct product1 : p.get()) {
            cartProductRepository.deleteById(product1.getId());
        }
        return ResponseEntity.ok("Success");

    }

    //delete products from carproduct table by cartproductId
    public ResponseEntity deleteProductsByCartProductId( long cartProductId){
        Optional<CartProduct> p= cartProductRepository
                .findProductsById(cartProductId);
        if (!p.isPresent()) {
            throw new RuntimeException("product with " + cartProductId + " does not exist in cart-product table.");
        }
        cartProductRepository.deleteById(cartProductId);
        return ResponseEntity.ok("Success");

    }


    //delete products from carproduct table by userId
    public ResponseEntity deleteProductsByUserId( long userId) {
        Optional<Cart> c= cartRepository
                .findCartByUserId(userId);
        if (!c.isPresent()) {
            throw new RuntimeException("cart with " + userId + " does not exist in cart table.");
        }
           Cart cart= c.get();
           Optional<List<CartProduct>> cp= cartProductRepository
                .findProductsByCartId(cart.getId());
             if (!cp.isPresent()) {
               throw new RuntimeException("products with " + cart.getId() + " does not exist in cart-product table.");
               }
                for (CartProduct product1 : cp.get()) {
                  cartProductRepository.delete(product1);
                }
              return ResponseEntity.ok("Success");

    }

    @Override
    public Page<Product> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return  productRepository.findAll(pageable);

    }

    @Override
    public Page<CartProduct> findCartProductPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return  cartProductRepository.findAll(pageable);

    }




}
