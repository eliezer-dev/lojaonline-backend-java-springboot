package dev.eliezer.lojaonline.modules.config.headerMenu.dtos;

import lombok.Data;

@Data
public class CategoryItemResponseDTO {

    private Long categoryId;

    private String categoryDescription;

    private String descriptionMenu;

    private Long orderMenu;


}
