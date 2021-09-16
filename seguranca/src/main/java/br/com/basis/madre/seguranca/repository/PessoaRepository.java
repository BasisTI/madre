package br.com.basis.madre.seguranca.repository;

import br.com.basis.madre.seguranca.domain.Pessoa;
import br.com.basis.madre.seguranca.service.projection.PessoaCadastrada;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Pessoa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    @Query(value = "select * from pessoa p where upper(p.nome) like %:nome% and (not (exists(select 1 from servidor s where p.id = s.pessoa_id)))", nativeQuery = true)
    Page<PessoaCadastrada> findPessoasCadastradas(@Param("nome") String nome, Pageable pageable);
}
