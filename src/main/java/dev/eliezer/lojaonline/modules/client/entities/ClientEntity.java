package dev.eliezer.lojaonline.modules.client.entities;

import dev.eliezer.lojaonline.modules.order.entities.OrderEntity;
import dev.eliezer.lojaonline.modules.shared.entities.Address;
import dev.eliezer.lojaonline.modules.shared.entities.Phone;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "tb_client")
public class ClientEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String document;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id", nullable = false)
    private ClientAddressEntity address;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ClientPhoneEntity> phones;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private List<OrderEntity> orders = new ArrayList<>();


 }
