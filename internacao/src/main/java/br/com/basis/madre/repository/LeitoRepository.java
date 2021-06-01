package br.com.basis.madre.repository;

import br.com.basis.madre.domain.Leito;
import br.com.basis.madre.domain.enumeration.CodigoDoTipoEventoLeito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface LeitoRepository extends JpaRepository<Leito, Long> {

    static interface Queries {
        String baseQuery = "select distinct leito from EventoLeito evento join evento.leito leito where evento.tipoDoEvento.id = ";
        String obterTodosOsLeitosReservados = baseQuery + CodigoDoTipoEventoLeito.Constants.RESERVA_ID + " and current_timestamp between evento.dataInicio and evento.dataFim";
        String obterTodosOsLeitosBloqueados = baseQuery + CodigoDoTipoEventoLeito.Constants.BLOQUEIO_ID + " and current_timestamp between evento.dataInicio and evento.dataFim";
        String obterTodosOsLeitosOcupados = baseQuery + CodigoDoTipoEventoLeito.Constants.OCUPACAO_ID + " and evento.dataFim is null";
        String obterTodosOsLeitosNaoLiberados = "select distinct leito from EventoLeito evento join evento.leito leito where current_timestamp < evento.dataFim and not evento.tipoDoEvento.id = " + CodigoDoTipoEventoLeito.Constants.LIBERACAO_ID;
        String obterTodosOsLeitosLiberados = "select distinct leito from EventoLeito evento right join evento.leito leito where (current_timestamp not between evento.dataInicio and evento.dataFim) or (evento is null)";
    }

    @Query(Queries.obterTodosOsLeitosReservados)
    Page<Leito> obterTodosOsLeitosReservados(Pageable pageable);

    @Query(Queries.obterTodosOsLeitosBloqueados)
    Page<Leito> obterTodosOsLeitosBloqueados(Pageable pageable);

    @Query(Queries.obterTodosOsLeitosOcupados)
    Page<Leito> obterTodosOsLeitosOcupados(Pageable pageable);

    @Query(Queries.obterTodosOsLeitosNaoLiberados)
    Page<Leito> obterTodosOsLeitosNaoLiberados(Pageable pageable);

    @Query(Queries.obterTodosOsLeitosLiberados)
    Page<Leito> obterTodosOsLeitosLiberados(Pageable pageable);


    Long findLeitoById(Long leitoId);
}
