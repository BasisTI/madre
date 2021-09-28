package br.com.basis.madre.madreexames.repository;

import br.com.basis.madre.madreexames.domain.Exame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Exame entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExameRepository extends JpaRepository<Exame, Long> {

    @Query(value = "select e from Exame e where upper(e.nome) like %:termo% or exists(select 1 from Sinonimo s where s.exame = e and upper(s.nome) like %:termo%)")
    List<Exame> findByNameOrSinonimos(@Param("termo") String termo);
}
