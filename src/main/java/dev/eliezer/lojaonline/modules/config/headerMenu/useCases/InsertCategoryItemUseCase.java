package dev.eliezer.lojaonline.modules.config.headerMenu.useCases;

import dev.eliezer.lojaonline.exceptions.ItemFoundException;
import dev.eliezer.lojaonline.modules.config.headerMenu.dtos.CategoryItemRequestDTO;
import dev.eliezer.lojaonline.modules.config.headerMenu.dtos.CategoryItemResponseDTO;
import dev.eliezer.lojaonline.modules.config.headerMenu.entities.CategoryItemEntity;
import dev.eliezer.lojaonline.modules.config.headerMenu.mappers.HeaderMenuMapper;
import dev.eliezer.lojaonline.modules.config.headerMenu.repositories.CategoryItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InsertCategoryItemUseCase {

    @Autowired
    CategoryItemRepository categoryItemRepository;

    @Autowired
    HeaderMenuMapper headerMenuMapper;

    public List<CategoryItemResponseDTO> execute(CategoryItemRequestDTO request) {
        categoryItemRepository.findById(request.getCategoryId())
                .ifPresent((item ) -> {
                    throw new ItemFoundException("The category item with id " + item.getId());
                });

        CategoryItemEntity categoryItemEntity = categoryItemRepository
                .save(headerMenuMapper
                .toHeaderMenuCategoriesConfigEntity(request));

        List<CategoryItemEntity> categoriesSaved = categoryItemRepository.findAll();

        return headerMenuMapper.toCategoryItemResponseDTO(categoriesSaved);



    }
}
