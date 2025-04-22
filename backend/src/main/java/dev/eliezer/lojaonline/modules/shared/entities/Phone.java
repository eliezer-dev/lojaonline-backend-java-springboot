package dev.eliezer.lojaonline.modules.shared.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@MappedSuperclass
public class Phone {

    @Column(nullable = false)
    private String countryCode = "+55";

    @Column(nullable = false)
    private String areaCode;

    @Column(nullable = false)
    private String number;


}
