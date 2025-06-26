package dev.eliezer.lojaonline.modules.product.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductUpdateRequestDTO {


    @Schema(example = "SmartWatch X2000", requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "short description of product")
    private String name;


    @Schema(example = " SmartWatch X2000 combina estilo e funcionalidade avançada. " +
            "Com tela HD touchscreen de 1.5 polegadas, monitoramento de saúde em tempo real e conectividade Bluetooth 5.0, " +
            "este relógio inteligente é perfeito para quem busca praticidade no dia a dia. " +
            "Além disso, é resistente à água e possui bateria de longa duração, proporcionando " +
            "uma experiência completa para acompanhar seu estilo de vida ativo.",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "complete description of product")
    private String description;

    @Schema(example = "laraj-1234", requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            description = "sku of product, sku is an alphanumeric code, used to identify the product for stock control, " +
                    "logistics and inventories. ")
    private String sku;

    @Schema(example = "12.00", requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "price of product")
    private BigDecimal price;

    @Schema(example = "1000", requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "stock quantity of product")
    private Long stock_quantity;

    @Column(nullable = false, columnDefinition = "numeric(1000,3) default '0.00'")
    @Schema(example = "0.00", requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "weight in KG of product")
    private BigDecimal weight;

    @Schema(example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "ID of the product category")
    private Long categoryId;

}
