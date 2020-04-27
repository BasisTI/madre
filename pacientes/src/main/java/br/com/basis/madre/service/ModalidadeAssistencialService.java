package br.com.basis.madre.service;

import br.com.basis.madre.domain.ModalidadeAssistencial;
import br.com.basis.madre.repository.ModalidadeAssistencialRepository;
import br.com.basis.madre.repository.search.ModalidadeAssistencialSearchRepository;
import br.com.basis.madre.service.dto.ModalidadeAssistencialDTO;
import br.com.basis.madre.service.mapper.ModalidadeAssistencialMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link ModalidadeAssistencial}.
 */
@RequiredArgsConstructor
@Service
@Transactional
public class ModalidadeAssistencialService {
    private final Logger log = LoggerFactory.getLogger(ModalidadeAssistencialService.class);
    private final ModalidadeAssistencialRepository modalidadeAssistencialRepository;
    private final ModalidadeAssistencialMapper modalidadeAssistencialMapper;
    private final ModalidadeAssistencialSearchRepository modalidadeAssistencialSearchRepository;

    /**
     * Save a modalidadeAssistencial.
     *
     * @param modalidadeAssistencialDTO the entity to save.
     * @return the persisted entity.
     */
    public ModalidadeAssistencialDTO save(ModalidadeAssistencialDTO modalidadeAssistencialDTO) {
        log.debug("Request to save ModalidadeAssistencial : {}", modalidadeAssistencialDTO);
        ModalidadeAssistencial modalidadeAssistencial = modalidadeAssistencialMapper.toEntity(modalidadeAssistencialDTO);
        modalidadeAssistencial = modalidadeAssistencialRepository.save(modalidadeAssistencial);
        ModalidadeAssistencialDTO result = modalidadeAssistencialMapper.toDto(modalidadeAssistencial);
        modalidadeAssistencialSearchRepository.save(modalidadeAssistencial);
        return result;
    }

    /**
     * Get all the modalidadeAssistencials.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ModalidadeAssistencialDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ModalidadeAssistencials");
        return modalidadeAssistencialRepository.findAll(pageable)
            .map(modalidadeAssistencialMapper::toDto);
    }


    /**
     * Get one modalidadeAssistencial by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ModalidadeAssistencialDTO> findOne(Long id) {
        log.debug("Request to get ModalidadeAssistencial : {}", id);
        return modalidadeAssistencialRepository.findById(id)
            .map(modalidadeAssistencialMapper::toDto);
    }

    /**
     * Delete the modalidadeAssistencial by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ModalidadeAssistencial : {}", id);
        modalidadeAssistencialRepository.deleteById(id);
        modalidadeAssistencialSearchRepository.deleteById(id);
    }

    /**
     * Search for the modalidadeAssistencial corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ModalidadeAssistencialDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ModalidadeAssistencials for query {}", query);
        return modalidadeAssistencialSearchRepository.search(queryStringQuery(query), pageable)
            .map(modalidadeAssistencialMapper::toDto);
    }
}
