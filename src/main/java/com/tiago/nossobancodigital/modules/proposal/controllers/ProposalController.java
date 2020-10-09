package com.tiago.nossobancodigital.modules.proposal.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import com.tiago.nossobancodigital.modules.proposal.dtos.StepFourDTO;
import com.tiago.nossobancodigital.modules.proposal.dtos.StepOneDTO;
import com.tiago.nossobancodigital.modules.proposal.dtos.StepTwoDTO;
import com.tiago.nossobancodigital.modules.proposal.models.Proposal;
import com.tiago.nossobancodigital.modules.proposal.services.CreateProposalWithPersonalInfoService;
import com.tiago.nossobancodigital.modules.proposal.services.AcceptOrRefuseProposalService;
import com.tiago.nossobancodigital.modules.proposal.services.AddAddressInfoToProposalService;
import com.tiago.nossobancodigital.modules.proposal.services.ListAllProposalsService;
import com.tiago.nossobancodigital.modules.proposal.services.ListProposalService;
import com.tiago.nossobancodigital.modules.proposal.services.UploadDocumentImagesService;
import com.tiago.nossobancodigital.shared.errors.AppException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
  private final CreateProposalWithPersonalInfoService createProposalWithPersonalInfo;
  private final AddAddressInfoToProposalService addAddressInfoToProposal;
  private final UploadDocumentImagesService uploadDocumentImages;
  private final ListProposalService listProposal;
  private final AcceptOrRefuseProposalService acceptOrRefuseProposal;
  
  public ProposalController(
    CreateProposalWithPersonalInfoService createProposalWithPersonalInfo,
    ListAllProposalsService listAllProposals,
    AddAddressInfoToProposalService addAddressInfoToProposal,
    UploadDocumentImagesService uploadDocumentImages,
    ListProposalService listProposal,
    AcceptOrRefuseProposalService acceptOrRefuseProposal
    ) {
    this.createProposalWithPersonalInfo = createProposalWithPersonalInfo;
    this.listAllProposals = listAllProposals;
    this.addAddressInfoToProposal = addAddressInfoToProposal;
    this.uploadDocumentImages = uploadDocumentImages;
    this.listProposal = listProposal;
    this.acceptOrRefuseProposal = acceptOrRefuseProposal;
  }

  @GetMapping
  public List<Proposal> index() {
    try {
      return this.listAllProposals.execute();
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<Proposal> show(@PathVariable String id){
    try {
      Proposal proposal = listProposal.execute(id);

      return ResponseEntity.ok().body(proposal);
    } catch (AppException e) {
      throw new ResponseStatusException(e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
    }
  }

  @PostMapping("step-one")
  public ResponseEntity<Proposal> stepOne(@RequestBody @Valid StepOneDTO proposalAsDto) {
   try {
    Proposal proposalAsEntity = proposalAsDto.toEntity();
    Proposal proposal = createProposalWithPersonalInfo.execute(proposalAsEntity);

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
      Proposal proposal = addAddressInfoToProposal.execute(proposalAsEntity);

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

  @PostMapping("step-four")
  public void stepFour(@RequestBody @Valid StepFourDTO request, @RequestParam String id){
    try {
      System.out.println(request.isAccepted());
      acceptOrRefuseProposal.execute(id, request.isAccepted());
    } catch (AppException e) {
      throw new ResponseStatusException(e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
    }
  }
}
