package br.com.basis.madre.repository;

import br.com.basis.madre.domain.Triagem;
import br.com.basis.madre.service.projection.TriagemProjection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("unused")
@Repository

public interface TriagemRepository extends JpaRepository<Triagem, Long> {
    List<Triagem> findByPacienteId(Long id);

    Page<TriagemProjection> findAllProjectedTriagemBy(Pageable pageable);
}

