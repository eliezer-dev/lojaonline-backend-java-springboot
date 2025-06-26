package dev.eliezer.lojaonline.modules.client.repositories;

import dev.eliezer.lojaonline.modules.client.entities.ClientAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientAddressRepository extends JpaRepository<ClientAddressEntity, Long> {
}
