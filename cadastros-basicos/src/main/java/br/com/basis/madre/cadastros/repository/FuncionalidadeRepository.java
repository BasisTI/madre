package br.com.basis.madre.cadastros.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.basis.madre.cadastros.domain.Funcionalidade;


/**
 * Spring Data JPA repository for the Funcionalidade entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FuncionalidadeRepository extends JpaRepository<Funcionalidade, Long> {
//	
//	@Query("SELECT a.id f.id FROM Acao a, Funcionalidade f")
//	List<Integer> pegaIds();
	
	@Query("SELECT a.id FROM Acao a")
	List<Integer>pegaIdsAcao();
	
	@Query("SELECT f.id FROM Funcionalidade f")
	List<Integer>pegaIdsFuncionalidade();
	
	
}
