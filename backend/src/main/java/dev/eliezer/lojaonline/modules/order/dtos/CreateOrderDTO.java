package dev.eliezer.lojaonline.modules.order.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class CreateOrderDTO {


    private Long userId = 1L;

    private Long clientId;

    @NotNull(message = "[orderItems] is not provided")
    private List<CreateOrderItemDTO> orderItems = new ArrayList<>();

    @NotNull(message = "[totalValue] is not provided.")
    private BigDecimal totalValue;

    private String invoiceNumber;

    @NotNull(message = "[orderInstallments] is not provided")
    private List<CreateOrderInstallmentsDTO> orderInstallments = new ArrayList<>();

}
