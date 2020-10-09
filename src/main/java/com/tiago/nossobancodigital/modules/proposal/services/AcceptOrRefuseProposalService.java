package com.tiago.nossobancodigital.modules.proposal.services;

import com.tiago.nossobancodigital.modules.proposal.models.Proposal;
import com.tiago.nossobancodigital.modules.proposal.repositories.ProposalRepository;
import com.tiago.nossobancodigital.shared.enums.Step;
import com.tiago.nossobancodigital.shared.errors.AppException;
import com.tiago.nossobancodigital.shared.providers.mail.models.IMailProvider;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AcceptOrRefuseProposalService {
  private final ProposalRepository proposalRepository;
  private final IMailProvider mailProvider;

  public AcceptOrRefuseProposalService(
    ProposalRepository proposalRepository, 
    IMailProvider mailProvider) {
    this.proposalRepository = proposalRepository;
    this.mailProvider = mailProvider;
  }

  public void execute(String id, boolean accept) throws AppException {
    Proposal findProposal = this.proposalRepository
      .findById(id)
      .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Could not find a proposal with such id"));

    if(findProposal.getCurrentStep() != Step.STEP_THREE_COMPLETE) {
      throw new AppException(HttpStatus.UNPROCESSABLE_ENTITY, "Ineligible for completing this step");
    }

    System.out.println(accept);

    if(accept){
      findProposal.setCurrentStep(Step.ACCEPTED_PROPOSAL_PENDING);
      mailProvider.send(
        "teste@testando.com.br", 
        "[Nosso Banco] Confirmação", 
        "Obrigado por acreditar no Nosso Banco! :)\n" +
        "Sua conta será gerada e você receberá um e-mail com os dados de acesso em alguns minutos, aguarde.");
    } else {
      findProposal.setCurrentStep(Step.REJECTED_PROPOSAL);
      mailProvider.send(
        "teste@testando.com.br", 
        "[Nosso Banco] Confirmação", 
        "Vi aqui que você recusou a proposta, tem algo que posso fazer por você?");
    }

    this.proposalRepository.save(findProposal);
  }
}
