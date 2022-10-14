package com.pintoo.ems.Response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private String firstName;


    private String lastName;


    private String phoneNo;


    private String email;
}
