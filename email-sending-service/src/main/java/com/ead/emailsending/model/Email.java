package com.ead.emailsending.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Email {
    private String subject;
    private String sendTo;
    private String sentFrom;
    private String message;
    private String attachment;
}
