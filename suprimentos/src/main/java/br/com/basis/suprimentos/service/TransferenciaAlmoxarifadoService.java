package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.TransferenciaAlmoxarifado;
import br.com.basis.suprimentos.repository.TransferenciaAlmoxarifadoRepository;
import br.com.basis.suprimentos.repository.search.TransferenciaAlmoxarifadoSearchRepository;
import br.com.basis.suprimentos.service.dto.TransferenciaAlmoxarifadoDTO;
import br.com.basis.suprimentos.service.mapper.TransferenciaAlmoxarifadoMapper;
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
public class TransferenciaAlmoxarifadoService {
    private final TransferenciaAlmoxarifadoRepository transferenciaAlmoxarifadoRepository;
    private final TransferenciaAlmoxarifadoMapper transferenciaAlmoxarifadoMapper;
    private final TransferenciaAlmoxarifadoSearchRepository transferenciaAlmoxarifadoSearchRepository;

    public TransferenciaAlmoxarifadoDTO save(TransferenciaAlmoxarifadoDTO transferenciaAlmoxarifadoDTO) {
        log.debug("Request to save TransferenciaAlmoxarifado : {}", transferenciaAlmoxarifadoDTO);
        TransferenciaAlmoxarifado transferenciaAlmoxarifado = transferenciaAlmoxarifadoMapper.toEntity(transferenciaAlmoxarifadoDTO);
        transferenciaAlmoxarifado = transferenciaAlmoxarifadoRepository.save(transferenciaAlmoxarifado);
        TransferenciaAlmoxarifadoDTO result = transferenciaAlmoxarifadoMapper.toDto(transferenciaAlmoxarifado);
        transferenciaAlmoxarifadoSearchRepository.save(transferenciaAlmoxarifado);
        return result;
    }

    @Transactional(readOnly = true)
    public Page<TransferenciaAlmoxarifadoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TransferenciaAlmoxarifados");
        return transferenciaAlmoxarifadoRepository.findAll(pageable)
            .map(transferenciaAlmoxarifadoMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<TransferenciaAlmoxarifadoDTO> findOne(Long id) {
        log.debug("Request to get TransferenciaAlmoxarifado : {}", id);
        return transferenciaAlmoxarifadoRepository.findById(id)
            .map(transferenciaAlmoxarifadoMapper::toDto);
    }

    public void delete(Long id) {
        log.debug("Request to delete TransferenciaAlmoxarifado : {}", id);
        transferenciaAlmoxarifadoRepository.deleteById(id);
        transferenciaAlmoxarifadoSearchRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<TransferenciaAlmoxarifadoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TransferenciaAlmoxarifados for query {}", query);
        return transferenciaAlmoxarifadoSearchRepository.search(queryStringQuery(query), pageable)
            .map(transferenciaAlmoxarifadoMapper::toDto);
    }
}
