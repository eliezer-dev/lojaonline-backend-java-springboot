package dev.eliezer.lojaonline.modules.client.entities;

import dev.eliezer.lojaonline.modules.shared.entities.Address;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity(name = "tb_client_address")
public class ClientAddressEntity extends Address {
    @Id
    @GeneratedValue
    private Long id;
}
