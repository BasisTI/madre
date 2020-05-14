package br.com.basis.madre.repository;

import br.com.basis.madre.domain.EventoLeito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface EventoLeitoRepository extends JpaRepository<EventoLeito, Long> {

}
