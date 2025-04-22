package dev.eliezer.lojaonline.modules.client.useCases;

import dev.eliezer.lojaonline.exceptions.EmailFoundException;
import dev.eliezer.lojaonline.modules.client.dtos.CreateClientDTO;
import dev.eliezer.lojaonline.modules.client.dtos.CreateResponseClientDTO;
import dev.eliezer.lojaonline.modules.client.entities.ClientEntity;
import dev.eliezer.lojaonline.modules.client.entities.ClientPhoneEntity;
import dev.eliezer.lojaonline.modules.client.mappers.ClientMapper;
import dev.eliezer.lojaonline.modules.client.mappers.ClientPhoneMapper;
import dev.eliezer.lojaonline.modules.client.repositories.ClientPhoneRepository;
import dev.eliezer.lojaonline.modules.client.repositories.ClientRepository;
import dev.eliezer.lojaonline.modules.shared.entities.UserToken;
import dev.eliezer.lojaonline.providers.JWTUserProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CreateClientUseCase {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientPhoneRepository clientPhoneRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ClientMapper clientMapper;

    @Autowired
    private ClientPhoneMapper clientPhoneMapper;

    @Autowired
    private JWTUserProvider jwtUserProvider;

    private CreateClientDTO request;

    public CreateResponseClientDTO execute(CreateClientDTO request) {

        this.request = request;

        clientRepository.findByEmail(request.getEmail())
                .ifPresent(clientSaved -> {
                    throw new EmailFoundException(clientSaved.getEmail());
                });

        ClientEntity clientSaved = clientRepository.save(clientMapper.toEntity(request));

        Iterable<ClientPhoneEntity> clientPhones = clientPhoneMapper
                .toClientPhoneEntity(request.getPhone(), clientSaved.getId());

        List<ClientPhoneEntity> clientPhoneSaved = clientPhoneRepository.saveAll(clientPhones);
        clientSaved.setPhones(clientPhoneSaved);

        CreateResponseClientDTO response = clientMapper.toResponseClientDTO(clientSaved);

        response.setClientToken(jwtUserProvider.tokenGenerator(response.getId().toString(), Collections.singletonList(1L)));

        return response;


    }


}