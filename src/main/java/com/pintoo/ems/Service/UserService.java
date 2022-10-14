package com.pintoo.ems.Service;

import com.pintoo.ems.Entity.User;
import com.pintoo.ems.Request.UserRequest;
import com.pintoo.ems.Response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    ResponseEntity addUsers( UserRequest userRequest);
    List<UserResponse> getAllUsers();
    User getUsersById(long id);
    ResponseEntity deleteUsersById(long userId);
    User updateUsers(UserRequest user , long userId);
    List<User> getAllUser();
    Page<User> findPaginated(int pageNo , int pageSize);
}
