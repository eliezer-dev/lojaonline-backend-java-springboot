package dev.eliezer.lojaonline.modules.product.useCases;

import dev.eliezer.lojaonline.modules.product.dtos.ProductCreateRequestDTO;
import dev.eliezer.lojaonline.modules.product.dtos.ProductResponseDTO;
import dev.eliezer.lojaonline.modules.product.entities.ProductEntity;
import dev.eliezer.lojaonline.modules.product.mappers.ProductMapper;
import dev.eliezer.lojaonline.modules.product.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CreateProductUseCase {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    public ProductResponseDTO execute (ProductCreateRequestDTO productCreateRequestDTO) {

        var productEntity = productRepository.save(productMapper.toProductEntity(productCreateRequestDTO));

        return productMapper.toProductResponseDTO(productEntity);

    }
}
