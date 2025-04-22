package dev.eliezer.lojaonline.modules.client.mappers;

import dev.eliezer.lojaonline.exceptions.NotFoundException;
import dev.eliezer.lojaonline.modules.client.dtos.ClientDTO;
import dev.eliezer.lojaonline.modules.client.entities.ClientEntity;
import dev.eliezer.lojaonline.modules.client.entities.ClientPhoneEntity;
import dev.eliezer.lojaonline.modules.client.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class ClientPhoneMapper {

    @Autowired
    private ClientRepository clientRepository;
    public List<ClientPhoneEntity> toClientPhoneEntity (List<ClientDTO.PhoneDTO> phoneDTOS, Long clientId) {


        ClientEntity client = clientRepository.findById(clientId).orElseThrow(() -> new NotFoundException(clientId));
        List<ClientPhoneEntity> clientPhoneEntities = new ArrayList<>();

        phoneDTOS.forEach(phoneDTO -> {
            ClientPhoneEntity clientPhoneEntity = new ClientPhoneEntity();
            clientPhoneEntity.setCountryCode(phoneDTO.getCountryCode());
            clientPhoneEntity.setAreaCode(phoneDTO.getAreaCode());
            clientPhoneEntity.setNumber(phoneDTO.getNumber());
            clientPhoneEntity.setClient(client);
            clientPhoneEntities.add(clientPhoneEntity);
        });

        return clientPhoneEntities;
    }

}
