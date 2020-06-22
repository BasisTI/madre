package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.CaracteristicaArmazenamento;
import br.com.basis.suprimentos.repository.CaracteristicaArmazenamentoRepository;
import br.com.basis.suprimentos.repository.search.CaracteristicaArmazenamentoSearchRepository;
import br.com.basis.suprimentos.service.dto.CaracteristicaArmazenamentoDTO;
import br.com.basis.suprimentos.service.mapper.CaracteristicaArmazenamentoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class CaracteristicaArmazenamentoService {
    private final CaracteristicaArmazenamentoRepository caracteristicaArmazenamentoRepository;
    private final CaracteristicaArmazenamentoMapper caracteristicaArmazenamentoMapper;
    private final CaracteristicaArmazenamentoSearchRepository caracteristicaArmazenamentoSearchRepository;

    public CaracteristicaArmazenamentoDTO save(CaracteristicaArmazenamentoDTO caracteristicaArmazenamentoDTO) {
        log.debug("Request to save CaracteristicaArmazenamento : {}", caracteristicaArmazenamentoDTO);
        CaracteristicaArmazenamento caracteristicaArmazenamento = caracteristicaArmazenamentoMapper.toEntity(caracteristicaArmazenamentoDTO);
        caracteristicaArmazenamento = caracteristicaArmazenamentoRepository.save(caracteristicaArmazenamento);
        CaracteristicaArmazenamentoDTO result = caracteristicaArmazenamentoMapper.toDto(caracteristicaArmazenamento);
        caracteristicaArmazenamentoSearchRepository.save(caracteristicaArmazenamento);
        return result;
    }

    @Transactional(readOnly = true)
    public Page<CaracteristicaArmazenamentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CaracteristicaArmazenamentos");
        return caracteristicaArmazenamentoRepository.findAll(pageable)
            .map(caracteristicaArmazenamentoMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<CaracteristicaArmazenamentoDTO> findOne(Long id) {
        log.debug("Request to get CaracteristicaArmazenamento : {}", id);
        return caracteristicaArmazenamentoRepository.findById(id)
            .map(caracteristicaArmazenamentoMapper::toDto);
    }

    public void delete(Long id) {
        log.debug("Request to delete CaracteristicaArmazenamento : {}", id);
        caracteristicaArmazenamentoRepository.deleteById(id);
        caracteristicaArmazenamentoSearchRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<CaracteristicaArmazenamentoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CaracteristicaArmazenamentos for query {}", query);
        return caracteristicaArmazenamentoSearchRepository.search(queryStringQuery(query), pageable)
            .map(caracteristicaArmazenamentoMapper::toDto);
    }
}
