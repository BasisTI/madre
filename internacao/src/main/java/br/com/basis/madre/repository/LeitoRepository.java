package br.com.basis.madre.repository;

import br.com.basis.madre.domain.Leito;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface LeitoRepository extends JpaRepository<Leito, Long> {

    @Query(value = "select le.id, le.nome, le.ala, le.andar, le.unidade_funcional_id\n"
        + "from evento_leito as ev\n"
        + "inner join leito as le on ev.leito_id = le.id\n"
        + "inner join tipo_do_evento_leito as tipo on tipo.id = ev.tipo_do_evento_id\n"
        + "where tipo.id = 2 and now() between data_do_inicio and data_do_fim;", nativeQuery = true)
    List<Leito> obterTodosOsLeitosReservados();

    @Query(value =
        "select le.id, le.nome, le.ala, le.andar, le.unidade_funcional_id\n"
            + "from evento_leito as ev\n"
            + "inner join leito as le on ev.leito_id = le.id\n"
            + "inner join tipo_do_evento_leito as tipo on tipo.id = ev.tipo_do_evento_id\n"
            + "where tipo.id = 3 and now() between data_do_inicio and data_do_fim;", nativeQuery = true)
    List<Leito> obterTodosOsLeitosBloqueados();

    @Query(value = "select le.id, le.nome, le.ala, le.andar, le.unidade_funcional_id\n"
        + "from evento_leito as ev\n"
        + "inner join leito as le on ev.leito_id = le.id\n"
        + "inner join tipo_do_evento_leito as tipo on tipo.id = ev.tipo_do_evento_id\n"
        + "where tipo.id = 4 and ev.data_do_fim is null;", nativeQuery = true)
    List<Leito> obterTodosOsLeitosOcupados();

    @Query(value =
        "select distinct l.id, l.nome, l.ala, l.andar, l.unidade_funcional_id from (select le.id, le.nome, le.ala, le.andar, le.unidade_funcional_id\n"
            + "from evento_leito as ev\n"
            + "inner join leito as le on ev.leito_id = le.id\n"
            + "inner join tipo_do_evento_leito as tipo on tipo.id = ev.tipo_do_evento_id\n"
            + "where not ev.tipo_do_evento_id = 1 and now() between data_do_inicio and data_do_fim or tipo.id = 4) as l;", nativeQuery = true)
    List<Leito> obterTodosOsLeitosNaoLiberados();

    @Query(value =
        "select distinct l.id, l.nome, l.ala, l.andar, l.unidade_funcional_id from (select l.id, l.nome, l.ala, l.andar, l.unidade_funcional_id\n"
            + "from evento_leito as e\n"
            + "right join leito as l on l.id = e.leito_id\n"
            + "where e.id is null or e.tipo_do_evento_id = 1 and e.data_do_fim > now()) as l;", nativeQuery = true)
    List<Leito> obterTodosOsLeitosLiberados();
}
