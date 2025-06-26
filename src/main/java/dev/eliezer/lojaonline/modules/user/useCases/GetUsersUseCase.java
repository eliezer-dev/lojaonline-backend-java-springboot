package dev.eliezer.lojaonline.modules.user.useCases;

import dev.eliezer.lojaonline.exceptions.EmailNotFoundException;
import dev.eliezer.lojaonline.exceptions.NotFoundException;
import dev.eliezer.lojaonline.modules.user.dtos.UserResponseDTO;
import dev.eliezer.lojaonline.modules.user.entities.UserEntity;
import dev.eliezer.lojaonline.modules.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class
GetUsersUseCase {
    @Autowired
    private UserRepository userRepository;

    public List<UserResponseDTO> execute (Long id, String email, String name) {
        List<UserResponseDTO> userResponseDTOs = new ArrayList<>();

        if (id != 0) {
            UserResponseDTO userResponseDTO = UserResponseDTO.parseUserResponseDTO(userRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException(id))
            );
            userResponseDTOs.add(userResponseDTO);
            return userResponseDTOs;
        }

        if (!email.isBlank()) {
            UserResponseDTO userResponseDTO = UserResponseDTO.parseUserResponseDTO(userRepository.findByEmail(email)
                    .orElseThrow(() -> new EmailNotFoundException(email))
            );
            userResponseDTOs.add(userResponseDTO);
            return userResponseDTOs;
        }

        if (!name.isBlank()) {
            List<UserEntity> usersList = userRepository.findByFullnameIgnoreCaseContainingOrderByFullname(name);
            usersList.forEach((user) -> {
                userResponseDTOs.add(UserResponseDTO.parseUserResponseDTO(user));
            });
            return userResponseDTOs;
        }

        userRepository.findAll(Sort.by(Sort.Direction.ASC, "id")).forEach((user) -> {
            userResponseDTOs.add(UserResponseDTO.parseUserResponseDTO(user));
        });
        return userResponseDTOs;
    }

}
