package com.example.booking.dto.res;

import com.example.core.domain.result.domain.Room;
import com.example.core.domain.result.domain.Student;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OwnerInfo extends Student {
    private Room student_class;
}
