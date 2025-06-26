package dev.eliezer.lojaonline.modules.user.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserRequestDTO {


    @Schema(example = "Jose da Silva", requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "user full name")
    private String fullname;

    @Email(message = "invalid email")
    @Schema(example = "jose@email.com", requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "user email")
    private String email;

    @Schema(example = "senha1234", requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "user password")
    private String password;

    @Schema(example = "true", requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "status of user")
    private Boolean active;

    @Schema(example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED, description =
            """
            0 - user admin.
            1 - client.
            2 - normal user.
            """)
    private Long userRole;

}
