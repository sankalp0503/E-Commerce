package com.pintoo.ems.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {



    private String categoryName;

    private  String description;
}
