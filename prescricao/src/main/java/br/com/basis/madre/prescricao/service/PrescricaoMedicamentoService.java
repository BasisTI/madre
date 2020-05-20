package br.com.basis.madre.prescricao.service;

import br.com.basis.madre.prescricao.domain.ItemPrescricaoMedicamento;
import br.com.basis.madre.prescricao.domain.PrescricaoMedicamento;
import br.com.basis.madre.prescricao.repository.PrescricaoMedicamentoRepository;
import br.com.basis.madre.prescricao.repository.search.PrescricaoMedicamentoSearchRepository;
import br.com.basis.madre.prescricao.service.dto.PrescricaoMedicamentoDTO;
import br.com.basis.madre.prescricao.service.mapper.PrescricaoMedicamentoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link PrescricaoMedicamento}.
 */
@Service
@Transactional
public class PrescricaoMedicamentoService {

    private final Logger log = LoggerFactory.getLogger(PrescricaoMedicamentoService.class);

    private final PrescricaoMedicamentoRepository prescricaoMedicamentoRepository;

    private final PrescricaoMedicamentoMapper prescricaoMedicamentoMapper;

    private final PrescricaoMedicamentoSearchRepository prescricaoMedicamentoSearchRepository;

    public PrescricaoMedicamentoService(PrescricaoMedicamentoRepository prescricaoMedicamentoRepository, PrescricaoMedicamentoMapper prescricaoMedicamentoMapper, PrescricaoMedicamentoSearchRepository prescricaoMedicamentoSearchRepository) {
        this.prescricaoMedicamentoRepository = prescricaoMedicamentoRepository;
        this.prescricaoMedicamentoMapper = prescricaoMedicamentoMapper;
        this.prescricaoMedicamentoSearchRepository = prescricaoMedicamentoSearchRepository;
    }

    /**
     * Save a prescricaoMedicamento.
     *
     * @param prescricaoMedicamentoDTO the entity to save.
     * @return the persisted entity.
     */
    public PrescricaoMedicamentoDTO save(PrescricaoMedicamentoDTO prescricaoMedicamentoDTO) {
        log.debug("Request to save PrescricaoMedicamento : {}", prescricaoMedicamentoDTO);
        PrescricaoMedicamento prescricaoMedicamento = prescricaoMedicamentoMapper.toEntity(prescricaoMedicamentoDTO);
        for (ItemPrescricaoMedicamento item : prescricaoMedicamento.getItemPrescricaoMedicamentos()) {
        	item.setPrescricaoMedicamento(prescricaoMedicamento);
			
		}
        prescricaoMedicamento = prescricaoMedicamentoRepository.save(prescricaoMedicamento);
        PrescricaoMedicamentoDTO result = prescricaoMedicamentoMapper.toDto(prescricaoMedicamento);
        prescricaoMedicamentoSearchRepository.save(prescricaoMedicamento);
        return result;
    }

    /**
     * Get all the prescricaoMedicamentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PrescricaoMedicamentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PrescricaoMedicamentos");
        return prescricaoMedicamentoRepository.findAll(pageable)
            .map(prescricaoMedicamentoMapper::toDto);
    }


    /**
     * Get one prescricaoMedicamento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PrescricaoMedicamentoDTO> findOne(Long id) {
        log.debug("Request to get PrescricaoMedicamento : {}", id);
        return prescricaoMedicamentoRepository.findById(id)
            .map(prescricaoMedicamentoMapper::toDto);
    }

    /**
     * Delete the prescricaoMedicamento by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PrescricaoMedicamento : {}", id);
        prescricaoMedicamentoRepository.deleteById(id);
        prescricaoMedicamentoSearchRepository.deleteById(id);
    }

    /**
     * Search for the prescricaoMedicamento corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PrescricaoMedicamentoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PrescricaoMedicamentos for query {}", query);
        return prescricaoMedicamentoSearchRepository.search(queryStringQuery(query), pageable)
            .map(prescricaoMedicamentoMapper::toDto);
    }
}
