package dev.eliezer.lojaonline.modules.order.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class CreateOrderItemDTO {

    @NotNull(message = "[productId] is not provided")
    private Long productId;

    @NotBlank(message = "[name] is not provided")
    private String name;

    @NotNull(message = "[quantity] is not provided")
    private Long quantity;

    @NotNull(message = "[price] is not provided")
    private BigDecimal price;

}
