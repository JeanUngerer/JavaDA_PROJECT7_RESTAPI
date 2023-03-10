package com.nnk.springboot.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(name = "account")
    String account;

    @Column(name = "type")
    String type;

    @Column(name = "buyQuantity")
    Double buyQuantity;

    @Column(name = "sellQuantity")
    Double sellQuantity;

    @Column(name = "buyPrice")
    Double buyPrice;

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
