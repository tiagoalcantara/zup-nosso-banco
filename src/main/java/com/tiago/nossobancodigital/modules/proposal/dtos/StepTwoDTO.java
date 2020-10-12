package com.tiago.nossobancodigital.modules.proposal.dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.tiago.nossobancodigital.modules.proposal.models.Address;
import com.tiago.nossobancodigital.modules.proposal.models.Proposal;
import com.tiago.nossobancodigital.shared.enums.Step;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StepTwoDTO {
  @NotNull(message = "Missing required field: cep")
  @Pattern(regexp = "^\\d{5}\\-\\d{3}$", message = "Invalid cep format")
  private String cep;

  @NotNull(message = "Missing required field: street")
  private String street;

  @NotNull(message = "Missing required field: neighborhood")
  private String neighborhood;

  private String complement;

  @NotNull(message = "Missing required field: city")
  private String city;

  @NotNull(message = "Missing required field: province")
  private String province;

  public Proposal toEntity(){
    return new Proposal(
      id, 
      null, 
      null, 
      null, 
      null, 
      null, 
      null, 
      new Address(cep, street, neighborhood, complement, city, province), 
      null, 
      null, 
      Step.STEP_TWO_COMPLETE,
      null);
  }
}
