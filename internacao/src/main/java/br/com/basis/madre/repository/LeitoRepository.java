package br.com.basis.madre.repository;

import br.com.basis.madre.domain.Leito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface LeitoRepository extends JpaRepository<Leito, Long> {

    @Query("select leito from EventoLeito evento join evento.leito leito where evento.tipoDoEvento.nome = 'Reserva' and current_date between evento.dataInicio and evento.dataFim")
    Page<Leito> obterTodosOsLeitosReservados(Pageable pageable);

    @Query("select leito from EventoLeito evento join evento.leito leito where evento.tipoDoEvento.nome = 'Bloqueio' and current_date between evento.dataInicio and evento.dataFim")
    Page<Leito> obterTodosOsLeitosBloqueados(Pageable pageable);

    @Query("select leito from EventoLeito evento join evento.leito leito where evento.tipoDoEvento.nome = 'Ocupação' and evento.dataFim is null")
    Page<Leito> obterTodosOsLeitosOcupados(Pageable pageable);

    @Query("select leito from EventoLeito evento join evento.leito leito\n" +
        "where not evento.tipoDoEvento.nome = 'Liberação'\n" +
        "and current_date between evento.dataInicio and evento.dataFim or evento.tipoDoEvento.nome = 'Ocupação'")
    Page<Leito> obterTodosOsLeitosNaoLiberados(Pageable pageable);

    @Query("select leito from EventoLeito evento right join evento.leito leito\n" +
        "where current_date > evento.dataFim or evento is null")
    Page<Leito> obterTodosOsLeitosLiberados(Pageable pageable);
}
