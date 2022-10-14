package com.pintoo.ems.Request;

import com.pintoo.ems.Entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartRequest {


    private List<Product> product;
}


