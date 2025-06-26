package dev.eliezer.lojaonline.modules.user.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.eliezer.lojaonline.modules.image.dtos.ImageLinkDTO;
import dev.eliezer.lojaonline.modules.user.entities.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class UserResponseDTO {
    @Schema(example = "1", description = "user id")
    private Long id;

    @Schema(example = "Jose da Silva", description = "user full name")
    private String fullname;

    @Schema(example = "jose@email.com", description = "user email")
    private String email;

    @Schema(example = "1", description = "user image id")
    @JsonIgnore
    private Long idImage;

    @Schema(example = "2024-07-21T22:38:10.514664", description = "user creation datetime")
    private LocalDateTime createAt;

    @Schema(example = "2024-07-21T22:38:10.514664", description = "user update datetime")
    private LocalDateTime updateAt;

    private ImageLinkDTO image;

    @Schema(example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED, description =
            """
            userAdmin,
            client,
            normalUser.
            """)
    private String userRoleDescription;

    @Schema(example = "true", description = "user active")
    private Boolean active;

    public Object getImage() {
        if (idImage != null) {
            ImageLinkDTO image = ImageLinkDTO.builder().id(idImage).build();
            return image;
        }
        return null;
    }

    public void setImage(ImageLinkDTO image) {

    }

    public static UserResponseDTO parseUserResponseDTO (UserEntity userEntity) {

        return UserResponseDTO.builder()
                .fullname(userEntity.getFullname())
                .id(userEntity.getId())
                .email(userEntity.getEmail())
                .createAt(userEntity.getCreateAt())
                .updateAt(userEntity.getUpdateAt())
                .active(userEntity.getActive())
                .idImage(userEntity.getIdImage())
                .userRoleDescription(userEntity.getUserRoleDescription())
                .build();
    }

    }
