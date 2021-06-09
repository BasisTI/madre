package br.com.basis.madre.repository;

import br.com.basis.madre.domain.EventoLeito;
import br.com.basis.madre.domain.TipoDoEventoLeito;
import br.com.basis.madre.service.projection.EventoCalendario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventoLeitoRepository extends JpaRepository<EventoLeito, Long> {

    Optional<EventoLeito> findOneByLeitoIdAndTipoDoEventoAndDataFimIsNull(Long leitoId,
                                                                          TipoDoEventoLeito tipoDoEventoLeito);

    boolean existsByLeitoId(Long leitoId);
    boolean findEventoLeitoById(Long leitoId);

    @Query("from EventoLeito l where l.leitoExcluido = false")
    List<EventoLeito> buscarLeitosOcupados();

    List<EventoLeito> findEventoLeitoByLeitoExcluidoIsFalse();
//    Page<EventoLeito> findEventoLeitoByLeitoExcluidoIsFalse(Pageable pageable);

    Page<EventoCalendario> findEventoCalendarioByLeitoExcluidoIsFalse(Pageable pageable);

}
