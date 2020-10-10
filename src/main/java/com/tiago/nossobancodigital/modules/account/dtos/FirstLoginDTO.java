package com.tiago.nossobancodigital.modules.account.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FirstLoginDTO {
  @NotNull(message = "Missing required field: email")
  @Email(message = "Invalid e-mail format")
  private String email;
  
  @NotNull(message = "Missing required field: cpf")
  @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}$", message = "Invalid cpf format")
  private String cpf;
}
