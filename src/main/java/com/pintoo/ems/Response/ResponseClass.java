package com.pintoo.ems.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ResponseClass<T>{
    private List<T> list;
    private String cursor;
    private Integer totalPages;
}
