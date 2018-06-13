package br.com.basis.madre.cadastros.service.impl;

import br.com.basis.madre.cadastros.service.AnexoService;
import br.com.basis.madre.cadastros.domain.Anexo;
import br.com.basis.madre.cadastros.repository.AnexoRepository;
import br.com.basis.madre.cadastros.repository.search.AnexoSearchRepository;
import br.com.basis.madre.cadastros.service.dto.AnexoDTO;
import br.com.basis.madre.cadastros.service.mapper.AnexoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Anexo.
 */
@Service
@Transactional
public class AnexoServiceImpl implements AnexoService {

    private final Logger log = LoggerFactory.getLogger(AnexoServiceImpl.class);

    private final AnexoRepository anexoRepository;

    private final AnexoMapper anexoMapper;

    private final AnexoSearchRepository anexoSearchRepository;

    public AnexoServiceImpl(AnexoRepository anexoRepository, AnexoMapper anexoMapper, AnexoSearchRepository anexoSearchRepository) {
        this.anexoRepository = anexoRepository;
        this.anexoMapper = anexoMapper;
        this.anexoSearchRepository = anexoSearchRepository;
    }

    /**
     * Save a anexo.
     *
     * @param anexoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AnexoDTO save(AnexoDTO anexoDTO) {
        log.debug("Request to save Anexo : {}", anexoDTO);
        Anexo anexo = anexoMapper.toEntity(anexoDTO);
        anexo = anexoRepository.save(anexo);
        AnexoDTO result = anexoMapper.toDto(anexo);
        anexoSearchRepository.save(anexo);
        return result;
    }

    /**
     * Get all the anexos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AnexoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Anexos");
        return anexoRepository.findAll(pageable)
            .map(anexoMapper::toDto);
    }

    /**
     * Get one anexo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AnexoDTO findOne(Long id) {
        log.debug("Request to get Anexo : {}", id);
        Anexo anexo = anexoRepository.findOne(id);
        return anexoMapper.toDto(anexo);
    }

    /**
     * Delete the anexo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Anexo : {}", id);
        anexoRepository.delete(id);
        anexoSearchRepository.delete(id);
    }

    /**
     * Search for the anexo corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AnexoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Anexos for query {}", query);
        Page<Anexo> result = anexoSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(anexoMapper::toDto);
    }
}
