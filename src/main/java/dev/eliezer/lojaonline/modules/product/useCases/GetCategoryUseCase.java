package dev.eliezer.lojaonline.modules.product.useCases;

import dev.eliezer.lojaonline.modules.product.dtos.CategoryResponseDTO;
import dev.eliezer.lojaonline.modules.product.entities.CategoryEntity;
import dev.eliezer.lojaonline.modules.product.mappers.CategoryMapper;
import dev.eliezer.lojaonline.modules.product.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class GetCategoryUseCase {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Transactional
    public List<CategoryResponseDTO> execute () {
        List<CategoryResponseDTO> categoryResponseDTOList = new ArrayList<>();

        categoryRepository.findAll().forEach(category -> {
            categoryResponseDTOList.add(categoryMapper.toCategoryToResponseDTO(category));
        });

        return categoryResponseDTOList;
    }

}
