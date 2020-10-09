package com.tiago.nossobancodigital.modules.proposal.services;

import com.tiago.nossobancodigital.modules.proposal.models.Proposal;
import com.tiago.nossobancodigital.modules.proposal.repositories.ProposalRepository;
import com.tiago.nossobancodigital.shared.errors.AppException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ListProposalService {
  private final ProposalRepository proposalRepository;

  public ListProposalService(ProposalRepository proposalRepository) {
    this.proposalRepository = proposalRepository;
  }

  public Proposal execute(String id) throws AppException {
    return this.proposalRepository
      .findById(id)
      .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Could not find a proposal with such id"));
  }
}
