package com.ead.authuser.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailResponse {
    private String emailSent;
}
