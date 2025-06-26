package dev.eliezer.lojaonline.modules.product.dtos;

import dev.eliezer.lojaonline.modules.product.entities.CategoryEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CategoryResponseDTO {

    private Long id;

    private String description;

    private Long parentCategoryId;

    private Boolean visibleHome = false;

    private Long orderHomePage;

}
