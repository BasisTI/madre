package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.Lote;
import br.com.basis.suprimentos.repository.LoteRepository;
import br.com.basis.suprimentos.repository.search.LoteSearchRepository;
import br.com.basis.suprimentos.service.dto.LoteDTO;
import br.com.basis.suprimentos.service.mapper.LoteMapper;
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
public class LoteService {
    private final LoteRepository loteRepository;
    private final LoteMapper loteMapper;
    private final LoteSearchRepository loteSearchRepository;

    public LoteDTO save(LoteDTO loteDTO) {
        log.debug("Request to save Lote : {}", loteDTO);
        Lote lote = loteMapper.toEntity(loteDTO);
        lote = loteRepository.save(lote);
        LoteDTO result = loteMapper.toDto(lote);
        loteSearchRepository.save(lote);
        return result;
    }

    @Transactional(readOnly = true)
    public Page<LoteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Lotes");
        return loteRepository.findAll(pageable)
            .map(loteMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<LoteDTO> findOne(Long id) {
        log.debug("Request to get Lote : {}", id);
        return loteRepository.findById(id)
            .map(loteMapper::toDto);
    }

    public void delete(Long id) {
        log.debug("Request to delete Lote : {}", id);
        loteRepository.deleteById(id);
        loteSearchRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<LoteDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Lotes for query {}", query);
        return loteSearchRepository.search(queryStringQuery(query), pageable)
            .map(loteMapper::toDto);
    }
}
