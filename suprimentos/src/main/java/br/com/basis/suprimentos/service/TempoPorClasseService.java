package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.TempoPorClasse;
import br.com.basis.suprimentos.repository.TempoPorClasseRepository;
import br.com.basis.suprimentos.repository.search.TempoPorClasseSearchRepository;
import br.com.basis.suprimentos.service.dto.TempoPorClasseDTO;
import br.com.basis.suprimentos.service.mapper.TempoPorClasseMapper;
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
public class TempoPorClasseService {
    private final TempoPorClasseRepository tempoPorClasseRepository;
    private final TempoPorClasseMapper tempoPorClasseMapper;
    private final TempoPorClasseSearchRepository tempoPorClasseSearchRepository;

    public TempoPorClasseDTO save(TempoPorClasseDTO tempoPorClasseDTO) {
        log.debug("Request to save TempoPorClasse : {}", tempoPorClasseDTO);
        TempoPorClasse tempoPorClasse = tempoPorClasseMapper.toEntity(tempoPorClasseDTO);
        tempoPorClasse = tempoPorClasseRepository.save(tempoPorClasse);
        TempoPorClasseDTO result = tempoPorClasseMapper.toDto(tempoPorClasse);
        tempoPorClasseSearchRepository.save(tempoPorClasse);
        return result;
    }

    @Transactional(readOnly = true)
    public Page<TempoPorClasseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TempoPorClasses");
        return tempoPorClasseRepository.findAll(pageable)
            .map(tempoPorClasseMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<TempoPorClasseDTO> findOne(Long id) {
        log.debug("Request to get TempoPorClasse : {}", id);
        return tempoPorClasseRepository.findById(id)
            .map(tempoPorClasseMapper::toDto);
    }

    public void delete(Long id) {
        log.debug("Request to delete TempoPorClasse : {}", id);
        tempoPorClasseRepository.deleteById(id);
        tempoPorClasseSearchRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<TempoPorClasseDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TempoPorClasses for query {}", query);
        return tempoPorClasseSearchRepository.search(queryStringQuery(query), pageable)
            .map(tempoPorClasseMapper::toDto);
    }
}
