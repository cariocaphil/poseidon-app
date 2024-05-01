package com.nnk.springboot.domain;

import jakarta.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import lombok.NoArgsConstructor;

@NoArgsConstructor // This should ensure a no-argument constructor is available
@Entity
@Table(name = "curvepoint")
public class CurvePoint {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NotNull
  @Column(name = "curve_id")
  private Integer curveId;

  @NotNull
  @Column(name = "as_of_date")
  private Timestamp asOfDate;

  @NotNull
  @Column(name = "term")
  private Double term;

  @NotNull
  @Column(name = "value")
  private Double value;

  @Column(name = "creation_date")
  private Timestamp creationDate;

  public CurvePoint(Integer curveId, Double term, Double value) {
    this.curveId = curveId;
    this.term = term;
    this.value = value;
    this.asOfDate = new Timestamp(System.currentTimeMillis()); // Assuming current time for the sake of example
    this.creationDate = new Timestamp(System.currentTimeMillis());
  }
  // standard getters and setters

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getCurveId() {
    return curveId;
  }

  public void setCurveId(Integer curveId) {
    this.curveId = curveId;
  }

  public Timestamp getAsOfDate() {
    return asOfDate;
  }

  public void setAsOfDate(Timestamp asOfDate) {
    this.asOfDate = asOfDate;
  }

  public Double getTerm() {
    return term;
  }

  public void setTerm(Double term) {
    this.term = term;
  }

  public Double getValue() {
    return value;
  }

  public void setValue(Double value) {
    this.value = value;
  }

  public Timestamp getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(Timestamp creationDate) {
    this.creationDate = creationDate;
  }
}
