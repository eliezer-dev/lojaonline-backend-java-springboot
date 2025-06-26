package dev.eliezer.lojaonline.modules.product.mappers;

import dev.eliezer.lojaonline.exceptions.NotFoundException;
import dev.eliezer.lojaonline.modules.compositeProduct.dtos.CompositeItemDTO;
import dev.eliezer.lojaonline.modules.compositeProduct.mappers.CompositeProductMapper;
import dev.eliezer.lojaonline.modules.image.dtos.ImageLinkDTO;
import dev.eliezer.lojaonline.modules.image.mappers.ImageMapper;
import dev.eliezer.lojaonline.modules.product.dtos.ProductCreateRequestDTO;
import dev.eliezer.lojaonline.modules.product.dtos.ProductResponseDTO;
import dev.eliezer.lojaonline.modules.product.entities.CategoryEntity;
import dev.eliezer.lojaonline.modules.product.entities.ProductEntity;
import dev.eliezer.lojaonline.modules.product.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Component
public class ProductMapper {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ImageMapper imageMapper;


    public ProductEntity toProductEntity (ProductCreateRequestDTO productCreateRequestDTO){
        ProductEntity productEntity = new ProductEntity();
        productEntity.setSku(productCreateRequestDTO.getSku());
        productEntity.setName(productCreateRequestDTO.getName());
        productEntity.setPrice(productCreateRequestDTO.getPrice());
        productEntity.setDescription(productCreateRequestDTO.getDescription());
        productEntity.setStock_quantity(productCreateRequestDTO.getStock_quantity());
        productEntity.setWeight(productCreateRequestDTO.getWeight() != null ? productCreateRequestDTO.getWeight() : BigDecimal.valueOf(0));

        if (productCreateRequestDTO.getCategoryId() != null) {
            CategoryEntity categoryEntity = categoryRepository
                    .findById(productCreateRequestDTO.getCategoryId())
                    .orElseThrow(() -> new NotFoundException(productCreateRequestDTO.getCategoryId()));
            productEntity.setCategory(categoryEntity);
        }

        return productEntity;
    }

    public ProductResponseDTO toProductResponseDTO (ProductEntity productEntity) {

        ProductResponseDTO productResponseDTO = new ProductResponseDTO();

        productResponseDTO.setId(productEntity.getId());

        productResponseDTO.setName(productEntity.getName());

        productResponseDTO.setSku(productEntity.getSku());

        productResponseDTO.setDescription(productEntity.getDescription());

        productResponseDTO.setPrice(productEntity.getPrice());

        productResponseDTO.setStock_quantity(productEntity.getStock_quantity());

        productResponseDTO.setWeight(productEntity.getWeight());

        productResponseDTO.setActive(productEntity.getActive());

        productResponseDTO.setCategoryEntity(productEntity.getCategory());

        if (!productEntity.getImageEntities().isEmpty()) {
            List<ImageLinkDTO> imageLinkDTOList = new ArrayList<>();
            productEntity.getImageEntities().forEach(imageEntity -> {
                imageLinkDTOList.add(imageMapper.toImageLinkDTO(imageEntity));
            });
            productResponseDTO.setImages(imageLinkDTOList);
        }

        if(!productEntity.getCompositeProductEntities().isEmpty()) {
            List<CompositeItemDTO> compositeItemsDTOs= new ArrayList<>();
            productEntity.getCompositeProductEntities().forEach(compositeProductEntity -> {
                compositeItemsDTOs.add(CompositeProductMapper.toCompositeItemDTO(compositeProductEntity, productEntity.getName()));
            });
            productResponseDTO.setCompositeItems(compositeItemsDTOs);
        }

        return productResponseDTO;

    }

    public List<ProductResponseDTO> toProductResponseDTOS (List<ProductEntity> productEntityList) {
        List<ProductResponseDTO> productResponseDTOS = new ArrayList<>();
        productEntityList.forEach(product -> {
            productResponseDTOS.add(toProductResponseDTO(product));
        });

        return productResponseDTOS;
    }

}
