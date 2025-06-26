package dev.eliezer.lojaonline.modules.compositeProduct.repositories;

import dev.eliezer.lojaonline.modules.compositeProduct.entities.CompositeProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompositeProductRepository extends JpaRepository<CompositeProductEntity,Long> {
    List<CompositeProductEntity> findAllByCompositeProductId (Long id);
    Optional<CompositeProductEntity> findByCompositeProductIdAndCompositeItemId(Long compositeProductId, Long compositeItemId);
}
