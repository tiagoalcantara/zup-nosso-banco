package com.tiago.nossobancodigital.shared.providers.token.models;

import com.tiago.nossobancodigital.shared.errors.AppException;

public interface ITokenManagerProvider {
  public String generate(String subject, long expTime);
  public String getSub(String token) throws AppException;
}
