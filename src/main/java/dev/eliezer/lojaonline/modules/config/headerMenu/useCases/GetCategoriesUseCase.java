package dev.eliezer.lojaonline.modules.config.headerMenu.useCases;

import dev.eliezer.lojaonline.modules.config.headerMenu.dtos.CategoryItemResponseDTO;
import dev.eliezer.lojaonline.modules.config.headerMenu.mappers.HeaderMenuMapper;
import dev.eliezer.lojaonline.modules.config.headerMenu.repositories.CategoryItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetCategoriesUseCase {
    @Autowired
    private CategoryItemRepository categoryItemRepository;

    @Autowired
    private HeaderMenuMapper headerMenuMapper;

    public List<CategoryItemResponseDTO> execute (){
        Sort sortByAndDirection = Sort.by(Sort.Direction.ASC,
                "orderMenu");

        List<CategoryItemResponseDTO> categoryItemSaved = headerMenuMapper
                .toCategoryItemResponseDTO(categoryItemRepository.findAll(sortByAndDirection));

        return categoryItemSaved;
    }
}
