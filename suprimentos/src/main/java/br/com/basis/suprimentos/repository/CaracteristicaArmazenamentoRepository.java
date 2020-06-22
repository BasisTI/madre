package br.com.basis.suprimentos.repository;

import br.com.basis.suprimentos.domain.CaracteristicaArmazenamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CaracteristicaArmazenamento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CaracteristicaArmazenamentoRepository extends JpaRepository<CaracteristicaArmazenamento, Long> {

}
