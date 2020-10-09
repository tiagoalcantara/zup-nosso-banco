package com.tiago.nossobancodigital.modules.proposal.services;

import com.tiago.nossobancodigital.modules.proposal.models.Proposal;
import com.tiago.nossobancodigital.modules.proposal.repositories.ProposalRepository;
import com.tiago.nossobancodigital.shared.enums.Step;
import com.tiago.nossobancodigital.shared.errors.AppException;
import com.tiago.nossobancodigital.shared.providers.storage.models.IStorageProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadDocumentImagesService {
  private final ProposalRepository proposalRepository;
  private final IStorageProvider storageProvider;

  @Autowired
  public UploadDocumentImagesService(ProposalRepository proposalRepository, IStorageProvider storageProvider) {
    this.proposalRepository = proposalRepository;
    this.storageProvider = storageProvider;
  }

  public Proposal execute(
    String id, 
    MultipartFile documentFront, 
    MultipartFile documentBack) throws AppException{
    Proposal findProposal = this.proposalRepository
      .findById(id)
      .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Could not find a proposal with such id"));

    if(findProposal.getCurrentStep() != Step.STEP_TWO_COMPLETE) {
      throw new AppException(HttpStatus.UNPROCESSABLE_ENTITY, "Ineligible for completing this step");
    }

    if(documentFront == null || documentBack == null) {
      throw new AppException(HttpStatus.BAD_REQUEST, "Missing files");
    }

    if(documentFront.isEmpty() || documentBack.isEmpty()) {
      throw new AppException(HttpStatus.BAD_REQUEST, "Missing files");
    }

    String documentFrontFilePath = this.storageProvider.save(documentFront);
    String documentBackFilePath = this.storageProvider.save(documentBack);

    findProposal.setDocumentFront(documentFrontFilePath);
    findProposal.setDocumentBack(documentBackFilePath);
    findProposal.setCurrentStep(Step.STEP_THREE_COMPLETE);

    return this.proposalRepository.save(findProposal);
  }
}
