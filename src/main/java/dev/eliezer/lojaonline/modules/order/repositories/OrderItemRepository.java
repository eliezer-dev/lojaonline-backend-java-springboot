package dev.eliezer.lojaonline.modules.order.repositories;

import dev.eliezer.lojaonline.modules.order.entities.OrderInstallmentsEntity;
import dev.eliezer.lojaonline.modules.order.entities.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemEntity,Long> {
    List<OrderItemEntity> findAllByOrder_Id (Long orderId);
}
