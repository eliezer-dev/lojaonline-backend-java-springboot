package dev.eliezer.lojaonline.modules.order.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {

    private Long id;

    private Long userId;

    private List<OrderItemResponseDTO> orderItems = new ArrayList<>();

    private BigDecimal totalValue;

    private String invoiceNumber;

    private List<OrderInstallmentsResponseDTO> orderInstallments = new ArrayList<>();

    @Schema(example = "2024-07-21T22:38:10.514664", requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "order creation datetime")
    private LocalDateTime createAt;

    @Schema(example = "2024-07-21T22:38:10.514664", requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "order update datetime")
    private LocalDateTime updateAt;
}
