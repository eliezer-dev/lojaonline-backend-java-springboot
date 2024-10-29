package dev.eliezer.lojaonline.modules.order.repositories;

import dev.eliezer.lojaonline.modules.order.entities.OrderEntity;
import dev.eliezer.lojaonline.modules.product.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity,Long> {
}
