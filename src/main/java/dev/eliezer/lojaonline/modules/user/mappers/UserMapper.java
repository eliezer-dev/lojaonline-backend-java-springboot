package dev.eliezer.lojaonline.modules.user.mappers;

import dev.eliezer.lojaonline.modules.user.dtos.CreateUserRequestDTO;
import dev.eliezer.lojaonline.modules.user.entities.UserEntity;
import lombok.Data;

@Data
public class UserMapper {
    public static UserEntity parseUserEntity (CreateUserRequestDTO createUserRequestDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(createUserRequestDTO.getEmail());
        userEntity.setPassword(createUserRequestDTO.getPassword());
        userEntity.setFullname(createUserRequestDTO.getFullname());
        return userEntity;
    }

}
