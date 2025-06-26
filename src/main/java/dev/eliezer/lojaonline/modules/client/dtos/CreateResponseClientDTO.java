package dev.eliezer.lojaonline.modules.client.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import dev.eliezer.lojaonline.modules.shared.entities.UserToken;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@JsonPropertyOrder({"id"})

public class CreateResponseClientDTO extends ClientDTO{

    @Schema(example = "1", requiredMode = Schema.RequiredMode.REQUIRED, description = "client id")
    @JsonProperty("id")
    private Long id;

    private UserToken clientToken;
}


