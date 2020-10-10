package com.tiago.nossobancodigital.modules.account.dtos;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountDTO {
  @NotNull(message = "Missing required field: proposalId")
  private String proposalId;
}
