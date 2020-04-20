package br.com.basis.madre.prescricao.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.basis.madre.prescricao.domain.Dieta;

@Repository
public interface DietaRepository extends JpaRepository<Dieta, Long>{
	@EntityGraph("dieta.itemDieta")
	List<Dieta> findByIdPaciente(Long id);
	
	@EntityGraph("dieta.itemDieta")
	Optional<Dieta> findById(Long id);
	
	@EntityGraph("dieta.itemDieta")
	List<Dieta> findAll();
	
	Optional<Dieta> save(Optional<Dieta> dieta);


}
