package com.pintoo.ems.Response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersResponse {

    private long id;

    private String email;

    private Date created;

    private Date modified;
}
