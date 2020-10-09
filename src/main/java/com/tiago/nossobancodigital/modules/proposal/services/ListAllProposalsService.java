package com.tiago.nossobancodigital.modules.proposal.services;

import java.util.List;

import com.tiago.nossobancodigital.modules.proposal.models.Proposal;
import com.tiago.nossobancodigital.modules.proposal.repositories.ProposalRepository;

import org.springframework.stereotype.Service;

@Service
public class ListAllProposalsService {
  private final ProposalRepository proposalRepository;

  public ListAllProposalsService(ProposalRepository proposalRepository) {
    this.proposalRepository = proposalRepository;
  }

  public List<Proposal> execute(){
    return this.proposalRepository.findAll();
  }
}
