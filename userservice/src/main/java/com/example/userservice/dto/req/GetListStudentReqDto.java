package com.example.userservice.dto.req;

import com.example.userservice.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetListStudentReqDto extends BaseDto {

    private String name;
    private String code;
    @JsonProperty(value = "class")
    private Integer clazz;
    private Boolean all;
}
