package dev.eliezer.lojaonline.modules.order.dtos;

import dev.eliezer.lojaonline.integrations.pagarMe.dtos.PagarMeResponseDTO;
import dev.eliezer.lojaonline.modules.order.entities.OrderEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderResponseDTO {

    private Long id;

    private Long userId;

    private List<OrderItemResponseDTO> orderItems = new ArrayList<>();

    private BigDecimal totalValue;

    private String invoiceNumber;

    private List<OrderInstallmentsResponseDTO> orderInstallments = new ArrayList<>();

    private PagarMeResponseDTO pagarMe;

    @Schema(example = "2024-07-21T22:38:10.514664", requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "order creation datetime")
    private LocalDateTime createAt;

    @Schema(example = "2024-07-21T22:38:10.514664", requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "order update datetime")
    private LocalDateTime updateAt;

    private Boolean canceled;

    private Long canceledBy;

    private LocalDateTime canceledAt;

    private String cancellationReason;



}
