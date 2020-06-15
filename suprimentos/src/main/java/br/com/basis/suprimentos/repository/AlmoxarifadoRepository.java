package br.com.basis.suprimentos.repository;

import br.com.basis.suprimentos.domain.Almoxarifado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface AlmoxarifadoRepository extends JpaRepository<Almoxarifado, Long> {
}
