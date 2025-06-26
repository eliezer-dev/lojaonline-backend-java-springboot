package dev.eliezer.lojaonline.modules.product.useCases;


import dev.eliezer.lojaonline.modules.product.dtos.CategoryWithProductsResponseDTO;
import dev.eliezer.lojaonline.modules.product.entities.CategoryEntity;
import dev.eliezer.lojaonline.modules.product.entities.ProductEntity;
import dev.eliezer.lojaonline.modules.product.mappers.CategoryMapper;
import dev.eliezer.lojaonline.modules.product.mappers.ProductMapper;
import dev.eliezer.lojaonline.modules.product.repositories.CategoryRepository;
import dev.eliezer.lojaonline.modules.product.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetCategoryWithProductsUseCase {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryMapper categoryMapper;


    @Transactional
    public List<CategoryWithProductsResponseDTO> execute (){

        Sort sortByAndDirectionProducts = Sort.by(Sort.Direction.ASC,"name");
        Sort sortByAndDirectionCategories = Sort.by(Sort.Direction.ASC,"orderHomePage");

        List<CategoryEntity> categoryEntityList = new ArrayList<>();

        List<CategoryWithProductsResponseDTO> categoriesWithProductsResponseDTOs = new ArrayList<>();

        categoryEntityList = categoryRepository.findByVisibleHome(true, sortByAndDirectionCategories);

        if (!categoryEntityList.isEmpty()){
            categoriesWithProductsResponseDTOs = categoryMapper.toCategoryWithProductsToResponseDTOs(categoryEntityList);
        }

        List<ProductEntity> productWithCategoryNotVisibleHome = productRepository
                .findAllProductWithCategoryNotVisibleHome(sortByAndDirectionProducts);

        if (!productWithCategoryNotVisibleHome.isEmpty()){
            CategoryWithProductsResponseDTO categoryWithOtherProducts = new CategoryWithProductsResponseDTO();

            categoryWithOtherProducts.setDescription("Outros Produtos S/Glutém e S/Lactose");

            categoryWithOtherProducts
                    .setProducts(productMapper.toProductResponseDTOS(productWithCategoryNotVisibleHome));
            categoriesWithProductsResponseDTOs.add(categoryWithOtherProducts);
        }


        return categoriesWithProductsResponseDTOs;


    }

}
