package dev.eliezer.lojaonline.modules.compositeProduct.useCases;

import dev.eliezer.lojaonline.exceptions.BusinessException;
import dev.eliezer.lojaonline.modules.compositeProduct.entities.CompositeProductEntity;
import dev.eliezer.lojaonline.modules.compositeProduct.repositories.CompositeProductRepository;
import dev.eliezer.lojaonline.modules.product.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RemoveItemsCompositeProductUseCase {
    @Autowired
    CompositeProductRepository compositeProductRepository;

    @Autowired
    ProductRepository productRepository;

    public String execute (Long compositeProductId, Long compositeItemId) {
        CompositeProductEntity compositeProductToRemove = compositeProductRepository.findByCompositeProductIdAndCompositeItemId(compositeProductId, compositeItemId)
                .orElseThrow(() -> new BusinessException("Composite Product is not found"));


        compositeProductRepository.delete(compositeProductToRemove);

/*        String response = "The composite item " + compositeProductToRemove.getCompositeProductName() +
                " of composite product" + compositeProductToRemove.getCompositeItemName() +
                " is successfully deleted.";*/

        return "successfully";
    }
}
