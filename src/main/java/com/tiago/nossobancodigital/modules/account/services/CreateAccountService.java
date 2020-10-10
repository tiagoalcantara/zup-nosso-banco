package com.tiago.nossobancodigital.modules.account.services;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import com.tiago.nossobancodigital.modules.account.models.Account;
import com.tiago.nossobancodigital.modules.account.repositories.AccountRepository;
import com.tiago.nossobancodigital.modules.account.utils.generators.AccountGenerator;
import com.tiago.nossobancodigital.modules.account.utils.generators.AgencyGenerator;
import com.tiago.nossobancodigital.modules.proposal.models.Proposal;
import com.tiago.nossobancodigital.modules.proposal.repositories.ProposalRepository;
import com.tiago.nossobancodigital.shared.enums.Step;
import com.tiago.nossobancodigital.shared.errors.AppException;
import com.tiago.nossobancodigital.shared.providers.mail.models.IMailProvider;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CreateAccountService {
  private final AccountRepository accountRepository;
  private final IMailProvider mailProvider;
  private final ProposalRepository proposalRepository;


  public CreateAccountService(
    AccountRepository accountRepository, 
    IMailProvider mailProvider,
    ProposalRepository proposalRepository) {
    this.accountRepository = accountRepository;
    this.mailProvider = mailProvider;
    this.proposalRepository = proposalRepository;
  }

  @Transactional
  public Account execute(String proposalId) throws AppException {
    Proposal findProposal = this.proposalRepository
      .findById(proposalId)
      .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Could not find a proposal with such id"));
  
    if(findProposal.getCurrentStep() != Step.ACCEPTED_PROPOSAL_PENDING){
      throw new AppException(HttpStatus.UNPROCESSABLE_ENTITY, "Ineligible for completing this step");
    }

    findProposal.setCurrentStep(Step.ACCEPTED_PROPOSAL_APPROVED);
    Account newAccount = new Account(
      null,
      AgencyGenerator.generate(),
      AccountGenerator.generate(),
      "123",
      BigDecimal.valueOf(0),
      null,
      findProposal
    );

    this.proposalRepository.save(findProposal);
    Account createdAccount = this.accountRepository.save(newAccount);

    this.mailProvider.send(
        "teste@testando.com.br", 
        "[Nosso Banco] Conta criada", 
        String.format(
          "Sua nova conta chegou!\nAgência: %s\nConta: %s\nCódigo do Banco: %s\n\nAproveite!",
          createdAccount.getAgency(),
          createdAccount.getAccount(),
          createdAccount.getBankCode()
      ));

    return createdAccount;
  }
}
