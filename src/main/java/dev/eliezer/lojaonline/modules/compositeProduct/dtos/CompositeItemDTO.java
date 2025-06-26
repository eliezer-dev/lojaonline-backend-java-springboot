package dev.eliezer.lojaonline.modules.compositeProduct.dtos;

import dev.eliezer.lojaonline.modules.compositeProduct.entities.CompositeProductEntity;
import dev.eliezer.lojaonline.modules.product.repositories.ProductRepository;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompositeItemDTO {

    @NotNull(message = "[id] cannot be null")
    @Schema(example = "1", requiredMode = Schema.RequiredMode.REQUIRED, description = "id of composite item")
    private Long id;

    @NotNull(message = "[Chave de Fenda XPTO] cannot be null")
    @Schema(example = "", requiredMode = Schema.RequiredMode.REQUIRED, description = "id of composite item")
    private String name;

    @NotNull(message = "[price] cannot be null")
    @Schema(example = "1.50", requiredMode = Schema.RequiredMode.REQUIRED, description = "price of composite item")
    private BigDecimal price = BigDecimal.valueOf(0.00);


    @NotNull(message = "[quantity] cannot be null")
    @Schema(example = "1000", requiredMode = Schema.RequiredMode.REQUIRED, description = "quantity of composite item for each sell")
    private Long quantity = 1L;



}
