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
public class TradeModel {

    Integer tradeId;
    @NotBlank(message = "Account is mandatory")
    String account;
    @NotBlank(message = "Type is mandatory")
    String type;
    @PositiveOrZero
    Double buyQuantity;
    @PositiveOrZero
    Double sellQuantity;
    @PositiveOrZero
    Double buyPrice;
    @PositiveOrZero
    Double sellPrice;
    String benchmark;
    Timestamp tradeDate;
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
