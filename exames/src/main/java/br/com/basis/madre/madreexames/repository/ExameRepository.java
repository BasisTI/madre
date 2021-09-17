package br.com.basis.madre.madreexames.repository;

import br.com.basis.madre.madreexames.domain.Exame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Exame entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExameRepository extends JpaRepository<Exame, Long> {

    List<Exame> findByGrupoAgendamentoExamesId(Long id);
}
