package dev.eliezer.lojaonline.modules.product.useCases;

import dev.eliezer.lojaonline.exceptions.BusinessException;
import dev.eliezer.lojaonline.exceptions.NotFoundException;
import dev.eliezer.lojaonline.modules.image.entities.ImageEntity;
import dev.eliezer.lojaonline.modules.image.repositories.ImageRepository;
import dev.eliezer.lojaonline.modules.image.dtos.ImageLinkDTO;
import dev.eliezer.lojaonline.modules.product.dtos.ProductResponseDTO;
import dev.eliezer.lojaonline.modules.product.entities.ProductEntity;
import dev.eliezer.lojaonline.modules.product.mappers.ProductMapper;
import dev.eliezer.lojaonline.modules.product.repositories.ProductRepository;
import dev.eliezer.lojaonline.utils.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UploadProductImageUseCase {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ProductMapper productMapper;

    public ProductResponseDTO execute (MultipartFile file, Long productId) throws IOException {
        List<ImageLinkDTO> imagesLinkDTO = new ArrayList<>();

        ProductEntity productEntity = productRepository.findById(productId).orElseThrow(() -> new NotFoundException(productId));

        if (productEntity.getImageEntities().size() >= 5) {
            throw new BusinessException("The maximum number of images has been exceeded.");
        }

        ImageEntity imageSaved = imageRepository.save(
                ImageEntity.builder()
                        .imageType(file.getContentType())
                        .filename(file.getOriginalFilename())
                        .imageData(ImageUtil.compressImage(file.getBytes()))
                        .product(productEntity)
                        .build()
        );

        productEntity.getImageEntities().add(imageSaved);

        return productMapper.toProductResponseDTO(productEntity);

    }
}
