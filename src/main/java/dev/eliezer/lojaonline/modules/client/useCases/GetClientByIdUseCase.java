package dev.eliezer.lojaonline.modules.client.useCases;

import dev.eliezer.lojaonline.exceptions.EmailFoundException;
import dev.eliezer.lojaonline.exceptions.NotFoundException;
import dev.eliezer.lojaonline.modules.client.dtos.CreateClientDTO;
import dev.eliezer.lojaonline.modules.client.dtos.CreateResponseClientDTO;
import dev.eliezer.lojaonline.modules.client.entities.ClientEntity;
import dev.eliezer.lojaonline.modules.client.mappers.ClientMapper;
import dev.eliezer.lojaonline.modules.client.repositories.ClientRepository;
import dev.eliezer.lojaonline.providers.JWTUserProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class GetClientByIdUseCase {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ClientMapper clientMapper;

    @Autowired
    private JWTUserProvider jwtUserProvider;




    public CreateResponseClientDTO execute(Long clientId) {

        var client = clientRepository.findById(clientId).orElseThrow(() -> new NotFoundException(clientId));


        return clientMapper.toResponseClientDTO(client);


    }
}
