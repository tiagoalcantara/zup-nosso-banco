package com.tiago.nossobancodigital.shared.errors;

import org.springframework.http.HttpStatus;

public class AppException extends Exception {
  private static final long serialVersionUID = 509338869456530909L;
  private HttpStatus status;
  
  public AppException(HttpStatus status, String message) {
    super(message);
    this.status = status;
  }

  public HttpStatus getStatus() {
    return this.status;
  }
}
