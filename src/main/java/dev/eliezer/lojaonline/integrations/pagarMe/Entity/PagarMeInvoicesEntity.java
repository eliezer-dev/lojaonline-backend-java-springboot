package dev.eliezer.lojaonline.integrations.pagarMe.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity(name = "tb_pagar_me_invoices")
public class PagarMeInvoicesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    String idFatura;

    @Column(nullable = true)
    Integer expireIn = 0;

    @Column(nullable = false)
    LocalDateTime createdAt;

    @Column(nullable = false)
    String pagarMeUrl;

}
