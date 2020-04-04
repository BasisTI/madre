package br.com.basis.madre.prescricao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.basis.madre.prescricao.domain.Dieta;

@Repository
public interface DietaRepository extends JpaRepository<Dieta, Long>{

}
