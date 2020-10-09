package com.tiago.nossobancodigital.modules.proposal.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import com.tiago.nossobancodigital.modules.proposal.dtos.StepOneDTO;
import com.tiago.nossobancodigital.modules.proposal.dtos.StepTwoDTO;
import com.tiago.nossobancodigital.modules.proposal.models.Proposal;
import com.tiago.nossobancodigital.modules.proposal.services.AccomplishStepOneService;
import com.tiago.nossobancodigital.modules.proposal.services.AccomplishStepTwoService;
import com.tiago.nossobancodigital.modules.proposal.services.ListAllProposalsService;
import com.tiago.nossobancodigital.modules.proposal.services.UploadDocumentImagesService;
import com.tiago.nossobancodigital.shared.errors.AppException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("proposal")
public class ProposalController {

  private final ListAllProposalsService listAllProposals;
  private final AccomplishStepOneService accomplishStepOne;
  private final AccomplishStepTwoService accomplishStepTwo;
  private final UploadDocumentImagesService uploadDocumentImages;
  
  public ProposalController(
    AccomplishStepOneService accomplishStepOne,
    ListAllProposalsService listAllProposals,
    AccomplishStepTwoService accomplishStepTwo,
    UploadDocumentImagesService uploadDocumentImages
    ) {
    this.accomplishStepOne = accomplishStepOne;
    this.listAllProposals = listAllProposals;
    this.accomplishStepTwo = accomplishStepTwo;
    this.uploadDocumentImages = uploadDocumentImages;
  }

  @GetMapping
  public List<Proposal> index() {
    try {
      return this.listAllProposals.execute();
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
    }
  }

  @PostMapping("step-one")
  public ResponseEntity<Proposal> stepOne(@RequestBody @Valid StepOneDTO proposalAsDto) {
   try {
    Proposal proposalAsEntity = proposalAsDto.toEntity();
    Proposal proposal = accomplishStepOne.execute(proposalAsEntity);

    URI location = new URI(String.format("http://localhost:3000/cadastro/segunda-etapa?proposal=%s", proposal.getId()));
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setLocation(location);

    return new ResponseEntity<Proposal>(proposal, responseHeaders, HttpStatus.CREATED);
   } catch (Exception e) {
     throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
   }
  }

  @PostMapping("step-two")
  public ResponseEntity<Proposal> stepTwo(@RequestBody @Valid StepTwoDTO proposalAsDTO) {
    try {
      Proposal proposalAsEntity = proposalAsDTO.toEntity();
      Proposal proposal = accomplishStepTwo.execute(proposalAsEntity);

      URI location = new URI(String.format("http://localhost:3000/cadastro/terceira-etapa?proposal=%s", proposal.getId()));
      HttpHeaders responseHeaders = new HttpHeaders();
      responseHeaders.setLocation(location);

      return new ResponseEntity<Proposal>(proposal, responseHeaders, HttpStatus.OK);
    } catch (AppException e) {
      throw new ResponseStatusException(e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
    }
  }

  @PostMapping("step-three")
  public ResponseEntity<Proposal> stepThree(
    @RequestParam String id,
    @RequestBody MultipartFile documentFront, 
    @RequestBody MultipartFile documentBack) {
      try {
        Proposal proposal = uploadDocumentImages.execute(id, documentFront, documentBack);
  
        URI location = new URI(String.format("http://localhost:3000/cadastro/quarta-etapa?proposal=%s", proposal.getId()));
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(location);
  
        return new ResponseEntity<Proposal>(proposal, responseHeaders, HttpStatus.OK);
      } catch (AppException e) {
        throw new ResponseStatusException(e.getStatus(), e.getMessage(), e);
      } catch (Exception e) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
      }
  }
}
