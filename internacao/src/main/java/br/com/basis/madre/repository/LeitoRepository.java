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
        String obterTodosOsLeitosReservados = "select leito from EventoLeito evento join evento.leito leito where evento.tipoDoEvento.nome = " + CodigoDoTipoEventoLeito.Constants.RESERVA_ID + " and current_date between evento.dataInicio and evento.dataFim";
        String obterTodosOsLeitosBloqueados = "select leito from EventoLeito evento join evento.leito leito where evento.tipoDoEvento.id = " + CodigoDoTipoEventoLeito.Constants.BLOQUEIO_ID + " and current_date between evento.dataInicio and evento.dataFim";
        String obterTodosOsLeitosOcupados = "select leito from EventoLeito evento join evento.leito leito where evento.tipoDoEvento.id = " + CodigoDoTipoEventoLeito.Constants.OCUPACAO_ID + " and evento.dataFim is null";
        String obterTodosOsLeitosNaoLiberados = "select leito from EventoLeito evento join evento.leito leito where not evento.tipoDoEvento.id = " + CodigoDoTipoEventoLeito.Constants.LIBERACAO_ID + " and current_date between evento.dataInicio and evento.dataFim or evento.tipoDoEvento.nome = " + CodigoDoTipoEventoLeito.Constants.OCUPACAO_ID;
        String obterTodosOsLeitosLiberados = "select leito from EventoLeito evento right join evento.leito leito where evento.dataFim > current_date or evento is null";
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
}
