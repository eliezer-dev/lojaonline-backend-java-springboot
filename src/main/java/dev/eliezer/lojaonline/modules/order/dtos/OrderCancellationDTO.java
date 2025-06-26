package dev.eliezer.lojaonline.modules.order.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderCancellationDTO {

    @NotNull(message = "[cancellationReason] is not provided")
    private String cancellationReason;
}
