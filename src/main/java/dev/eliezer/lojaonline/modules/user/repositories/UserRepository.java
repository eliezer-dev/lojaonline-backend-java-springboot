package dev.eliezer.lojaonline.modules.user.repositories;

import dev.eliezer.lojaonline.modules.user.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail (String email);
    List<UserEntity> findByFullnameIgnoreCaseContainingOrderByFullname (String name);

//    @Query("select new dev.eliezer.lojaonline.modules.user.dto.UserResponseDTO(u.id, u.fullname, u.email, u.updateAt, u.createAt) from tb_user u")
//    List<UserResponseDTO> findAllUsers();
}
