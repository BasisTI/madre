package br.com.basis.madre.repository;

import br.com.basis.madre.domain.SituacaoDeLeito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface SituacaoDeLeitoRepository extends JpaRepository<SituacaoDeLeito, Long> {

}
