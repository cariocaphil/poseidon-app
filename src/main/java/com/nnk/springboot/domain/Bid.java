package com.nnk.springboot.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "bidlist")
public class Bid {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NotBlank(message = "Account is mandatory")
  private String account;

  @NotBlank(message = "Type is mandatory")
  private String type;

  @NotNull(message = "Bid quantity is mandatory")
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
