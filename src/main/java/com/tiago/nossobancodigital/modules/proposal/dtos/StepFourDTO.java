package com.tiago.nossobancodigital.modules.proposal.dtos;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StepFourDTO {
  @NotNull
  private boolean accepted;
}
