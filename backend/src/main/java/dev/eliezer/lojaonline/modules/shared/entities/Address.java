package dev.eliezer.lojaonline.modules.shared.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@MappedSuperclass
public class Address {
    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String zipCode;

    @Column(nullable = false)
    private String neighborhood;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String number;

    private String complement;

}
