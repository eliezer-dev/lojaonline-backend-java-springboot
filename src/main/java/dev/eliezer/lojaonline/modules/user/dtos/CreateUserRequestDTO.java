package dev.eliezer.lojaonline.modules.user.dtos;

import dev.eliezer.lojaonline.exceptions.BusinessException;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserRequestDTO {

    @NotBlank(message = "fullname not provided")
    @Schema(example = "Jose da Silva", requiredMode = Schema.RequiredMode.REQUIRED, description = "user full name")
    private String fullname;

    @NotBlank (message = "email not provided")
    @Email(message = "invalid email")
    @Schema(example = "jose@email.com", requiredMode = Schema.RequiredMode.REQUIRED, description = "user email")
    private String email;

    @NotBlank (message = "password not provided")
    @Schema(example = "senha1234", requiredMode = Schema.RequiredMode.REQUIRED, description = "user password")
    private String password;

    @Schema(example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED, description =
            """
            0 - user admin.
            1 - client.
            2 - normal user.
            """)
    private Long userRole = 1L;

    public String getUserRoleDescription () {

        int role = userRole.intValue();

        return switch (role) {
            case 0 -> "userAdmin";
            case 1 -> "client";
            case 2 -> "normalUser";
            default -> throw new BusinessException("User role is invalid");
        };
    }


}
