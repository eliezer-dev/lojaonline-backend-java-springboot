package dev.eliezer.lojaonline.modules.user.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.eliezer.lojaonline.exceptions.BusinessException;
import dev.eliezer.lojaonline.modules.image.entities.ImageEntity;
import dev.eliezer.lojaonline.modules.order.entities.OrderEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "tb_user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "user id")
    private Long id;

    @NotBlank (message = "fullname not provided")
    @Column(nullable = false)
    @Schema(example = "Jose da Silva", requiredMode = Schema.RequiredMode.REQUIRED, description = "user full name")
    private String fullname;

    @NotBlank (message = "email not provided")
    @Column(nullable = false, unique = true)
    @Schema(example = "jose@email.com", requiredMode = Schema.RequiredMode.REQUIRED, description = "user email")
    private String email;



    @NotBlank (message = "password not provided")
    @Column(nullable = false)
    @Schema(example = "senha1234", requiredMode = Schema.RequiredMode.REQUIRED, description = "user password")
    private String password;


    @CreationTimestamp
    @Schema(example = "2024-07-21T22:38:10.514664", requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "user creation datetime")
    private LocalDateTime createAt;

    @UpdateTimestamp
    @Schema(example = "2024-07-21T22:38:10.514664", requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "user update datetime")
    private LocalDateTime updateAt;

    @Column(name = "id_image",unique = true)
    private Long idImage;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_image", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    private ImageEntity imageEntity;

    @Column(name = "user_role", columnDefinition = "bigint default 1")
    @Schema(example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED, description =
            """
            0 - normal user
            1 - user admin.
            """)
    private Long userRole = 1L;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<OrderEntity> orders = new ArrayList<>();

    @JsonIgnore
    @Column(columnDefinition = "boolean default true")
    @Schema(example = "true", description = "user active")
    private Boolean active = true;



    public String getUserRoleDescription () {

        int role = userRole.intValue();

        return switch (role) {
            case 0 -> "userAdmin";
            case 1 -> "client";
            case 2 -> "normalUser";
            default -> throw new BusinessException("User role is invalid");
        };
    }



}
