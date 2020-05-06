package br.com.basis.madre.repository;

import br.com.basis.madre.domain.Leito;
import br.com.basis.madre.domain.SituacaoDeLeito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface LeitoRepository extends JpaRepository<Leito, Long> {

    Page<Leito> findBySituacaoAndNomeIgnoreCaseContaining(SituacaoDeLeito situacaoDeLeito, String nome, Pageable pageable);
}
