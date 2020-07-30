package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.UnidadeMedida;
import br.com.basis.suprimentos.repository.UnidadeMedidaRepository;
import br.com.basis.suprimentos.repository.search.UnidadeMedidaSearchRepository;
import br.com.basis.suprimentos.service.dto.UnidadeMedidaDTO;
import br.com.basis.suprimentos.service.mapper.UnidadeMedidaMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
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
public class UnidadeMedidaService {
    private final UnidadeMedidaRepository unidadeMedidaRepository;
    private final UnidadeMedidaMapper unidadeMedidaMapper;
    private final UnidadeMedidaSearchRepository unidadeMedidaSearchRepository;

     public UnidadeMedidaDTO save(UnidadeMedidaDTO unidadeMedidaDTO) {
        log.debug("Request to save UnidadeMedida : {}", unidadeMedidaDTO);
        UnidadeMedida unidadeMedida = unidadeMedidaMapper.toEntity(unidadeMedidaDTO);
        unidadeMedida = unidadeMedidaRepository.save(unidadeMedida);
        UnidadeMedidaDTO result = unidadeMedidaMapper.toDto(unidadeMedida);
        unidadeMedidaSearchRepository.save(unidadeMedida);
        return result;
    }

    @Transactional(readOnly = true)
    public Page<UnidadeMedidaDTO> findAll(Pageable pageable, UnidadeMedidaDTO unidadeMedidaDTO) {
        log.debug("Request to get all UnidadeMedidas");
        return unidadeMedidaRepository.findAll(Example.of(unidadeMedidaMapper.toEntity(unidadeMedidaDTO), ExampleMatcher.matchingAll().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)), pageable)
            .map(unidadeMedidaMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<UnidadeMedidaDTO> findOne(Long id) {
        log.debug("Request to get UnidadeMedida : {}", id);
        return unidadeMedidaRepository.findById(id)
            .map(unidadeMedidaMapper::toDto);
    }

    public void delete(Long id) {
        log.debug("Request to delete UnidadeMedida : {}", id);
        unidadeMedidaRepository.deleteById(id);
        unidadeMedidaSearchRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<UnidadeMedidaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of UnidadeMedidas for query {}", query);
        return unidadeMedidaSearchRepository.search(queryStringQuery(query), pageable)
            .map(unidadeMedidaMapper::toDto);
    }
}
