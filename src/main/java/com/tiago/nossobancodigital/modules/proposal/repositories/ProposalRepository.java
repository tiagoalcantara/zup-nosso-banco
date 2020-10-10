package com.tiago.nossobancodigital.modules.proposal.repositories;

import com.tiago.nossobancodigital.modules.proposal.models.Proposal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProposalRepository extends JpaRepository<Proposal, String> {
  Proposal findByEmail(String email);
  Proposal findByCpf(String cpf);
}
