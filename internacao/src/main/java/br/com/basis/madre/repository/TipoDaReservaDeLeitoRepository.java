package br.com.basis.madre.repository;

import br.com.basis.madre.domain.TipoDaReservaDeLeito;
import java.util.List;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface TipoDaReservaDeLeitoRepository extends JpaRepository<TipoDaReservaDeLeito, Long> {
}
