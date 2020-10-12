package com.tiago.nossobancodigital.modules.account.models;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tiago.nossobancodigital.modules.proposal.models.Proposal;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DynamicInsert
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name="UUID", strategy="org.hibernate.id.UUIDGenerator")
  private String id;

  @Column(length = 4)
  private String agency;

  @Column(length = 8)
  private String account;

  @Column(name = "bank_code", columnDefinition = "varchar(3) default '001'")
  private String bankCode;
  
  @Column(columnDefinition = "Decimal(10,2) default 0")
  private BigDecimal balance;

  @JsonIgnore
  private String password;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "proposal_id", referencedColumnName="id")
  private Proposal proposal;
}
