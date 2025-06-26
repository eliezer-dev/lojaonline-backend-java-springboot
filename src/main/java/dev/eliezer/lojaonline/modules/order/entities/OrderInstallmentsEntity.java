package dev.eliezer.lojaonline.modules.order.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.eliezer.lojaonline.modules.order.dtos.CreateOrderInstallmentsDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity(name = "tb_order_installments")
@NoArgsConstructor
public class OrderInstallmentsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "order installments id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private OrderEntity order;


    @NotNull(message = "[paymentMethod] is not provided")
    private Long paymentMethod;

    @NotNull(message = "[installments] is not provided")
    private Long installment;

    @NotNull(message = "[numberOfInstallments] is not provided")
    private Long numberOfInstallments;

    private BigDecimal installmentValue;

    public OrderInstallmentsEntity (OrderEntity order, CreateOrderInstallmentsDTO createOrderInstallmentsDTO) {
        this.order = order;
        this.installment = createOrderInstallmentsDTO.getInstallment();
        this.numberOfInstallments = createOrderInstallmentsDTO.getNumberOfInstallments();
        this.paymentMethod = createOrderInstallmentsDTO.getPaymentMethod();
        this.installmentValue = createOrderInstallmentsDTO.getInstallmentValue();

    }

}
