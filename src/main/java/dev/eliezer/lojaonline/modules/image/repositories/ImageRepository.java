package dev.eliezer.lojaonline.modules.image.repositories;

import dev.eliezer.lojaonline.modules.image.entities.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Long> {

}
