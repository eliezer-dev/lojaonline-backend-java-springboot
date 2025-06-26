package dev.eliezer.lojaonline.modules.user.useCases;

import dev.eliezer.lojaonline.exceptions.NotFoundException;
import dev.eliezer.lojaonline.modules.image.entities.ImageEntity;
import dev.eliezer.lojaonline.modules.image.repositories.ImageRepository;
import dev.eliezer.lojaonline.modules.user.dtos.UserResponseDTO;
import dev.eliezer.lojaonline.modules.user.entities.UserEntity;
import dev.eliezer.lojaonline.modules.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class UploadUserImageUseCase {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageRepository imageRepository;

    public UserResponseDTO execute (Long id, MultipartFile file) throws IOException {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));

        Optional<ImageEntity> imageFound = Optional.empty();

        if (userEntity.getIdImage() != null) {
            imageFound = imageRepository.findById(userEntity.getIdImage());

        }

        ImageEntity imageSaved = imageRepository.save(
                ImageEntity.builder()
                        .imageType(file.getContentType())
                        .filename(file.getOriginalFilename())
                        .imageData(file.getBytes())
                        .build()
        );
        userEntity.setIdImage(imageSaved.getId());

        UserResponseDTO userUpdated =  UserResponseDTO.parseUserResponseDTO(userRepository.save(userEntity));

        imageFound.ifPresent(imageEntity ->
                imageRepository.delete(imageEntity)

        );

        return userUpdated;

    }
}
