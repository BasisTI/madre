package br.com.basis.madre.prescricao.service;

import br.com.basis.madre.prescricao.domain.ViasAdministracao;
import br.com.basis.madre.prescricao.repository.ViasAdministracaoRepository;
import br.com.basis.madre.prescricao.repository.search.ViasAdministracaoSearchRepository;
import br.com.basis.madre.prescricao.service.dto.ViasAdministracaoDTO;
import br.com.basis.madre.prescricao.service.mapper.ViasAdministracaoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link ViasAdministracao}.
 */
@Service
@Transactional
public class ViasAdministracaoService {

    private final Logger log = LoggerFactory.getLogger(ViasAdministracaoService.class);

    private final ViasAdministracaoRepository viasAdministracaoRepository;

    private final ViasAdministracaoMapper viasAdministracaoMapper;

    private final ViasAdministracaoSearchRepository viasAdministracaoSearchRepository;

    public ViasAdministracaoService(ViasAdministracaoRepository viasAdministracaoRepository, ViasAdministracaoMapper viasAdministracaoMapper, ViasAdministracaoSearchRepository viasAdministracaoSearchRepository) {
        this.viasAdministracaoRepository = viasAdministracaoRepository;
        this.viasAdministracaoMapper = viasAdministracaoMapper;
        this.viasAdministracaoSearchRepository = viasAdministracaoSearchRepository;
    }

    /**
     * Save a viasAdministracao.
     *
     * @param viasAdministracaoDTO the entity to save.
     * @return the persisted entity.
     */
    public ViasAdministracaoDTO save(ViasAdministracaoDTO viasAdministracaoDTO) {
        log.debug("Request to save ViasAdministracao : {}", viasAdministracaoDTO);
        ViasAdministracao viasAdministracao = viasAdministracaoMapper.toEntity(viasAdministracaoDTO);
        viasAdministracao = viasAdministracaoRepository.save(viasAdministracao);
        ViasAdministracaoDTO result = viasAdministracaoMapper.toDto(viasAdministracao);
        viasAdministracaoSearchRepository.save(viasAdministracao);
        return result;
    }

    /**
     * Get all the viasAdministracaos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ViasAdministracaoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ViasAdministracaos");
        return viasAdministracaoRepository.findAll(pageable)
            .map(viasAdministracaoMapper::toDto);
    }


    /**
     * Get one viasAdministracao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ViasAdministracaoDTO> findOne(Long id) {
        log.debug("Request to get ViasAdministracao : {}", id);
        return viasAdministracaoRepository.findById(id)
            .map(viasAdministracaoMapper::toDto);
    }

    /**
     * Delete the viasAdministracao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ViasAdministracao : {}", id);
        viasAdministracaoRepository.deleteById(id);
        viasAdministracaoSearchRepository.deleteById(id);
    }

    /**
     * Search for the viasAdministracao corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ViasAdministracaoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ViasAdministracaos for query {}", query);
        return viasAdministracaoSearchRepository.search(queryStringQuery(query), pageable)
            .map(viasAdministracaoMapper::toDto);
    }
}
