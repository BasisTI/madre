package br.com.basis.suprimentos.repository;

import br.com.basis.suprimentos.domain.TransferenciaAlmoxarifado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface TransferenciaAlmoxarifadoRepository extends JpaRepository<TransferenciaAlmoxarifado, Long> {
}
