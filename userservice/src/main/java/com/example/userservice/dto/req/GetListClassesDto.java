package com.example.userservice.dto.req;

import com.example.userservice.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetListClassesDto extends BaseDto {
    private String key;
}
