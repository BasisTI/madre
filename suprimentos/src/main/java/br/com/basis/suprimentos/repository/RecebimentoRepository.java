package br.com.basis.suprimentos.repository;

import br.com.basis.suprimentos.domain.Recebimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Recebimento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecebimentoRepository extends JpaRepository<Recebimento, Long> {

}
