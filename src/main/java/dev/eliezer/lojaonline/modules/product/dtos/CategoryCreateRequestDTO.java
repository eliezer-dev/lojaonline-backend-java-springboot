package dev.eliezer.lojaonline.modules.product.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryCreateRequestDTO {
    @NotBlank
    @Schema(example = "Hortifruti", requiredMode = Schema.RequiredMode.REQUIRED, description = "description of category")
    private String description;

    @Schema(example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long parentCategoryId;

    private Boolean visibleHome = false;

    private Long orderHomePage;
}
