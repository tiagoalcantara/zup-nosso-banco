package com.tiago.nossobancodigital.shared.enums;

public enum Step {
  STEP_ONE_COMPLETE(1),
  STEP_TWO_COMPLETE(2),
  STEP_THREE_COMPLETE(3),
  REJECTED_PROPOSAL(4),
  ACCEPTED_PROPOSAL_PENDING(5),
  ACCEPTED_PROPOSAL_APPROVED(6);

  public final Integer value;

  private Step(Integer value) {
    this.value = value;
  }
}
