package com.tiago.nossobancodigital.modules.account.services;

import javax.transaction.Transactional;

import com.tiago.nossobancodigital.modules.account.dtos.TokenResponseDTO;
import com.tiago.nossobancodigital.modules.account.models.Account;
import com.tiago.nossobancodigital.modules.account.repositories.AccountRepository;
import com.tiago.nossobancodigital.modules.proposal.models.Proposal;
import com.tiago.nossobancodigital.modules.proposal.repositories.ProposalRepository;
import com.tiago.nossobancodigital.shared.errors.AppException;
import com.tiago.nossobancodigital.shared.providers.token.models.ITokenManagerProvider;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class GenerateFirstLoginTokenService {
  private final ProposalRepository proposalRepository;
  private final AccountRepository accountRepository;
  private final ITokenManagerProvider tokenManager;

  public GenerateFirstLoginTokenService(
    ProposalRepository proposalRepository,
    AccountRepository accountRepository,
    ITokenManagerProvider tokenManager
  ){
    this.proposalRepository = proposalRepository;
    this.accountRepository = accountRepository;
    this.tokenManager = tokenManager;
  }

  @Transactional
  public TokenResponseDTO execute(String cpf, String email) throws AppException {
    Proposal proposal = this.proposalRepository.findByCpf(cpf);
    System.out.println("*******" + proposal);

    if(proposal == null) {
      throw new AppException(HttpStatus.BAD_REQUEST, "Incorrect credentials");
    }

    if(!proposal.getEmail().equals(email)) {
      throw new AppException(HttpStatus.BAD_REQUEST, "Incorrect credentials");
    }

    Account account = this.accountRepository.findByProposalId(proposal.getCpf());
    
    if(account == null){
      throw new AppException(HttpStatus.BAD_REQUEST, "Incorrect credentials");
    }

    if(account.getPassword() != null) {
      throw new AppException(HttpStatus.BAD_REQUEST, "Account already has a password");
    }

    String token = tokenManager.generate(account.getId(), 60000);

    return new TokenResponseDTO(token);
  }
}
