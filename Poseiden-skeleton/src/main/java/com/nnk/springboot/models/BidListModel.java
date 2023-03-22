package com.nnk.springboot.models;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.sql.Timestamp;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BidListModel {

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
