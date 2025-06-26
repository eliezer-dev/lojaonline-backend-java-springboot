package dev.eliezer.lojaonline.modules.client.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.eliezer.lojaonline.modules.client.entities.ClientEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
public class CreateClientDTO extends ClientDTO{

    @NotBlank(message = "password not provided")
    @jakarta.validation.constraints.Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).{8,}$",
            message = "password must be at least 8 characters long and contain both letters and numbers")
    @Schema(example = "Senha1234@@", requiredMode = Schema.RequiredMode.REQUIRED, description = "client password")
    private String password;


}
