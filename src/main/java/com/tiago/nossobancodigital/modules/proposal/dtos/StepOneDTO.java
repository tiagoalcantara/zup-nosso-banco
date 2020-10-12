package com.tiago.nossobancodigital.modules.proposal.dtos;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.tiago.nossobancodigital.modules.proposal.models.Proposal;
import com.tiago.nossobancodigital.shared.enums.Step;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StepOneDTO {
  @NotNull(message = "Missing required field: firstName")
  private String firstName;

  @NotNull(message = "Missing required field: lastName")
  private String lastName;

  @NotNull(message = "Missing required field: email")
  @Email(message = "Invalid e-mail format")
  private String email;

  @NotNull(message = "Missing required field: cpf")
  @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}$", message = "Invalid cpf format")
  private String cpf;

  @NotNull(message = "Missing required field: dateOfBirth")
  private Date dateOfBirth;

  @NotNull(message = "Missing required field: identificationDocument")
  private String identificationDocument;

  public Proposal toEntity() {
    return new Proposal(
      null, 
      firstName, 
      lastName, 
      email, 
      cpf, 
      dateOfBirth, 
      identificationDocument, 
      null, 
      null, 
      null, 
      Step.STEP_ONE_COMPLETE,
      null);
  }
}
