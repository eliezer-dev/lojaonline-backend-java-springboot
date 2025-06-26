package dev.eliezer.lojaonline.modules.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthUserRequestDTO {
    @NotBlank(message = "email is not provided")
    private String email;
    @NotBlank(message = "password is not provided")
    private String password;

}
