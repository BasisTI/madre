package br.com.basis.madre.consulta.service.impl;

import br.com.basis.madre.consulta.domain.Feriado;
import br.com.basis.madre.consulta.repository.FeriadoRepository;
import br.com.basis.madre.consulta.service.FeriadoService;
import br.com.basis.madre.consulta.service.dto.FeriadoDTO;
import br.com.basis.madre.consulta.service.mapper.FeriadoMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FeriadoServiceImpl implements FeriadoService {

    private final Logger log = LoggerFactory.getLogger(FeriadoServiceImpl.class);

    private final FeriadoMapper feriadoMapper;

    private final FeriadoRepository feriadoRepository;

    @Override
    public Page<FeriadoDTO> findAll(FeriadoDTO feriadoDTO, Pageable pageable) {
        log.debug("Request to get all Feriado");
        return feriadoRepository.findAllByFilter(feriadoDTO, pageable);
    }

    @Override
    public Optional<FeriadoDTO> findById(Long id) {
        log.debug("Request to get Feriado: {}", id);
        return feriadoRepository.findById(id)
            .map(feriadoMapper::toDto);
    }

    @Override
    public FeriadoDTO save(FeriadoDTO feriadoDTO) {

        Feriado entity = feriadoMapper.toEntity(feriadoDTO);
        entity = feriadoRepository.save(entity);
        return feriadoMapper.toDto(entity);
    }
}
