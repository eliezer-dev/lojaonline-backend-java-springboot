package dev.eliezer.lojaonline.modules.product.useCases;

import dev.eliezer.lojaonline.exceptions.BusinessException;
import dev.eliezer.lojaonline.exceptions.NotFoundException;
import dev.eliezer.lojaonline.modules.product.dtos.ProductResponseDTO;
import dev.eliezer.lojaonline.modules.product.entities.ProductEntity;
import dev.eliezer.lojaonline.modules.product.mappers.ProductMapper;
import dev.eliezer.lojaonline.modules.product.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class GetProductUseCase {

    private static final List<String> LIST_FIELDS_ORDER = new ArrayList<>(List.of("id", "name", "sku", "price" ));

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Transactional
    public List<ProductResponseDTO> execute (Long productId, String productName, String productSku, String productType,
                                        Long categoryId, Boolean otherCategories, String fieldOrder, String sortDirection) {

        if (!LIST_FIELDS_ORDER.contains(fieldOrder)) fieldOrder = "id";

        Sort sortByAndDirection = Sort.by(!sortDirection.equalsIgnoreCase("DESC") ? Sort.Direction.ASC
                                          : Sort.Direction.DESC,fieldOrder);


        if (!productId.equals(0L)) {
            var productResponse = productRepository.findById(productId).orElseThrow(() -> new NotFoundException(productId));

            return Collections.singletonList(productMapper.toProductResponseDTO(productResponse));
        }

        if (!productName.isBlank()) {
            var productsList = productRepository.findAllByNameContainingIgnoreCase(productName, sortByAndDirection);
            return toProductResponseDTOS(productsList);
        }

        if (!productSku.isBlank()) {
            var productsList = productRepository.findAllBySku(productSku,sortByAndDirection);
            return toProductResponseDTOS(productsList);
        }
        if (!productType.isBlank()) {
            List<ProductEntity> productsList = new ArrayList<>();
            switch (productType) {
                case "composite":
                    productsList = productRepository.findAllCompositeProducts(sortByAndDirection);
                    return toProductResponseDTOS(productsList);

                case "simple":
                    var productList = productRepository.findAllSimpleProducts(sortByAndDirection);
                    return toProductResponseDTOS(productsList);
                default:
                    throw new BusinessException("product type invalid");
            }
        }
        if (!categoryId.equals(0L)) {
            List<ProductEntity> productsList = new ArrayList<>();
            productsList = productRepository.findAllByCategoryId(categoryId, sortByAndDirection);
            return toProductResponseDTOS(productsList);
        }

        if (otherCategories.equals(true)) {
            List<ProductEntity> productsList = new ArrayList<>();
            productsList = productRepository.findAllProductWithCategoryNotVisibleHome(sortByAndDirection);
            return toProductResponseDTOS(productsList);
        }

        return toProductResponseDTOS(productRepository.findAll(sortByAndDirection));

    }
    private List<ProductResponseDTO> toProductResponseDTOS (List<ProductEntity> productEntityList) {
        List<ProductResponseDTO> productResponseDTOS = new ArrayList<>();
        productEntityList.forEach(product -> {
            productResponseDTOS.add(productMapper.toProductResponseDTO(product));
        });

        return productResponseDTOS;
    }


}
