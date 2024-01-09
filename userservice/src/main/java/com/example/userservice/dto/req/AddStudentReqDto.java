package com.example.userservice.dto.req;

import com.example.core.domain.result.domain.Student;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddStudentReqDto extends Student {
    private String hinhAnh;
}
