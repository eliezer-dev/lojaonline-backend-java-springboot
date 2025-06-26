package dev.eliezer.lojaonline.modules.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class AuthUserResponseDTO {
    private String access_token;
    private Long expires_in;
}
