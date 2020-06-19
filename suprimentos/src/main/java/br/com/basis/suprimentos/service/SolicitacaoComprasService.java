package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.SolicitacaoCompras;
import br.com.basis.suprimentos.repository.SolicitacaoComprasRepository;
import br.com.basis.suprimentos.repository.search.SolicitacaoComprasSearchRepository;
import br.com.basis.suprimentos.service.dto.SolicitacaoComprasDTO;
import br.com.basis.suprimentos.service.mapper.SolicitacaoComprasMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link SolicitacaoCompras}.
 */
@Service
@Transactional
public class SolicitacaoComprasService {

    private final Logger log = LoggerFactory.getLogger(SolicitacaoComprasService.class);

    private final SolicitacaoComprasRepository solicitacaoComprasRepository;

    private final SolicitacaoComprasMapper solicitacaoComprasMapper;

    private final SolicitacaoComprasSearchRepository solicitacaoComprasSearchRepository;

    public SolicitacaoComprasService(SolicitacaoComprasRepository solicitacaoComprasRepository, SolicitacaoComprasMapper solicitacaoComprasMapper, SolicitacaoComprasSearchRepository solicitacaoComprasSearchRepository) {
        this.solicitacaoComprasRepository = solicitacaoComprasRepository;
        this.solicitacaoComprasMapper = solicitacaoComprasMapper;
        this.solicitacaoComprasSearchRepository = solicitacaoComprasSearchRepository;
    }

    /**
     * Save a solicitacaoCompras.
     *
     * @param solicitacaoComprasDTO the entity to save.
     * @return the persisted entity.
     */
    public SolicitacaoComprasDTO save(SolicitacaoComprasDTO solicitacaoComprasDTO) {
        log.debug("Request to save SolicitacaoCompras : {}", solicitacaoComprasDTO);
        SolicitacaoCompras solicitacaoCompras = solicitacaoComprasMapper.toEntity(solicitacaoComprasDTO);
        solicitacaoCompras = solicitacaoComprasRepository.save(solicitacaoCompras);
        SolicitacaoComprasDTO result = solicitacaoComprasMapper.toDto(solicitacaoCompras);
        solicitacaoComprasSearchRepository.save(solicitacaoCompras);
        return result;
    }

    /**
     * Get all the solicitacaoCompras.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SolicitacaoComprasDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SolicitacaoCompras");
        return solicitacaoComprasRepository.findAll(pageable)
            .map(solicitacaoComprasMapper::toDto);
    }


    /**
     * Get one solicitacaoCompras by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SolicitacaoComprasDTO> findOne(Long id) {
        log.debug("Request to get SolicitacaoCompras : {}", id);
        return solicitacaoComprasRepository.findById(id)
            .map(solicitacaoComprasMapper::toDto);
    }

    /**
     * Delete the solicitacaoCompras by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SolicitacaoCompras : {}", id);
        solicitacaoComprasRepository.deleteById(id);
        solicitacaoComprasSearchRepository.deleteById(id);
    }

    /**
     * Search for the solicitacaoCompras corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SolicitacaoComprasDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SolicitacaoCompras for query {}", query);
        return solicitacaoComprasSearchRepository.search(queryStringQuery(query), pageable)
            .map(solicitacaoComprasMapper::toDto);
    }
}
