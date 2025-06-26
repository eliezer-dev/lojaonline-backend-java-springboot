package dev.eliezer.lojaonline.modules.user.useCases;


import dev.eliezer.lojaonline.exceptions.NotFoundException;
import dev.eliezer.lojaonline.modules.user.dtos.UpdateUserRequestDTO;
import dev.eliezer.lojaonline.modules.user.dtos.UserResponseDTO;
import dev.eliezer.lojaonline.modules.user.entities.UserEntity;
import dev.eliezer.lojaonline.modules.user.repositories.UserRepository;
import dev.eliezer.lojaonline.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UpdateUserUseCase {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectUtils objectUtils;

    public UserResponseDTO execute (UpdateUserRequestDTO userUpdateData, Long userId) {

        UserEntity userTarget = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(userId));

        if (userUpdateData.getPassword() != null && !userUpdateData.getPassword().isBlank()) {
            var password = passwordEncoder.encode(userUpdateData.getPassword());
            userUpdateData.setPassword(password);
        }

        objectUtils.objectUpdate(userTarget, userUpdateData);

        return UserResponseDTO.parseUserResponseDTO(userRepository.save(userTarget));
    }

}
