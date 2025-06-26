package dev.eliezer.lojaonline.modules.compositeProduct.useCases;

import dev.eliezer.lojaonline.exceptions.BusinessException;
import dev.eliezer.lojaonline.exceptions.NotFoundException;
import dev.eliezer.lojaonline.modules.compositeProduct.dtos.CompositeProductUpdateDTO;
import dev.eliezer.lojaonline.modules.compositeProduct.entities.CompositeProductEntity;
import dev.eliezer.lojaonline.modules.compositeProduct.repositories.CompositeProductRepository;
import dev.eliezer.lojaonline.modules.product.entities.ProductEntity;
import dev.eliezer.lojaonline.modules.product.repositories.ProductRepository;
import dev.eliezer.lojaonline.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateCompositeProductUseCase {

    @Autowired
    CompositeProductRepository compositeProductRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ObjectUtils objectUtils;


    public CompositeProductEntity execute (Long compositeProductId, CompositeProductUpdateDTO CompositeProductSource) {

        CompositeProductEntity compositeProductTarget = compositeProductRepository
                .findByCompositeProductIdAndCompositeItemId(compositeProductId, CompositeProductSource.getCompositeItemId())
                .orElseThrow((() -> new BusinessException("composite item is not found.")));

        CompositeProductEntity compositeProductEntitySource = parseCompositeProductEntity(compositeProductId, CompositeProductSource);

        objectUtils.objectUpdate(compositeProductTarget, compositeProductEntitySource);

        return compositeProductRepository.save(compositeProductTarget);
    }

    private CompositeProductEntity parseCompositeProductEntity(Long compositeProductId, CompositeProductUpdateDTO compositeProductUpdateDTO) {

        ProductEntity compositeProductItemFound = productRepository.findById(compositeProductUpdateDTO.getCompositeItemId())
                .orElseThrow(() -> new NotFoundException(compositeProductUpdateDTO.getCompositeItemId()));

        CompositeProductEntity compositeProductEntity = CompositeProductEntity.builder()
                .compositeProductId(compositeProductItemFound.getId())
                .price(compositeProductUpdateDTO.getPrice())
                .quantity(compositeProductUpdateDTO.getQuantity())
                .build();

        return compositeProductEntity;
    }


}
