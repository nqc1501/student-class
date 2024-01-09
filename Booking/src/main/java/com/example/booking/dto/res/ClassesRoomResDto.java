package com.example.booking.dto.res;

import com.example.core.domain.result.domain.Classroom;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassesRoomResDto extends Classroom {
    private OwnerInfo owner_info;
}
