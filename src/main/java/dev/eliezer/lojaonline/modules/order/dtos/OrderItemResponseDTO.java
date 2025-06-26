package dev.eliezer.lojaonline.modules.order.dtos;

import dev.eliezer.lojaonline.modules.order.entities.OrderItemEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data

public class OrderItemResponseDTO {

    private Long id;

    private Long productId;

    private Long quantity;

    private BigDecimal price;

    @Schema(example = "2024-07-21T22:38:10.514664", requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "order item creation datetime")
    private LocalDateTime createAt;

    @Schema(example = "2024-07-21T22:38:10.514664", requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "order item update datetime")
    private LocalDateTime updateAt;

    public OrderItemResponseDTO (OrderItemEntity orderItemEntity) {

        this.id = orderItemEntity.getId();
        this.productId = orderItemEntity.getProduct().getId();
        this.quantity = orderItemEntity.getQuantity();
        this.price = orderItemEntity.getPrice();
        this.createAt = orderItemEntity.getCreateAt();
        this.updateAt = orderItemEntity.getUpdateAt();

    }


}
