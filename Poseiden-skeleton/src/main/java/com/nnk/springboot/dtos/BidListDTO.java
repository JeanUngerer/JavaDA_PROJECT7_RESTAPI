package com.nnk.springboot.dtos;

import java.sql.Timestamp;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BidListDTO {

    Integer bidListId;
    @NotBlank(message = "Account is mandatory")
    String account;
    @NotBlank(message = "Type is mandatory")
    String type;
    @PositiveOrZero
    Double bidQuantity;
    @PositiveOrZero
    Double askQuantity;
    @PositiveOrZero
    Double bid;
    @PositiveOrZero
    Double ask;
    String benchmark;
    Timestamp bidListDate;
    String commentary;
    String security;
    String status;
    String trader;
    String book;
    String creationName;
    Timestamp creationDate;
    String revisionName;
    Timestamp revisionDate;
    String dealName;
    String dealType;
    String sourceListId;
    String side;

}
