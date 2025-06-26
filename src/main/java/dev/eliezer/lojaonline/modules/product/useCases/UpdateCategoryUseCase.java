package dev.eliezer.lojaonline.modules.product.useCases;

import dev.eliezer.lojaonline.exceptions.NotFoundException;
import dev.eliezer.lojaonline.modules.product.dtos.CategoryCreateRequestDTO;
import dev.eliezer.lojaonline.modules.product.dtos.CategoryResponseDTO;
import dev.eliezer.lojaonline.modules.product.dtos.CategoryUpdateRequestDTO;
import dev.eliezer.lojaonline.modules.product.entities.CategoryEntity;
import dev.eliezer.lojaonline.modules.product.mappers.CategoryMapper;
import dev.eliezer.lojaonline.modules.product.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateCategoryUseCase {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    public CategoryResponseDTO execute (Long categoryId, CategoryUpdateRequestDTO categoryUpdateRequestDTO) {

        CategoryEntity parentCategory = null;

        var categoryToUpdate = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException(categoryId));


        if (categoryUpdateRequestDTO.getParentCategoryId() != null) {
            parentCategory =  categoryRepository
                    .findById(categoryUpdateRequestDTO.getParentCategoryId())
                    .orElseThrow(() -> new NotFoundException(categoryUpdateRequestDTO.getParentCategoryId()));
        }


        categoryToUpdate.setDescription(categoryUpdateRequestDTO.getDescription() != null &&
                !categoryUpdateRequestDTO.getDescription().isBlank() ?
                categoryUpdateRequestDTO.getDescription() : categoryToUpdate.getDescription());

        categoryToUpdate.setVisibleHome(categoryUpdateRequestDTO.getVisibleHome() != null ?
                categoryUpdateRequestDTO.getVisibleHome() : categoryToUpdate.getVisibleHome());

        categoryToUpdate.setParentCategory(parentCategory != null ?
               parentCategory : categoryToUpdate.getParentCategory());

        categoryToUpdate.setOrderHomePage(categoryUpdateRequestDTO.getOrderHomePage() != null ?
                categoryUpdateRequestDTO.getOrderHomePage() : categoryToUpdate.getOrderHomePage());

        //regra para sempre que a categoria for removida da home, é apagado a ordenação.
        if (categoryToUpdate.getVisibleHome() == false) {
            categoryToUpdate.setOrderHomePage(null);
        }

        return categoryMapper.toCategoryToResponseDTO(categoryRepository.save(categoryToUpdate));


    }
}
