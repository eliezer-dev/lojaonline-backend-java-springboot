package dev.eliezer.lojaonline.modules.config.headerMenu.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import dev.eliezer.lojaonline.modules.config.headerMenu.entities.CategoryItemEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryItemRepository extends JpaRepository<CategoryItemEntity, Long> {

    Optional<CategoryItemEntity> findByCategory_Id(Long categoryId);

}
