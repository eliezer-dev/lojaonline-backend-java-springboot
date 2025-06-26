package dev.eliezer.lojaonline.modules.compositeProduct.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductItemToCompositeProductDTO {
    @NotNull(message = "[price] cannot be null")
    @Schema(example = "1", requiredMode = Schema.RequiredMode.REQUIRED, description = "id of product item")
    private Long id;

    @NotNull(message = "[price] cannot be null")
    @Schema(example = "1.50", requiredMode = Schema.RequiredMode.REQUIRED, description = "price of product item")
    private BigDecimal price = BigDecimal.valueOf(0.00);

    @Column(nullable = false, columnDefinition = "bigint default 1")
    @NotNull(message = "[quantity] cannot be null")
    @Schema(example = "1000", requiredMode = Schema.RequiredMode.REQUIRED, description = "quantity of product item in the composite product")
    private Long quantity = 1L;

}
