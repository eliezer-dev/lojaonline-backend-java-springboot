package dev.eliezer.lojaonline.modules.config.headerMenu.useCases;

import dev.eliezer.lojaonline.exceptions.BusinessException;
import dev.eliezer.lojaonline.exceptions.NotFoundException;
import dev.eliezer.lojaonline.modules.config.headerMenu.entities.CategoryItemEntity;
import dev.eliezer.lojaonline.modules.config.headerMenu.mappers.HeaderMenuMapper;
import dev.eliezer.lojaonline.modules.config.headerMenu.repositories.CategoryItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import dev.eliezer.lojaonline.modules.config.headerMenu.dtos.CategoryItemResponseDTO;
import dev.eliezer.lojaonline.modules.config.headerMenu.dtos.CategoryItemRequestDTO;

import java.util.List;

@Service
public class UpdateCategoryItemUseCase {

    @Autowired
    CategoryItemRepository categoryItemRepository;

    @Autowired
    HeaderMenuMapper headerMenuMapper;

    public List<CategoryItemResponseDTO> execute (Long categoryId, CategoryItemRequestDTO request){

            CategoryItemEntity categoryItemToUpdate = categoryItemRepository.findByCategory_Id(categoryId)
                    .orElseThrow(() -> new NotFoundException(categoryId));

            if (request.getDescriptionMenu() != null && request.getDescriptionMenu().length() > 20) {
                throw new BusinessException("The length of description exceeds 20 characters.");
            }

            categoryItemToUpdate.setDescriptionMenu(request.getDescriptionMenu() != null ? request.getDescriptionMenu() :
                    categoryItemToUpdate.getDescriptionMenu());

            categoryItemToUpdate.setOrderMenu(request.getOrderMenu() != null ? request.getOrderMenu() :
                    categoryItemToUpdate.getOrderMenu());

            categoryItemRepository.save(categoryItemToUpdate);

            List<CategoryItemEntity> CategoriesSaved = categoryItemRepository.findAll();



            return headerMenuMapper.toCategoryItemResponseDTO(CategoriesSaved);
    }


}
