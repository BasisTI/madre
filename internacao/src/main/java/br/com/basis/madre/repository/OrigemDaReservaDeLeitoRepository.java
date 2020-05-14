package br.com.basis.madre.repository;

import br.com.basis.madre.domain.OrigemDaReservaDeLeito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface OrigemDaReservaDeLeitoRepository extends
    JpaRepository<OrigemDaReservaDeLeito, Long> {

}
