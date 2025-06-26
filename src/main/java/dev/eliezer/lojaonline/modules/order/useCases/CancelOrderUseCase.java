package dev.eliezer.lojaonline.modules.order.useCases;

import dev.eliezer.lojaonline.exceptions.NotFoundException;
import dev.eliezer.lojaonline.modules.order.dtos.OrderCancellationDTO;
import dev.eliezer.lojaonline.modules.order.entities.OrderEntity;
import dev.eliezer.lojaonline.modules.order.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CancelOrderUseCase {

    @Autowired
    private OrderRepository orderRepository;

    private OrderEntity orderTarget;

    public void execute( Long orderId,Long userId, OrderCancellationDTO cancellationReason) {

        this.orderTarget = orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException(orderId));

        orderTarget.setCanceled(true);
        orderTarget.setCanceledBy(userId);
        orderTarget.setCanceledAt(LocalDateTime.now());
        orderTarget.setCancellationReason(cancellationReason.getCancellationReason());

        orderRepository.save(orderTarget);


    }

}
