package dev.eliezer.lojaonline.modules.product.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.eliezer.lojaonline.modules.compositeProduct.dtos.CompositeItemDTO;
import dev.eliezer.lojaonline.modules.image.dtos.ImageLinkDTO;
import dev.eliezer.lojaonline.modules.product.entities.CategoryEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProductResponseDTO {
    private Long id;

    @Schema(example = "SmartWatch X2000", description = "short description of product")
    private String name;


    @Schema(example = " SmartWatch X2000 combina estilo e funcionalidade avançada. " +
            "Com tela HD touchscreen de 1.5 polegadas, monitoramento de saúde em tempo real e conectividade Bluetooth 5.0, " +
            "este relógio inteligente é perfeito para quem busca praticidade no dia a dia. " +
            "Além disso, é resistente à água e possui bateria de longa duração, proporcionando " +
            "uma experiência completa para acompanhar seu estilo de vida ativo.", description = "complete description of product")
    private String description;

    @Schema(example = "laraj-1234",
            description = "sku of product, sku is an alphanumeric code, used to identify the product for stock control, " +
                    "logistics and inventories. ")
    private String sku;

    private BigDecimal price = BigDecimal.valueOf(0.00);

    @Schema(example = "1000", description = "stock quantity of product")
    private Long stock_quantity = 0L;

    @Schema(example = "0.000", description = "weight in KG of product")
    private BigDecimal weight = BigDecimal.valueOf(0.00);

    @Schema(example = "true", description = "product active")
    private Boolean active = true;

    private List<ImageLinkDTO> images = new ArrayList<>();

    @Schema(example = "simple, composite", description = "product type")
    private String productType;

    private List<CompositeItemDTO> compositeItems = new ArrayList<>();

    @JsonIgnore
    private CategoryEntity categoryEntity;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private CategoryDTO category;



    public String getProductType() {
        if (!compositeItems.isEmpty()) {
            return "composite";
        }

        return "simple";
    }



    public CategoryDTO getCategory() {
        if (categoryEntity != null) {
            return CategoryDTO.builder()
                    .id(categoryEntity.getId())
                    .description(categoryEntity.getDescription())
                    .build();
        }
        return null;
    }

    public void setProductType(String productType) {

    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class CategoryDTO {
        private Long id;
        private String description;
    }
}

