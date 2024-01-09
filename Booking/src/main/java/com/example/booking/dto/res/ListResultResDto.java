package com.example.booking.dto.res;

import com.example.core.domain.result.Result;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListResultResDto extends Result<Object> {
    private int total;
}
