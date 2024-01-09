package com.example.userservice.dto.req;

import com.example.core.domain.result.domain.Classroom;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrAddNewClass extends Classroom {
    private String hinhAnh;
}
