package com.nnk.springboot.dtos;

import lombok.*;

import javax.validation.constraints.PositiveOrZero;
import java.sql.Timestamp;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CurvePointDTO {

    Integer id;
    Integer curveId;
    Timestamp asOfDate;
    Double term;
    @PositiveOrZero
    Double value;
    Timestamp creationDate;
}
