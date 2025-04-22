package dev.eliezer.lojaonline.modules.client.mappers;

import dev.eliezer.lojaonline.modules.client.dtos.CreateClientDTO;
import dev.eliezer.lojaonline.modules.client.dtos.ClientDTO.PhoneDTO;
import dev.eliezer.lojaonline.modules.client.dtos.ClientDTO.AddressDTO;
import dev.eliezer.lojaonline.modules.client.dtos.CreateResponseClientDTO;
import dev.eliezer.lojaonline.modules.client.entities.ClientAddressEntity;
import dev.eliezer.lojaonline.modules.client.entities.ClientEntity;
import dev.eliezer.lojaonline.modules.client.entities.ClientPhoneEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ClientMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ClientEntity toEntity(CreateClientDTO createClientDTO) {
        ClientEntity clientEntity = new ClientEntity();

        clientEntity.setName(createClientDTO.getFullname());
        clientEntity.setDocument(createClientDTO.getDocument());
        clientEntity.setGender(createClientDTO.getGender());
        clientEntity.setBirthDate(createClientDTO.getBirthDate());
        clientEntity.setEmail(createClientDTO.getEmail());
        clientEntity.setPassword(passwordEncoder.encode(createClientDTO.getPassword()));

        if (createClientDTO.getAddress() != null) {
            clientEntity.setAddress(toClientAddressEntity( createClientDTO.getAddress()));

        }

//        if (!createClientDTO.getPhone().isEmpty()) {
//            clientEntity.setPhones(toClientPhoneEntity( createClientDTO.getPhone()));
//        }

        return clientEntity;

    }

    public List<ClientPhoneEntity> toClientPhoneEntity (List<PhoneDTO> phoneDTOS) {

        List<ClientPhoneEntity> clientPhoneEntities = new ArrayList<>();

        phoneDTOS.forEach(phoneDTO -> {
            ClientPhoneEntity clientPhoneEntity = new ClientPhoneEntity();
            clientPhoneEntity.setCountryCode(phoneDTO.getCountryCode());
            clientPhoneEntity.setAreaCode(phoneDTO.getAreaCode());
            clientPhoneEntity.setNumber(phoneDTO.getNumber());
            clientPhoneEntities.add(clientPhoneEntity);
        });

        return clientPhoneEntities;
    }

    public ClientAddressEntity toClientAddressEntity (AddressDTO addressDTO) {
        ClientAddressEntity clientAddressEntity = new ClientAddressEntity();
        clientAddressEntity.setCountry(addressDTO.getCountry());
        clientAddressEntity.setState(addressDTO.getState());
        clientAddressEntity.setCity(addressDTO.getCity());
        clientAddressEntity.setZipCode(addressDTO.getZipCode());
        clientAddressEntity.setNeighborhood(addressDTO.getNeighborhood());
        clientAddressEntity.setStreet(addressDTO.getStreet());
        clientAddressEntity.setNumber(addressDTO.getNumber());
        clientAddressEntity.setComplement(addressDTO.getComplement());

        return clientAddressEntity;

    }

    public AddressDTO toResponseAddressDTO(ClientAddressEntity addressEntity) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setCountry(addressEntity.getCountry());
        addressDTO.setState(addressEntity.getState());
        addressDTO.setCity(addressEntity.getCity());
        addressDTO.setZipCode(addressEntity.getZipCode());
        addressDTO.setNeighborhood(addressEntity.getNeighborhood());
        addressDTO.setStreet(addressEntity.getStreet());
        addressDTO.setNumber(addressEntity.getNumber());
        addressDTO.setComplement(addressEntity.getComplement());
        return addressDTO;
    }

    public List<PhoneDTO> toResponsePhoneDTOs(List<ClientPhoneEntity> phoneEntities) {
        List<PhoneDTO> phoneDTOList = new ArrayList<>();

        phoneEntities.forEach(phoneEntity -> {
            PhoneDTO phoneDTO = new PhoneDTO();
            phoneDTO.setCountryCode(phoneEntity.getCountryCode());
            phoneDTO.setAreaCode(phoneEntity.getAreaCode());
            phoneDTO.setNumber(phoneEntity.getNumber());
            phoneDTOList.add(phoneDTO);
        });

        return phoneDTOList;
    }

    public CreateResponseClientDTO toResponseClientDTO(ClientEntity clientEntity) {
        CreateResponseClientDTO createResponseClientDTO = new CreateResponseClientDTO();

        createResponseClientDTO.setId(clientEntity.getId());
        createResponseClientDTO.setFullname(clientEntity.getName());
        createResponseClientDTO.setEmail(clientEntity.getEmail());
        createResponseClientDTO.setDocument(clientEntity.getDocument());
        createResponseClientDTO.setGender(clientEntity.getGender());
        createResponseClientDTO.setBirthDate(clientEntity.getBirthDate());

        if (clientEntity.getPhones() != null && !clientEntity.getPhones().isEmpty()) {
            createResponseClientDTO.setPhone(toResponsePhoneDTOs(clientEntity.getPhones()));
        }

        if (clientEntity.getAddress() != null) {
            createResponseClientDTO.setAddress(toResponseAddressDTO(clientEntity.getAddress()));
        }

        return createResponseClientDTO;
    }


}
