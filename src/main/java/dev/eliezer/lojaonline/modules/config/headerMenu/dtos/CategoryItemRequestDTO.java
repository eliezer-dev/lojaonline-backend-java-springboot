package dev.eliezer.lojaonline.modules.config.headerMenu.dtos;

import lombok.Data;

@Data
public class CategoryItemRequestDTO {

    private Long categoryId;

    private String descriptionMenu;

    private Long orderMenu;

}
