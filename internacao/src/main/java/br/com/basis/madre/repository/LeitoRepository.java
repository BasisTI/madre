package br.com.basis.madre.repository;

import br.com.basis.madre.domain.Leito;
import br.com.basis.madre.domain.SituacaoDeLeito;
import br.com.basis.madre.service.projection.LeitoProjection;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface LeitoRepository extends JpaRepository<Leito, Long> {

    List<LeitoProjection> findBySituacaoAndNomeIgnoreCaseContaining(SituacaoDeLeito situacaoDeLeito, String nome, Sort sort);

    List<LeitoProjection> findBySituacaoInAndNomeIgnoreCaseContaining(List<SituacaoDeLeito> situacoes, String nome, Sort sort);

}
