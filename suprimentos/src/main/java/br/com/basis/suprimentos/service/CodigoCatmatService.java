package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.CodigoCatmat;
import br.com.basis.suprimentos.repository.CodigoCatmatRepository;
import br.com.basis.suprimentos.repository.search.CodigoCatmatSearchRepository;
import br.com.basis.suprimentos.service.dto.CodigoCatmatDTO;
import br.com.basis.suprimentos.service.mapper.CodigoCatmatMapper;
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
public class CodigoCatmatService {
    private final CodigoCatmatRepository codigoCatmatRepository;
    private final CodigoCatmatMapper codigoCatmatMapper;
    private final CodigoCatmatSearchRepository codigoCatmatSearchRepository;

    public CodigoCatmatDTO save(CodigoCatmatDTO codigoCatmatDTO) {
        log.debug("Request to save CodigoCatmat : {}", codigoCatmatDTO);
        CodigoCatmat codigoCatmat = codigoCatmatMapper.toEntity(codigoCatmatDTO);
        codigoCatmat = codigoCatmatRepository.save(codigoCatmat);
        CodigoCatmatDTO result = codigoCatmatMapper.toDto(codigoCatmat);
        codigoCatmatSearchRepository.save(codigoCatmat);
        return result;
    }

    @Transactional(readOnly = true)
    public Page<CodigoCatmatDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CodigoCatmats");
        return codigoCatmatRepository.findAll(pageable)
            .map(codigoCatmatMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<CodigoCatmatDTO> findOne(Long id) {
        log.debug("Request to get CodigoCatmat : {}", id);
        return codigoCatmatRepository.findById(id)
            .map(codigoCatmatMapper::toDto);
    }

    public void delete(Long id) {
        log.debug("Request to delete CodigoCatmat : {}", id);
        codigoCatmatRepository.deleteById(id);
        codigoCatmatSearchRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<CodigoCatmatDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CodigoCatmats for query {}", query);
        return codigoCatmatSearchRepository.search(queryStringQuery(query), pageable)
            .map(codigoCatmatMapper::toDto);
    }
}
