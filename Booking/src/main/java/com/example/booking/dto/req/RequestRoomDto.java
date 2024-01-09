package com.example.booking.dto.req;

import lombok.Data;

@Data
public class RequestRoomDto {

    private String endTime;
    private String startTime;
    private Integer owner;
    private Integer room;
}

