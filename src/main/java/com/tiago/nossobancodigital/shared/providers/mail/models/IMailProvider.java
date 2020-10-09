package com.tiago.nossobancodigital.shared.providers.mail.models;

public interface IMailProvider {
  void send(String to, String subject, String text);
}
