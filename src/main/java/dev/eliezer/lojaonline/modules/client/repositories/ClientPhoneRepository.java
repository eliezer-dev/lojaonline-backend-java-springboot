package dev.eliezer.lojaonline.modules.client.repositories;

import dev.eliezer.lojaonline.modules.client.entities.ClientPhoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientPhoneRepository extends JpaRepository<ClientPhoneEntity, Long> {
}
