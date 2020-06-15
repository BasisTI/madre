package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.CentroDeAtividade;
import br.com.basis.suprimentos.repository.CentroDeAtividadeRepository;
import br.com.basis.suprimentos.repository.search.CentroDeAtividadeSearchRepository;
import br.com.basis.suprimentos.service.dto.CentroDeAtividadeDTO;
import br.com.basis.suprimentos.service.mapper.CentroDeAtividadeMapper;
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
public class CentroDeAtividadeService {

    private final CentroDeAtividadeRepository centroDeAtividadeRepository;

    private final CentroDeAtividadeMapper centroDeAtividadeMapper;

    private final CentroDeAtividadeSearchRepository centroDeAtividadeSearchRepository;

    public CentroDeAtividadeDTO save(CentroDeAtividadeDTO centroDeAtividadeDTO) {
        log.debug("Request to save CentroDeAtividade : {}", centroDeAtividadeDTO);
        CentroDeAtividade centroDeAtividade = centroDeAtividadeMapper.toEntity(centroDeAtividadeDTO);
        centroDeAtividade = centroDeAtividadeRepository.save(centroDeAtividade);
        CentroDeAtividadeDTO result = centroDeAtividadeMapper.toDto(centroDeAtividade);
        centroDeAtividadeSearchRepository.save(centroDeAtividade);
        return result;
    }

    @Transactional(readOnly = true)
    public Page<CentroDeAtividadeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CentroDeAtividades");
        return centroDeAtividadeRepository.findAll(pageable)
            .map(centroDeAtividadeMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<CentroDeAtividadeDTO> findOne(Long id) {
        log.debug("Request to get CentroDeAtividade : {}", id);
        return centroDeAtividadeRepository.findById(id)
            .map(centroDeAtividadeMapper::toDto);
    }

    public void delete(Long id) {
        log.debug("Request to delete CentroDeAtividade : {}", id);
        centroDeAtividadeRepository.deleteById(id);
        centroDeAtividadeSearchRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<CentroDeAtividadeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CentroDeAtividades for query {}", query);
        return centroDeAtividadeSearchRepository.search(queryStringQuery(query), pageable)
            .map(centroDeAtividadeMapper::toDto);
    }
}
