package dev.eliezer.lojaonline.modules.image.mappers;

import dev.eliezer.lojaonline.modules.image.dtos.ImageLinkDTO;
import dev.eliezer.lojaonline.modules.image.entities.ImageEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ImageMapper {

    @Value("${system.base-url}")
    private String baseUrl;

    public ImageLinkDTO toImageLinkDTO(ImageEntity imageEntity) {


        return ImageLinkDTO.builder()
                .id(imageEntity.getId())
                .link(baseUrl + "/images/" + imageEntity.getId())
                .build();
    }

}
