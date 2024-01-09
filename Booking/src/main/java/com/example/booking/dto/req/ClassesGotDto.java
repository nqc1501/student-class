package com.example.booking.dto.req;

import lombok.Data;

@Data
public class ClassesGotDto {
    private String key;
    private int limit;
    private Integer owner;
    private int start;
}
