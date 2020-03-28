package br.com.basis.madre.consulta.service;

import br.com.basis.madre.consulta.service.dto.FeriadoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface FeriadoService {

    Page<FeriadoDTO> findAll(FeriadoDTO feriadoDTO, Pageable pageable);

    Optional<FeriadoDTO> findById(Long id);

    FeriadoDTO save(FeriadoDTO feriadoDTO);

    void delete(Long id);
}
