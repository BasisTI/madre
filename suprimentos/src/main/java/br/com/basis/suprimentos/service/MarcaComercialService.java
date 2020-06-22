package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.MarcaComercial;
import br.com.basis.suprimentos.repository.MarcaComercialRepository;
import br.com.basis.suprimentos.repository.search.MarcaComercialSearchRepository;
import br.com.basis.suprimentos.service.dto.MarcaComercialDTO;
import br.com.basis.suprimentos.service.mapper.MarcaComercialMapper;
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
public class MarcaComercialService {
    private final MarcaComercialRepository marcaComercialRepository;
    private final MarcaComercialMapper marcaComercialMapper;
    private final MarcaComercialSearchRepository marcaComercialSearchRepository;

    public MarcaComercialDTO save(MarcaComercialDTO marcaComercialDTO) {
        log.debug("Request to save MarcaComercial : {}", marcaComercialDTO);
        MarcaComercial marcaComercial = marcaComercialMapper.toEntity(marcaComercialDTO);
        marcaComercial = marcaComercialRepository.save(marcaComercial);
        MarcaComercialDTO result = marcaComercialMapper.toDto(marcaComercial);
        marcaComercialSearchRepository.save(marcaComercial);
        return result;
    }

    @Transactional(readOnly = true)
    public Page<MarcaComercialDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MarcaComercials");
        return marcaComercialRepository.findAll(pageable)
            .map(marcaComercialMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<MarcaComercialDTO> findOne(Long id) {
        log.debug("Request to get MarcaComercial : {}", id);
        return marcaComercialRepository.findById(id)
            .map(marcaComercialMapper::toDto);
    }

    public void delete(Long id) {
        log.debug("Request to delete MarcaComercial : {}", id);
        marcaComercialRepository.deleteById(id);
        marcaComercialSearchRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<MarcaComercialDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MarcaComercials for query {}", query);
        return marcaComercialSearchRepository.search(queryStringQuery(query), pageable)
            .map(marcaComercialMapper::toDto);
    }
}
