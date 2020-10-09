package com.tiago.nossobancodigital.modules.proposal.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tiago.nossobancodigital.shared.enums.Step;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Proposal {
  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name="UUID", strategy="org.hibernate.id.UUIDGenerator")
  private String id;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(unique = true)
  private String email;

  @Column(unique = true)
  private String cpf;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "date_of_birth")
  private Date dateOfBirth;

  @Column(name = "identification_document")
  private String identificationDocument;

  @Embedded
  private Address address;

  @Column(name = "document_front")
  private String documentFront;

  @Column(name = "document_back")
  private String documentBack;

  @Column(name = "current_step", nullable=false)
  private Step currentStep = Step.STEP_ONE_COMPLETE;
}
