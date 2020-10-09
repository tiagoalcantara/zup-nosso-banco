package com.tiago.nossobancodigital.shared.providers.mail.implementations;

import com.tiago.nossobancodigital.shared.providers.mail.models.IMailProvider;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailtrapMailProvider implements IMailProvider {
  
  private JavaMailSender mailSender;

  public MailtrapMailProvider(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  public void send(String to, String subject, String text) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom("tiagofacampos@gmail.com");
    message.setTo(to);
    message.setSubject(subject);
    message.setText(text);

    mailSender.send(message);
  }
  
}
