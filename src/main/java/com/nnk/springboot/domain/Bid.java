package com.nnk.springboot.domain;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "bidlist")
public class Bid {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer bidListId;

  private String account;

  private String type;

  @Column(name = "bid_quantity")
  private Double bidQuantity;

  @Column(name = "ask_quantity")
  private Double askQuantity;

  private Double bid;

  private Double ask;

  private String benchmark;

  @Column(name = "bid_list_date")
  private Timestamp bidListDate;

  private String commentary;

  private String security;

  private String status;

  private String trader;

  private String book;

  @Column(name = "creation_name")
  private String creationName;

  @Column(name = "creation_date")
  private Timestamp creationDate;

  @Column(name = "revision_name")
  private String revisionName;

  @Column(name = "revision_date")
  private Timestamp revisionDate;

  @Column(name = "deal_name")
  private String dealName;

  @Column(name = "deal_type")
  private String dealType;

  @Column(name = "source_list_id")
  private String sourceListId;

  private String side;

}
