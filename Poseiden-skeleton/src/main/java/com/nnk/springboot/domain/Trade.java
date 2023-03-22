package com.nnk.springboot.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.sql.Timestamp;


@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trade")
public class Trade {
    // TODO: Map columns in data table TRADE with corresponding java fields


    public Trade(String account, String type) {
        this.account = account;
        this.type = type;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tradeId", unique = true, nullable = false)
    Integer tradeId;

    @NotBlank(message = "Account is mandatory")
    @Column(name = "account")
    String account;

    @NotBlank(message = "Type is mandatory")
    @Column(name = "type")
    String type;

    @PositiveOrZero
    @Column(name = "buyQuantity")
    Double buyQuantity;

    @PositiveOrZero
    @Column(name = "sellQuantity")
    Double sellQuantity;

    @PositiveOrZero
    @Column(name = "buyPrice")
    Double buyPrice;

    @PositiveOrZero
    @Column(name = "sellPrice")
    Double sellPrice;

    @Column(name = "benchmark")
    String benchmark;

    @Column(name = "tradeDate")
    Timestamp tradeDate;

    @Column(name = "security")
    String security;

    @Column(name = "status")
    String status;

    @Column(name = "trader")
    String trader;

    @Column(name = "book")
    String book;

    @Column(name = "creationName")
    String creationName;

    @Column(name = "creationDate")
    Timestamp creationDate;

    @Column(name = "revisionName")
    String revisionName;

    @Column(name = "revisionDate")
    Timestamp revisionDate;

    @Column(name = "dealName")
    String dealName;

    @Column(name = "dealType")
    String dealType;

    @Column(name = "sourceListId")
    String sourceListId;

    @Column(name = "side")
    String side;

}
