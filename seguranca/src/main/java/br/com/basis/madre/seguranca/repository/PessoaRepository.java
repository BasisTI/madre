package br.com.basis.madre.seguranca.repository;


import br.com.basis.madre.seguranca.domain.Pessoa;
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



    @Query(value = "select p from Pessoa p where upper(p.nome) like %:nome% and (not (exists(select 1 from Servidor s where p.id = s.pessoa.id)))")
    Page<Pessoa> buscarPessoasCadastradas(@Param("nome") String nome, Pageable pageable);

}
