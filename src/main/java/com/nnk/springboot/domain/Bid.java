package com.nnk.springboot.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "bidlist")
public class Bid {
    // TODO: Map columns in data table BIDLIST with corresponding java fields

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
}
