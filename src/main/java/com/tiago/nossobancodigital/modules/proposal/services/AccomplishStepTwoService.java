package com.tiago.nossobancodigital.modules.proposal.services;

import com.tiago.nossobancodigital.modules.proposal.models.Proposal;
import com.tiago.nossobancodigital.modules.proposal.repositories.ProposalRepository;
import com.tiago.nossobancodigital.shared.enums.Step;
import com.tiago.nossobancodigital.shared.errors.AppException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AccomplishStepTwoService {
  private final ProposalRepository proposalRepository;

  public AccomplishStepTwoService(ProposalRepository proposalRepository) {
    this.proposalRepository = proposalRepository;
  }

  public Proposal execute(Proposal proposal) throws Exception {
    Proposal findProposal = this.proposalRepository
      .findById(proposal.getId())
      .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Could not find a proposal with such id"));

    if(findProposal.getCurrentStep() != Step.STEP_ONE_COMPLETE) {
      throw new AppException(HttpStatus.UNPROCESSABLE_ENTITY, "Ineligible for completing this step");
    }

    findProposal.setAddress(proposal.getAddress());
    findProposal.setCurrentStep(proposal.getCurrentStep());

    return this.proposalRepository.save(findProposal);
  }
}
