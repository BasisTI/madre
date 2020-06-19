package br.com.basis.suprimentos.repository;
import br.com.basis.suprimentos.domain.TempoPorClasse;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TempoPorClasse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TempoPorClasseRepository extends JpaRepository<TempoPorClasse, Long> {

}
