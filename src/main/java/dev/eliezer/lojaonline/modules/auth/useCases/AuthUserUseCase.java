package dev.eliezer.lojaonline.modules.auth.useCases;

import dev.eliezer.lojaonline.exceptions.UsernameNotFoundException;
import dev.eliezer.lojaonline.modules.auth.dto.AuthUserRequestDTO;
import dev.eliezer.lojaonline.modules.client.repositories.ClientRepository;
import dev.eliezer.lojaonline.modules.shared.entities.UserToken;
import dev.eliezer.lojaonline.modules.user.repositories.UserRepository;
import dev.eliezer.lojaonline.providers.JWTUserProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthUserUseCase{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUserProvider jwtUserProvider;

    public UserToken execute (AuthUserRequestDTO userAuth, Long userRole) {

        if (userRole == 3) {
            return generateClientToken(userAuth);
        }

        return generateUserToken(userAuth);
    }


    private UserToken generateUserToken(AuthUserRequestDTO userAuth) {

        var user = userRepository.findByEmail(userAuth.getEmail())
                .orElseThrow(() -> {
                            throw new UsernameNotFoundException();
                        }

                );

        var passwordMatches = passwordEncoder.matches(userAuth.getPassword(), user.getPassword());

        if (!passwordMatches) {
            throw new UsernameNotFoundException();
        }

        var userToken = jwtUserProvider.tokenGenerator(user.getId().toString(), Collections.singletonList(user.getUserRole()));


        return userToken;
    }

    private UserToken generateClientToken(AuthUserRequestDTO userAuth) {

        var client = clientRepository.findByEmail(userAuth.getEmail())
                .orElseThrow(() -> {
                            throw new UsernameNotFoundException();
                        }

                );

        var passwordMatches = passwordEncoder.matches(userAuth.getPassword(), client.getPassword());

        if (!passwordMatches) {
            throw new UsernameNotFoundException();
        }

        var userToken = jwtUserProvider.tokenGenerator(client.getId().toString(), Collections.singletonList(0L));

        return userToken;
    }

}

