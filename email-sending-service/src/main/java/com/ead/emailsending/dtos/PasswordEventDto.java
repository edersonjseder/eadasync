package com.ead.emailsending.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PasswordEventDto {
    private String username;
    private String email;
    private String fullName;
    private String message;
    private String actionType;
}
