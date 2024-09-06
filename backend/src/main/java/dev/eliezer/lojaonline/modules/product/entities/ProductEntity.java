package dev.eliezer.lojaonline.modules.product.entities;

import dev.eliezer.lojaonline.modules.product.dtos.CreateProductRequestDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Data
@Entity(name = "tb_product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "id of product")
    private Long id;

    @NotBlank
    @Column(nullable = false)
    @Schema(example = "SmartWatch X2000", requiredMode = Schema.RequiredMode.REQUIRED, description = "short description of product")
    private String name;


    @Schema(example = " SmartWatch X2000 combina estilo e funcionalidade avançada. " +
            "Com tela HD touchscreen de 1.5 polegadas, monitoramento de saúde em tempo real e conectividade Bluetooth 5.0, " +
            "este relógio inteligente é perfeito para quem busca praticidade no dia a dia. " +
            "Além disso, é resistente à água e possui bateria de longa duração, proporcionando " +
            "uma experiência completa para acompanhar seu estilo de vida ativo.",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "complete description of product")
    private String description;

    @Schema(example = "laraj-1234", requiredMode = Schema.RequiredMode.REQUIRED,
            description = "sku of product, sku is an alphanumeric code, used to identify the product for stock control, " +
                    "logistics and inventories. ")
    private String sku;

    @Column(nullable = false, columnDefinition = "numeric(1000,2) default '0.00'")
    @NotNull
    private BigDecimal price = BigDecimal.valueOf(0.00);

    @Column(nullable = false, columnDefinition = "bigint default 0")
    @NotNull
    @Schema(example = "1000", requiredMode = Schema.RequiredMode.REQUIRED, description = "stock quantity of product")
    private Long stock_quantity = 0L;

    @Column(nullable = false, columnDefinition = "numeric(1000,3) default '0.000'")
    @Schema(example = "0.000", requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "weight in KG of product")
    private BigDecimal weight = BigDecimal.valueOf(0.00);

    @CreationTimestamp
    @Schema(example = "2024-07-21T22:38:10.514664", requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "creation time of product")
    private LocalDateTime createAt;

    @UpdateTimestamp
    @Schema(example = "2024-07-21T22:38:10.514664", requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "update time of product")
    private LocalDateTime updateAt;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public static ProductEntity parseProductEntity (CreateProductRequestDTO product){
        ProductEntity productEntity = new ProductEntity();
        productEntity.setSku(product.getSku());
        productEntity.setName(product.getName());
        productEntity.setPrice(BigDecimal.valueOf(product.getPrice()));
        productEntity.setDescription(product.getDescription());
        productEntity.setStock_quantity(product.getStock_quantity());
        productEntity.setWeight(BigDecimal.valueOf(product.getWeight()));

        return productEntity;
    }

}
