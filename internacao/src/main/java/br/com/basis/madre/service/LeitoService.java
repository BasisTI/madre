package br.com.basis.madre.service;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import br.com.basis.madre.domain.Leito;
import br.com.basis.madre.domain.SituacaoDeLeito;
import br.com.basis.madre.domain.enumeration.CodigoDeSituacaoDeLeito;
import br.com.basis.madre.repository.LeitoRepository;
import br.com.basis.madre.repository.search.LeitoSearchRepository;
import br.com.basis.madre.service.dto.LeitoDTO;
import br.com.basis.madre.service.dto.SituacaoDeLeitoDTO;
import br.com.basis.madre.service.mapper.LeitoMapper;
import br.com.basis.madre.service.mapper.SituacaoDeLeitoMapper;
import br.com.basis.madre.service.projection.LeitoProjection;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class LeitoService {

    private final Logger log = LoggerFactory.getLogger(LeitoService.class);

    private final LeitoRepository leitoRepository;

    private final SituacaoDeLeitoService situacaoDeLeitoService;

    private final LeitoMapper leitoMapper;

    private final SituacaoDeLeitoMapper situacaoDeLeitoMapper;

    private final LeitoSearchRepository leitoSearchRepository;

    /**
     * Save a leito.
     *
     * @param leitoDTO the entity to save.
     * @return the persisted entity.
     */
    public LeitoDTO save(LeitoDTO leitoDTO) {
        log.debug("Request to save Leito : {}", leitoDTO);
        Leito leito = leitoMapper.toEntity(leitoDTO);
        leito = leitoRepository.save(leito);
        LeitoDTO result = leitoMapper.toDto(leito);
        leitoSearchRepository.save(leito);
        return result;
    }

    /**
     * Get all the leitos.
     *
     * @param leitoDTO
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LeitoDTO> findAll(LeitoDTO leitoDTO,
        Pageable pageable) {
        log.debug("Request to get all Leitos");
        return leitoRepository.findAll(
            Example.of(leitoMapper.toEntity(leitoDTO),
                ExampleMatcher.matching().withIgnoreCase().withStringMatcher(
                    StringMatcher.CONTAINING))
            , pageable)
            .map(leitoMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<LeitoProjection> getLeitosDesocupadosPor(String nome,
        Pageable pageable) {
        Sort sort = pageable.getSort();
        CodigoDeSituacaoDeLeito codigoDeSituacao = CodigoDeSituacaoDeLeito.DESOCUPADO;

        SituacaoDeLeitoDTO situacao = new SituacaoDeLeitoDTO();
        situacao.setId(codigoDeSituacao.getValor());

        return leitoRepository
            .findBySituacaoAndNomeIgnoreCaseContaining(situacaoDeLeitoMapper.toEntity(situacao),
                nome, sort);
    }

    @Transactional(readOnly = true)
    public List<LeitoProjection> getLeitosNaoDesocupadosPor(String nome,
        Pageable pageable) {
        Sort sort = pageable.getSort();
        CodigoDeSituacaoDeLeito codigoDeDesocupado = CodigoDeSituacaoDeLeito.BLOQUEADO;
        CodigoDeSituacaoDeLeito codigoDeReservado = CodigoDeSituacaoDeLeito.RESERVADO;
        CodigoDeSituacaoDeLeito codigoDeOcupado = CodigoDeSituacaoDeLeito.OCUPADO;

        SituacaoDeLeitoDTO bloqueado = new SituacaoDeLeitoDTO().id(codigoDeDesocupado.getValor());
        SituacaoDeLeitoDTO reservado = new SituacaoDeLeitoDTO().id(codigoDeReservado.getValor());
        SituacaoDeLeitoDTO ocupado = new SituacaoDeLeitoDTO().id(codigoDeOcupado.getValor());

        List<SituacaoDeLeito> situacoes = Arrays.asList(bloqueado, reservado, ocupado).stream()
            .map(situacaoDeLeitoMapper::toEntity).collect(Collectors.toList());

        return leitoRepository
            .findBySituacaoInAndNomeIgnoreCaseContaining(situacoes,
                nome, sort);
    }

    /**
     * Get one leito by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LeitoDTO> findOne(Long id) {
        log.debug("Request to get Leito : {}", id);
        return leitoRepository.findById(id)
            .map(leitoMapper::toDto);
    }

    /**
     * Delete the leito by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Leito : {}", id);
        leitoRepository.deleteById(id);
        leitoSearchRepository.deleteById(id);
    }

    /**
     * Search for the leito corresponding to the query.
     *
     * @param query    the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LeitoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Leitos for query {}", query);
        return leitoSearchRepository.search(queryStringQuery(query), pageable)
            .map(leitoMapper::toDto);
    }

    public LeitoDTO liberarLeito(Long leitoId) {
        CodigoDeSituacaoDeLeito codigoDeSituacao = CodigoDeSituacaoDeLeito.DESOCUPADO;

        SituacaoDeLeitoDTO situacao = new SituacaoDeLeitoDTO();
        situacao.setId(codigoDeSituacao.getValor());

        Leito leito = leitoRepository.findById(leitoId).orElseThrow(EntityNotFoundException::new)
            .situacao(situacaoDeLeitoMapper.toEntity(situacao));
        leito = leitoRepository.save(leito);
        return leitoMapper.toDto(leito);
    }

    public LeitoDTO ocuparLeito(Long leitoId) {
        LeitoDTO leitoDTO = findOne(leitoId).orElseThrow(EntityNotFoundException::new);
        leitoDTO.setSituacaoId(CodigoDeSituacaoDeLeito.OCUPADO.getValor());
        return save(leitoDTO);
    }
}
