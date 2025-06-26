package dev.eliezer.lojaonline.modules.client.entities;

import dev.eliezer.lojaonline.modules.shared.entities.Phone;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "tb_client_phone")
public class ClientPhoneEntity extends Phone {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = true)
    private ClientEntity client;
}
