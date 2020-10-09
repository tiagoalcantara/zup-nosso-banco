package com.tiago.nossobancodigital.modules.proposal.services;

import com.tiago.nossobancodigital.modules.proposal.models.Proposal;
import com.tiago.nossobancodigital.modules.proposal.repositories.ProposalRepository;

import org.springframework.stereotype.Service;

@Service
public class AccomplishStepOneService {
  private final ProposalRepository proposalRepository;

  public AccomplishStepOneService(ProposalRepository proposalRepository) {
    this.proposalRepository = proposalRepository;
  }

  public Proposal execute(Proposal proposal){
    return this.proposalRepository.save(proposal);
  }
}
