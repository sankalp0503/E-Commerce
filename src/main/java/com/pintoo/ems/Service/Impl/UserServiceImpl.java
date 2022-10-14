package com.pintoo.ems.Service.Impl;

import com.pintoo.ems.Entity.*;
import com.pintoo.ems.Exception.ResourceNotFoundException;
import com.pintoo.ems.Repository.*;
import com.pintoo.ems.Request.UserRequest;
import com.pintoo.ems.Service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import com.pintoo.ems.Response.UserResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {


     private UserRepository userRepository;
     private CartRepository cartRepository;
     private AddressRepository addressRepository;
     private OrderRepository orderRepository;
     private CartProductRepository cartProductRepository;
     private ActivityRepository activityRepository;


    //post
     public ResponseEntity addUsers(UserRequest userRequest)  {
         Optional<User> user1= userRepository.findUserByEmail(userRequest.getEmail());
         if(user1.isPresent()) {
             throw new RuntimeException("user with such email id already exists");
         }
             User user = new User();
             user.setFirstName(userRequest.getFirstName());
             user.setLastName(userRequest.getLastName());
             user.setPhoneNo(userRequest.getPhoneNo());
             user.setEmail(userRequest.getEmail());
             userRepository.save(user);

             Cart cart = new Cart();
             cart.setUser(user);
             cartRepository.save(cart);

//             Activity act =new Activity();
//             act.setUse(user);
//             act.setStatus("User created");
//             activityRepository.save(act);

           return ResponseEntity.ok().body(user);


     }

     //Get all users using UserResponse
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponse> userResponse = new ArrayList<>();
        for (User u : users) {
            UserResponse u2 = new UserResponse();
            u2.setFirstName(u.getFirstName());
            u2.setLastName(u.getLastName());
            u2.setPhoneNo(u.getPhoneNo());
            u2.setEmail(u.getEmail());
            userResponse.add(u2);
        }
        return userResponse;
    }

    //Get all users Using User Entity
    public List<User> getAllUser(){
        return userRepository.findAll() ;

    }

    @Override
    public Page<User> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return userRepository.findAll(pageable);

    }

    //getById
    public User getUsersById(@PathVariable long userId){
         Optional<User> users = userRepository.findById(userId);
         if(users.isPresent()){
             return users.get();
         }else{
             throw new ResourceNotFoundException("Users","Id",userId);
         }
         //
         //return userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Users","Id",id));
    }


    //delete
    public ResponseEntity deleteUsersById(@PathVariable long userId) {
        Optional<User> users = userRepository.findById(userId);
        if (users.isPresent()) {


            Optional<Cart> cartOptional = cartRepository.findByUserId(userId);
            if (cartOptional.isPresent()) {

                Optional<List<Address>> addressOptional = addressRepository.findAddressByUserId(userId);
                if (addressOptional.isPresent()) {


                    Cart cart = cartOptional.get();

                    Optional<List<Orders>> orderOptional = orderRepository.findByUserId(cart.getUser());
                    if(orderOptional.isPresent()){

                        Optional<List<CartProduct>> cartProdOptional = cartProductRepository.findByCartId(cart);
                        if(cartProdOptional.isPresent()) {


                            for(CartProduct cp: cartProdOptional.get()){
                                cartProductRepository.deleteById(cp.getId());
                            }
                            for (Address ads : addressOptional.get()) {
                                addressRepository.deleteById(ads.getId());
                            }

                            for (Orders od : orderOptional.get()) {
                                orderRepository.deleteById(od.getId());
                            }
                            cartRepository.deleteById(cart.getId());
                            userRepository.deleteById(userId);

                        }else{
                            throw new RuntimeException("NO SUCH PRODUCTS FOUND");
                        }
                          }
                      else{
                          throw new RuntimeException("no such order found ");
                      }

                } else {
                    throw new RuntimeException("address for such user dies not exist");
                }
            }
        } else {
            throw new RuntimeException("user is not formed , there is as issue with users ke code me");
        }
        return ResponseEntity.ok("Success");
    }

    public User updateUsers(@RequestBody UserRequest user , @PathVariable  long userId) {
        User existingUsers = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("UserRequest", "Id", userId));

        existingUsers.setFirstName(user.getFirstName());
        existingUsers.setLastName(user.getLastName());
        existingUsers.setPhoneNo(user.getPhoneNo());
        existingUsers.setEmail(user.getEmail());
        //existingUsers.setAddress(user.getAddress());

        userRepository.save(existingUsers);
        return existingUsers;

    }

    }


