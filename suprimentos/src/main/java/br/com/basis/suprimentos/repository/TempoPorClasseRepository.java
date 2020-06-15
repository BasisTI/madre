package br.com.basis.suprimentos.repository;

import br.com.basis.suprimentos.domain.TempoPorClasse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface TempoPorClasseRepository extends JpaRepository<TempoPorClasse, Long> {
}
