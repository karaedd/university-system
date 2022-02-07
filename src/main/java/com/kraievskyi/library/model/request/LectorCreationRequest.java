package com.kraievskyi.library.model.request;

import lombok.Data;

@Data
public class LectorCreationRequest {
    private String name;
    private String degree;
    private int salary;
}
