package com.pintoo.ems.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponse {


    private String addLine;


    private String city;


    private String postalCode;


    private  String country;

    private boolean defaultAddress;

    private Date created;

    private Date modified;

}
