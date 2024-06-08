package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.sql.Timestamp;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "trade")
public class Trade {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "trade_id")
    private Integer tradeId;

    @NotBlank(message = "account is mandatory")
    @Column(nullable = false, length = 30)
    private String account;

    @NotBlank(message = "type is mandatory")
    @Column(nullable = false, length = 30)
    private String type;
    @NotNull(message = "buyQuantity is mandatory")
    @Column(name = "buyQuantity")
    private Double buyQuantity;
    private Double sellQuantity;
    private Double buyPrice;
    private Double sellPrice;

    @Column(name = "tradeDate")
    private Timestamp tradeDate;

    private String security;
    private String status;
    private String trader;
    private String benchmark;
    private String book;
    private String creationName;

    @Column(name = "creationDate")
    private Timestamp creationDate;

    private String revisionName;

    @Column(name = "revisionDate")
    private Timestamp revisionDate;

    private String dealName;
    private String dealType;
    private String sourceListId;
    private String side;

    public Trade(String trade_account, String type) {
    }

    public Trade(){
    }

}
