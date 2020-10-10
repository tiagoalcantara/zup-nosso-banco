package com.tiago.nossobancodigital.modules.account.controllers;

import javax.validation.Valid;

import com.tiago.nossobancodigital.modules.account.dtos.CreateAccountDTO;
import com.tiago.nossobancodigital.modules.account.dtos.FirstLoginDTO;
import com.tiago.nossobancodigital.modules.account.dtos.TokenResponseDTO;
import com.tiago.nossobancodigital.modules.account.models.Account;
import com.tiago.nossobancodigital.modules.account.services.CreateAccountService;
import com.tiago.nossobancodigital.modules.account.services.GenerateFirstLoginTokenService;
import com.tiago.nossobancodigital.shared.errors.AppException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("account")
public class AccountController {
  
  private final CreateAccountService createAccount;
  private final GenerateFirstLoginTokenService generateFirstLoginToken;

  public AccountController(
    CreateAccountService createAccount,
    GenerateFirstLoginTokenService generateFirstLoginToken){
    this.createAccount = createAccount;
    this.generateFirstLoginToken = generateFirstLoginToken;
  }

  @PostMapping
  public ResponseEntity<Account> create(@RequestBody @Valid CreateAccountDTO createAccountDTO){
    try {
      Account account = this.createAccount.execute(createAccountDTO.getProposalId());
      return ResponseEntity.status(HttpStatus.CREATED).body(account);
    } catch (AppException e) {
      throw new ResponseStatusException(e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
    }
  }

  @PostMapping("first-login")
  public ResponseEntity<TokenResponseDTO> firstLogin(@RequestBody @Valid FirstLoginDTO firstLoginDTO) {
    try {
      TokenResponseDTO response = generateFirstLoginToken.execute(firstLoginDTO.getEmail(), firstLoginDTO.getCpf());
      return ResponseEntity.ok(response);
    } catch (AppException e) {
      throw new ResponseStatusException(e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
    }
  }
}
