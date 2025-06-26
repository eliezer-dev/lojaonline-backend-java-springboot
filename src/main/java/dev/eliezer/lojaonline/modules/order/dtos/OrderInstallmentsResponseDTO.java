package dev.eliezer.lojaonline.modules.order.dtos;

import dev.eliezer.lojaonline.modules.order.entities.OrderInstallmentsEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderInstallmentsResponseDTO {

    private Long id;

    private Long paymentMethod;

    private Long installment;

    private Long numberOfInstallments;

    private BigDecimal installmentValue;

    public OrderInstallmentsResponseDTO (OrderInstallmentsEntity orderInstallmentsEntity) {
        this.id = orderInstallmentsEntity.getId();
        this.paymentMethod = orderInstallmentsEntity.getPaymentMethod();
        this.installment = orderInstallmentsEntity.getInstallment();
        this.numberOfInstallments = orderInstallmentsEntity.getNumberOfInstallments();
        this.installmentValue = orderInstallmentsEntity.getInstallmentValue();

    }
}
