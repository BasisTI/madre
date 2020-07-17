package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.Sazonalidade;
import br.com.basis.suprimentos.repository.SazonalidadeRepository;
import br.com.basis.suprimentos.repository.search.SazonalidadeSearchRepository;
import br.com.basis.suprimentos.service.dto.SazonalidadeDTO;
import br.com.basis.suprimentos.service.mapper.SazonalidadeMapper;
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
public class SazonalidadeService {
    private final SazonalidadeRepository sazonalidadeRepository;
    private final SazonalidadeMapper sazonalidadeMapper;
    private final SazonalidadeSearchRepository sazonalidadeSearchRepository;

    public SazonalidadeDTO save(SazonalidadeDTO sazonalidadeDTO) {
        log.debug("Request to save Sazonalidade : {}", sazonalidadeDTO);
        Sazonalidade sazonalidade = sazonalidadeMapper.toEntity(sazonalidadeDTO);
        sazonalidade = sazonalidadeRepository.save(sazonalidade);
        SazonalidadeDTO result = sazonalidadeMapper.toDto(sazonalidade);
        sazonalidadeSearchRepository.save(sazonalidade);
        return result;
    }

    @Transactional(readOnly = true)
    public Page<SazonalidadeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Sazonalidades");
        return sazonalidadeRepository.findAll(pageable)
            .map(sazonalidadeMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<SazonalidadeDTO> findOne(Long id) {
        log.debug("Request to get Sazonalidade : {}", id);
        return sazonalidadeRepository.findById(id)
            .map(sazonalidadeMapper::toDto);
    }

    public void delete(Long id) {
        log.debug("Request to delete Sazonalidade : {}", id);
        sazonalidadeRepository.deleteById(id);
        sazonalidadeSearchRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<SazonalidadeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Sazonalidades for query {}", query);
        return sazonalidadeSearchRepository.search(queryStringQuery(query), pageable)
            .map(sazonalidadeMapper::toDto);
    }
}
