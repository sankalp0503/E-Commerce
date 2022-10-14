package com.pintoo.ems.Controller;

import com.pintoo.ems.Entity.User;
import com.pintoo.ems.Request.UserRequest;
import com.pintoo.ems.Response.UserResponse;
import com.pintoo.ems.Service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {


    private UserService userService;

    @PostMapping
    public ResponseEntity<User> saveUsers (@Valid @RequestBody UserRequest user) {
    return new ResponseEntity (userService.addUsers( user),HttpStatus.CREATED);

}

    @GetMapping("/all")
    public List<UserResponse> getAllUsers(){
        return userService.getAllUsers();

    }

    @GetMapping
    public List<User> getAllUser(){
        return userService.getAllUser();

    }


    @GetMapping("/{userId}")
    public ResponseEntity<User> getUsersById(@PathVariable long userId){
        return new ResponseEntity(userService.getUsersById(userId),HttpStatus.OK);

    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<User> deleteusersById(@PathVariable long userId) {
        return new ResponseEntity(userService.deleteUsersById(userId),HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUsers(@RequestBody UserRequest user , @PathVariable long id){
        return new ResponseEntity(userService.updateUsers(user,id),HttpStatus.OK);

    }

    @GetMapping("/page/{pageNo}/{pageSize}")
    public Page<User> getPaginated(@PathVariable int pageNo, @PathVariable int pageSize ) {
        return  userService.findPaginated(pageNo,pageSize);
    }
}
