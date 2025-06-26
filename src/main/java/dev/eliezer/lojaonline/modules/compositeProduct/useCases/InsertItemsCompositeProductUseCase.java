package dev.eliezer.lojaonline.modules.compositeProduct.useCases;

import dev.eliezer.lojaonline.exceptions.BusinessException;
import dev.eliezer.lojaonline.exceptions.NotFoundException;
import dev.eliezer.lojaonline.modules.compositeProduct.dtos.ProductItemToCompositeProductDTO;
import dev.eliezer.lojaonline.modules.compositeProduct.entities.CompositeProductEntity;
import dev.eliezer.lojaonline.modules.compositeProduct.repositories.CompositeProductRepository;
import dev.eliezer.lojaonline.modules.product.entities.ProductEntity;
import dev.eliezer.lojaonline.modules.product.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InsertItemsCompositeProductUseCase {
    @Autowired
    private CompositeProductRepository compositeProductRepository;

    @Autowired
    private ProductRepository productRepository;

    public ProductEntity execute (ProductItemToCompositeProductDTO compositeProductItemRequest, Long idCompositeProduct) {

        ProductEntity compositeproductFound = productRepository.findById(idCompositeProduct)
                .orElseThrow(() -> new NotFoundException(idCompositeProduct));



/*        ProductEntity compositeProductItemFound = productRepository.findById(compositeProductItemRequest.getId())
                .orElseThrow(() -> new NotFoundException(compositeProductItemRequest.getId()));


        compositeProductRepository.findByCompositeProduct_IdAndItemProduct_Id(idCompositeProduct, compositeProductItemRequest.getId())
                .ifPresent(
                       item -> {
                            throw new BusinessException("Composite item with id " + compositeProductItemRequest.getId() + " is alright exists.");
                       });


        compositeproductFound.getCompositeItems()
                .add(compositeProductRepository.save(CompositeProductEntity
                        .parseCompositeProduct(compositeproductFound, compositeProductItemFound, compositeProductItemRequest)));*/

        return compositeproductFound;

    }

}
