package com.tiago.nossobancodigital.modules.account.repositories;

import com.tiago.nossobancodigital.modules.account.models.Account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, String>{
  Account findByProposalId(String proposalId);
}
