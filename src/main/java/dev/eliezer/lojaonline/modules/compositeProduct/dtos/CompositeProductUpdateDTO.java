package dev.eliezer.lojaonline.modules.compositeProduct.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.eliezer.lojaonline.modules.compositeProduct.entities.CompositeProductEntity;
import dev.eliezer.lojaonline.modules.product.entities.ProductEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompositeProductUpdateDTO {

    @NotNull(message = "id of composite item is not provided")
    @Schema(example = "9", requiredMode = Schema.RequiredMode.REQUIRED, description = "id of composite item")
    private Long compositeItemId;

    @Schema(example = "1.50", requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "price of composite item")
    private BigDecimal price;

    @Column(columnDefinition = "bigint default 0")
    @Schema(example = "10", requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "quantity of composite item for each sell")
    private Long quantity;

    @CreationTimestamp
    @Schema(example = "2024-07-21T22:38:10.514664", requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "creation time of composite item")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Schema(example = "2024-07-21T22:38:10.514664", requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "update time of composite item")
    private LocalDateTime updateAt;

}


