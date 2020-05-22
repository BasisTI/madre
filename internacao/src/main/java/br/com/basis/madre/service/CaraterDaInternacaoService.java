package br.com.basis.madre.service;

import br.com.basis.madre.domain.CaraterDaInternacao;
import br.com.basis.madre.repository.CaraterDaInternacaoRepository;
import br.com.basis.madre.repository.search.CaraterDaInternacaoSearchRepository;
import br.com.basis.madre.service.dto.CaraterDaInternacaoDTO;
import br.com.basis.madre.service.mapper.CaraterDaInternacaoMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

@RequiredArgsConstructor
@Service
@Transactional
public class CaraterDaInternacaoService {

    private final Logger log = LoggerFactory.getLogger(CaraterDaInternacaoService.class);

    private final CaraterDaInternacaoRepository caraterDaInternacaoRepository;

    private final CaraterDaInternacaoMapper caraterDaInternacaoMapper;

    private final CaraterDaInternacaoSearchRepository caraterDaInternacaoSearchRepository;

    /**
     * Save a caraterDaInternacao.
     *
     * @param caraterDaInternacaoDTO the entity to save.
     * @return the persisted entity.
     */
    public CaraterDaInternacaoDTO save(CaraterDaInternacaoDTO caraterDaInternacaoDTO) {
        log.debug("Request to save CaraterDaInternacao : {}", caraterDaInternacaoDTO);
        CaraterDaInternacao caraterDaInternacao = caraterDaInternacaoMapper
            .toEntity(caraterDaInternacaoDTO);
        caraterDaInternacao = caraterDaInternacaoRepository.save(caraterDaInternacao);
        CaraterDaInternacaoDTO result = caraterDaInternacaoMapper.toDto(caraterDaInternacao);
        caraterDaInternacaoSearchRepository.save(caraterDaInternacao);
        return result;
    }

    /**
     * Get all the caraterDaInternacaos.
     *
     * @param caraterDaInternacaoDTO
     * @param pageable               the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CaraterDaInternacaoDTO> findAll(
        CaraterDaInternacaoDTO caraterDaInternacaoDTO,
        Pageable pageable) {
        log.debug("Request to get all CaraterDaInternacaos");
        return caraterDaInternacaoRepository.findAll(
            Example.of(caraterDaInternacaoMapper.toEntity(caraterDaInternacaoDTO),
                ExampleMatcher.matching().withIgnoreCase().withStringMatcher(
                    StringMatcher.CONTAINING
                ))
            , pageable)
            .map(caraterDaInternacaoMapper::toDto);
    }


    /**
     * Get one caraterDaInternacao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CaraterDaInternacaoDTO> findOne(Long id) {
        log.debug("Request to get CaraterDaInternacao : {}", id);
        return caraterDaInternacaoRepository.findById(id)
            .map(caraterDaInternacaoMapper::toDto);
    }

    /**
     * Delete the caraterDaInternacao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CaraterDaInternacao : {}", id);
        caraterDaInternacaoRepository.deleteById(id);
        caraterDaInternacaoSearchRepository.deleteById(id);
    }

    /**
     * Search for the caraterDaInternacao corresponding to the query.
     *
     * @param query    the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CaraterDaInternacaoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CaraterDaInternacaos for query {}", query);
        return caraterDaInternacaoSearchRepository.search(queryStringQuery(query), pageable)
            .map(caraterDaInternacaoMapper::toDto);
    }

}
