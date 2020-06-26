package br.com.basis.suprimentos.repository;

import br.com.basis.suprimentos.domain.AutorizacaoFornecimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AutorizacaoFornecimento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AutorizacaoFornecimentoRepository extends JpaRepository<AutorizacaoFornecimento, Long> {

}
