package com.pintoo.ems.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Cursor {

    private Integer pageNumber;

    private Integer pageSize;
}
