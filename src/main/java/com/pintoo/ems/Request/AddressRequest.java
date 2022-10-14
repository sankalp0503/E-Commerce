package com.pintoo.ems.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequest {


    private String addLine;


    private String city;


    private String postalCode;


    private  String country;

    private boolean defaultAddress;


}
