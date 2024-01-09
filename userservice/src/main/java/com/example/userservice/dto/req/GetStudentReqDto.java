package com.example.userservice.dto.req;

import com.example.userservice.dto.BaseDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetStudentReqDto extends BaseDto {
    private String email;
    private String name;
}
