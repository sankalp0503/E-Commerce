package com.pintoo.ems.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {


    private String firstName;


    private String lastName;


    private String phoneNo;


    private String email;

//    @CreatedDate
//    private LocalDateTime created;
//
//    @LastModifiedDate
//    private LocalDateTime updated;


   // private Address address;
    //not taking address as as input
}
