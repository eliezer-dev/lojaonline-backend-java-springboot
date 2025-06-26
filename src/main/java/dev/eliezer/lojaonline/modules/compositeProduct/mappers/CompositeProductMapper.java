package dev.eliezer.lojaonline.modules.compositeProduct.mappers;

import dev.eliezer.lojaonline.modules.compositeProduct.dtos.CompositeItemDTO;
import dev.eliezer.lojaonline.modules.compositeProduct.entities.CompositeProductEntity;

public class CompositeProductMapper {

    public static CompositeItemDTO toCompositeItemDTO (CompositeProductEntity compositeProductEntity, String compositeItemName) {

        CompositeItemDTO compositeItemDTO = CompositeItemDTO.builder()
                .id(compositeProductEntity.getCompositeItemId())
                .name(compositeItemName)
                .price(compositeProductEntity.getPrice())
                .quantity(compositeProductEntity.getQuantity())
                .build();

        return compositeItemDTO;
    }
}
