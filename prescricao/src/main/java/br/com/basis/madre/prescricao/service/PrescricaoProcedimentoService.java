package br.com.basis.madre.prescricao.service;

import br.com.basis.madre.prescricao.domain.ItemPrescricaoProcedimento;
import br.com.basis.madre.prescricao.domain.PrescricaoProcedimento;
import br.com.basis.madre.prescricao.repository.PrescricaoProcedimentoRepository;
import br.com.basis.madre.prescricao.repository.search.PrescricaoProcedimentoDTOSearchRepository;
import br.com.basis.madre.prescricao.repository.search.PrescricaoProcedimentoSearchRepository;
import br.com.basis.madre.prescricao.service.dto.PrescricaoProcedimentoDTO;
import br.com.basis.madre.prescricao.service.mapper.PrescricaoProcedimentoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link PrescricaoProcedimento}.
 */
@Service
@Transactional
public class PrescricaoProcedimentoService {

	private final Logger log = LoggerFactory.getLogger(PrescricaoProcedimentoService.class);

	private final PrescricaoProcedimentoRepository prescricaoProcedimentoRepository;

	private final PrescricaoProcedimentoMapper prescricaoProcedimentoMapper;

	private final PrescricaoProcedimentoSearchRepository prescricaoProcedimentoSearchRepository;
	
	private final PrescricaoProcedimentoDTOSearchRepository prescricaoProcedimentoDTOSearchRepository;

	public PrescricaoProcedimentoService(PrescricaoProcedimentoRepository prescricaoProcedimentoRepository,
			PrescricaoProcedimentoMapper prescricaoProcedimentoMapper,
			PrescricaoProcedimentoSearchRepository prescricaoProcedimentoSearchRepository,
			PrescricaoProcedimentoDTOSearchRepository prescricaoProcedimentoDTOSearchRepository) {
		this.prescricaoProcedimentoRepository = prescricaoProcedimentoRepository;
		this.prescricaoProcedimentoMapper = prescricaoProcedimentoMapper;
		this.prescricaoProcedimentoSearchRepository = prescricaoProcedimentoSearchRepository;
		this.prescricaoProcedimentoDTOSearchRepository = prescricaoProcedimentoDTOSearchRepository;
	}

	/**
	 * Save a prescricaoProcedimento.
	 *
	 * @param prescricaoProcedimentoDTO the entity to save.
	 * @return the persisted entity.
	 */
	public PrescricaoProcedimentoDTO save(PrescricaoProcedimentoDTO prescricaoProcedimentoDTO) {
		log.debug("Request to save PrescricaoProcedimento : {}", prescricaoProcedimentoDTO);
		PrescricaoProcedimento prescricaoProcedimento = prescricaoProcedimentoMapper
				.toEntity(prescricaoProcedimentoDTO);
		for (ItemPrescricaoProcedimento item : prescricaoProcedimento.getItemPrescricaoProcedimentos()) {
			item.setPrescricaoProcedimento(prescricaoProcedimento);
		}
		prescricaoProcedimento = prescricaoProcedimentoRepository.save(prescricaoProcedimento);
		PrescricaoProcedimentoDTO result = prescricaoProcedimentoMapper.toDto(prescricaoProcedimento);
		prescricaoProcedimentoDTOSearchRepository.save(result);
		return result;
	}

	/**
	 * Get all the prescricaoProcedimentos.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	@Transactional(readOnly = true)
	public Page<PrescricaoProcedimentoDTO> findAll(Pageable pageable) {
		log.debug("Request to get all PrescricaoProcedimentos");
		return prescricaoProcedimentoRepository.findAll(pageable).map(prescricaoProcedimentoMapper::toDto);
	}

	/**
	 * Get one prescricaoProcedimento by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Transactional(readOnly = true)
	public Optional<PrescricaoProcedimentoDTO> findOne(Long id) {
		log.debug("Request to get PrescricaoProcedimento : {}", id);
		return prescricaoProcedimentoRepository.findById(id).map(prescricaoProcedimentoMapper::toDto);
	}

	/**
	 * Delete the prescricaoProcedimento by id.
	 *
	 * @param id the id of the entity.
	 */
	public void delete(Long id) {
		log.debug("Request to delete PrescricaoProcedimento : {}", id);
		prescricaoProcedimentoRepository.deleteById(id);
		prescricaoProcedimentoSearchRepository.deleteById(id);
	}

	/**
	 * Search for the prescricaoProcedimento corresponding to the query.
	 *
	 * @param query    the query of the search.
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	@Transactional(readOnly = true)
	public Page<PrescricaoProcedimentoDTO> search(String query, Pageable pageable) {
		log.debug("Request to search for a page of PrescricaoProcedimentos for query {}", query);
		return prescricaoProcedimentoSearchRepository.search(queryStringQuery(query), pageable)
				.map(prescricaoProcedimentoMapper::toDto);
	}
}
