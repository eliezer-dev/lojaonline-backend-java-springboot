package dev.eliezer.lojaonline.modules.product.useCases;

import dev.eliezer.lojaonline.exceptions.NotFoundException;
import dev.eliezer.lojaonline.modules.product.dtos.ProductResponseDTO;
import dev.eliezer.lojaonline.modules.product.dtos.ProductUpdateRequestDTO;
import dev.eliezer.lojaonline.modules.product.entities.CategoryEntity;
import dev.eliezer.lojaonline.modules.product.entities.ProductEntity;
import dev.eliezer.lojaonline.modules.product.mappers.ProductMapper;
import dev.eliezer.lojaonline.modules.product.repositories.CategoryRepository;
import dev.eliezer.lojaonline.modules.product.repositories.ProductRepository;
import dev.eliezer.lojaonline.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateProductUseCase {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ObjectUtils objectUtils;

    @Autowired
    private ProductMapper productMapper;


    public ProductResponseDTO execute(Long id, ProductUpdateRequestDTO productUpdateRequestDTO) {

        ProductEntity productToUpdate = productRepository.findById(id).orElseThrow(() -> new NotFoundException(id));


        if (productUpdateRequestDTO.getCategoryId() != null) {
            CategoryEntity categoryEntity = categoryRepository.findById(productUpdateRequestDTO.getCategoryId())
                    .orElseThrow(() -> new NotFoundException(productUpdateRequestDTO.getCategoryId()));
            productToUpdate.setCategory(categoryEntity);
        }

        objectUtils.objectUpdate(productToUpdate, productUpdateRequestDTO);

            return productMapper.toProductResponseDTO(productRepository.save(productToUpdate));


    }
}
