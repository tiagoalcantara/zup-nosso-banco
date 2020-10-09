package com.tiago.nossobancodigital.modules.proposal.models;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Address {
  private String cep;

  private String street;

  private String complement;

  private String neighborhood;

  private String city;

  private String province;
}
