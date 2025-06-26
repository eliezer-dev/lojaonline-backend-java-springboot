package dev.eliezer.lojaonline.modules.product.dtos;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CategoryWithProductsResponseDTO {
    private String description;

    private Long parentCategoryId;

    private Boolean visibleHome = false;

    private List<ProductResponseDTO> products = new ArrayList<>();
}
