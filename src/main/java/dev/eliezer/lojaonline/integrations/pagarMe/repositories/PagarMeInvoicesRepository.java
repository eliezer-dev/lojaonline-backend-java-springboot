package dev.eliezer.lojaonline.integrations.pagarMe.repositories;

import dev.eliezer.lojaonline.integrations.pagarMe.Entity.PagarMeInvoicesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagarMeInvoicesRepository extends JpaRepository<PagarMeInvoicesEntity, Long> {
}
