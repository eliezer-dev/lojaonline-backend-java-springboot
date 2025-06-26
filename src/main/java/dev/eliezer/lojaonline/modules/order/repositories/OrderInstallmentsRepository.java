package dev.eliezer.lojaonline.modules.order.repositories;

import dev.eliezer.lojaonline.modules.order.entities.OrderInstallmentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderInstallmentsRepository extends JpaRepository<OrderInstallmentsEntity, Long> {
    List<OrderInstallmentsEntity> findAllByOrder_Id (Long orderId);
}
