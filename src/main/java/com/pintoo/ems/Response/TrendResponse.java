package com.pintoo.ems.Response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrendResponse {

    private long productId;

    private String productName;

    private String brand;

    private int totalOrdered;

}
