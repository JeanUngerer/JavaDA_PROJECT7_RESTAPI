package com.nnk.springboot.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bidlist")
public class BidList {
    // TODO: Map columns in data table BIDLIST with corresponding java fields


    public BidList(String account, String type, Double bidQuantity) {
        this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bidListId", unique = true, nullable = false)
    Integer bidListId;

    @NotBlank(message = "Account is mandatory")
    @Column(name = "account")
    String account;

    @NotBlank(message = "Type is mandatory")
    @Column(name = "type")
    String type;

    @PositiveOrZero
    @Column(name = "bidQuantity")
    Double bidQuantity;

    @PositiveOrZero
    @Column(name = "askQuantity")
    Double askQuantity;

    @PositiveOrZero
    @Column(name = "bid")
    Double bid;

    @PositiveOrZero
    @Column(name = "ask")
    Double ask;

    @Column(name = "benchmark")
    String benchmark;

    @Column(name = "bidListDate")
    Timestamp bidListDate;

    @Column(name = "commentary")
    String commentary;

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
