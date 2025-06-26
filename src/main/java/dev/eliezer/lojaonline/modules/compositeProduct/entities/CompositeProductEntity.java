package dev.eliezer.lojaonline.modules.compositeProduct.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import dev.eliezer.lojaonline.modules.compositeProduct.dtos.ProductItemToCompositeProductDTO;
import dev.eliezer.lojaonline.modules.product.entities.ProductEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity(name = "composite_product")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompositeProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(name = "composite_product_id", nullable = false, insertable = false, updatable = false)
    private Long compositeProductId;

    @Column(name = "composite_item_id", nullable = false, insertable = false, updatable = false)
    private Long compositeItemId;


    @Column(nullable = false, columnDefinition = "numeric(1000,2)")
    @NotNull(message = "[price] is not provided")
    private BigDecimal price = BigDecimal.valueOf(0.00);

    @Column(columnDefinition = "bigint default 0")
    @NotNull(message = "[quantity] is not provided")
    private Long quantity = 0L;

    @CreationTimestamp
    @Schema(example = "2024-07-21T22:38:10.514664", requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "creation time of product")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Schema(example = "2024-07-21T22:38:10.514664", requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "update time of product")
    private LocalDateTime updateAt;

}
