package br.com.basis.suprimentos.repository;

import br.com.basis.suprimentos.domain.CentroDeAtividade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface CentroDeAtividadeRepository extends JpaRepository<CentroDeAtividade, Long> {
}
