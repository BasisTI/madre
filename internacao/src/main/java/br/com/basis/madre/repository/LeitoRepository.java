package br.com.basis.madre.repository;

import br.com.basis.madre.domain.Leito;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface LeitoRepository extends JpaRepository<Leito, Long> {

    @Query(value = "select *\n"
        + "from evento_leito as ev\n"
        + "inner join leito as le on ev.leito_id = le.id\n"
        + "inner join tipo_do_evento_leito as tipo on tipo.id = ev.tipo_do_evento_id\n"
        + "where tipo.id = 2 and now() between data_do_inicio and data_do_fim;", nativeQuery = true)
    List<Leito> obterTodosOsLeitosReservados();

    @Query(value =
        "select *\n"
            + "from evento_leito as ev\n"
            + "inner join leito as le on ev.leito_id = le.id\n"
            + "inner join tipo_do_evento_leito as tipo on tipo.id = ev.tipo_do_evento_id\n"
            + "where tipo.id = 3 and now() between data_do_inicio and data_do_fim;", nativeQuery = true)
    List<Leito> obterTodosOsLeitosBloqueados();

    @Query(value = "select *\n"
        + "from evento_leito as ev\n"
        + "inner join leito as le on ev.leito_id = le.id\n"
        + "inner join tipo_do_evento_leito as tipo on tipo.id = ev.tipo_do_evento_id\n"
        + "where tipo.id = 4;", nativeQuery = true)
    List<Leito> obterTodosOsLeitosOcupados();

    @Query(value = "select *\n"
        + "from evento_leito as ev\n"
        + "inner join leito as le on ev.leito_id = le.id\n"
        + "inner join tipo_do_evento_leito as tipo on tipo.id = ev.tipo_do_evento_id\n"
        + "where not ev.tipo_do_evento_id = 1 and now() between data_do_inicio and data_do_fim or tipo.id = 4;", nativeQuery = true)
    List<Leito> obterTodosOsLeitosNaoLiberados();

    @Query(value = "select *\n"
        + "from leito as l\n"
        + "left join evento_leito as e on l.id = e.leito_id\n"
        + "where e.id is null", nativeQuery = true)
    List<Leito> obterTodosOsLeitosLiberados();
}
