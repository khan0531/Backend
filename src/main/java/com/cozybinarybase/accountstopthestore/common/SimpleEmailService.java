package com.cozybinarybase.accountstopthestore.common;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;

@Service
public class SimpleEmailService {

  private final SesClient sesClient;

  public SimpleEmailService() {
    this.sesClient = SesClient.builder()
        .region(Region.of("us-east-2"))
        .build();
  }

  public void sendEmail(String to, String subject, String body) {
    SendEmailRequest request = SendEmailRequest.builder()
        .destination(Destination.builder()
            .toAddresses(to)
            .build())
        .message(Message.builder()
            .subject(Content.builder()
                .data(subject)
                .charset("UTF-8")
                .build())
            .body(Body.builder()
                .text(Content.builder()
                    .data(body)
                    .charset("UTF-8")
                    .build())
                .build())
            .build())
        .source("lim7897@gmail.com")
        .build();

    sesClient.sendEmail(request);
  }
}