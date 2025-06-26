package dev.eliezer.lojaonline.modules.config.headerMenu.mappers;

import dev.eliezer.lojaonline.exceptions.BusinessException;
import dev.eliezer.lojaonline.exceptions.NotFoundException;
import dev.eliezer.lojaonline.modules.config.headerMenu.dtos.CategoryItemRequestDTO;
import dev.eliezer.lojaonline.modules.config.headerMenu.dtos.CategoryItemResponseDTO;
import dev.eliezer.lojaonline.modules.config.headerMenu.entities.CategoryItemEntity;
import dev.eliezer.lojaonline.modules.product.entities.CategoryEntity;
import dev.eliezer.lojaonline.modules.product.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HeaderMenuMapper {

    @Autowired
    private CategoryRepository categoryRepository;


    public CategoryItemEntity toHeaderMenuCategoriesConfigEntity (CategoryItemRequestDTO categoryItemRequestDTO) {

        CategoryItemEntity categoryItemEntity = new CategoryItemEntity();

        CategoryEntity category = categoryRepository.findById(categoryItemRequestDTO.getCategoryId())
                .orElseThrow(() -> new NotFoundException(categoryItemRequestDTO.getCategoryId()));

        categoryItemEntity.setCategory(category);

        if (categoryItemRequestDTO.getDescriptionMenu() != null && categoryItemRequestDTO.getDescriptionMenu().length() > 20) {
            throw new BusinessException("The length of description exceeds 20 characters.");
        }

        categoryItemEntity.setDescriptionMenu(categoryItemRequestDTO.getDescriptionMenu());

        categoryItemEntity.setOrderMenu(categoryItemRequestDTO.getOrderMenu());


        return categoryItemEntity;
    }

    public CategoryItemResponseDTO toCategoryItemResponseDTO (CategoryItemEntity categoryItemEntity) {
        CategoryItemResponseDTO categoryItemResponseDTO = new CategoryItemResponseDTO();

        categoryItemResponseDTO.setCategoryId(categoryItemEntity.getId());
        categoryItemResponseDTO.setDescriptionMenu(
                categoryItemEntity.getDescriptionMenu() != null ?
                        categoryItemEntity.getDescriptionMenu() :
                        categoryItemEntity.getCategory().getDescription()
        );

        categoryItemResponseDTO.setCategoryDescription(categoryItemEntity.getCategory().getDescription());

        categoryItemResponseDTO.setOrderMenu(categoryItemEntity.getOrderMenu());

        return categoryItemResponseDTO;
    }

    public List<CategoryItemResponseDTO> toCategoryItemResponseDTO (List<CategoryItemEntity> categoryItemEntityList) {

        List<CategoryItemResponseDTO> categoryItemResponseDTOList = new ArrayList<>();

        categoryItemEntityList.forEach(categoryItemEntity -> {

            CategoryItemResponseDTO categoryItemResponseDTO = toCategoryItemResponseDTO(categoryItemEntity);

            categoryItemResponseDTOList.add(categoryItemResponseDTO);


        });

        return categoryItemResponseDTOList;
    }
}
