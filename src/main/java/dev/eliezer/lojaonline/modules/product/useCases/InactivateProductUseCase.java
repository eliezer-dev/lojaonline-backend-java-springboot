package dev.eliezer.lojaonline.modules.product.useCases;

import dev.eliezer.lojaonline.exceptions.NotFoundException;
import dev.eliezer.lojaonline.modules.product.entities.ProductEntity;
import dev.eliezer.lojaonline.modules.product.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class InactivateProductUseCase {
    @Autowired
    private ProductRepository productRepository;

    public ProductEntity execute (Long id) {

        ProductEntity productToDelete = productRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        productToDelete.setActive(false);

        return productRepository.save(productToDelete);

    }
}
