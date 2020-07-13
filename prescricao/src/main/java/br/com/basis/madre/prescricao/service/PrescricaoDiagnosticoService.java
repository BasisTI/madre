package br.com.basis.madre.prescricao.service;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.basis.madre.prescricao.domain.ItemPrescricaoDiagnostico;
import br.com.basis.madre.prescricao.domain.PrescricaoDiagnostico;
import br.com.basis.madre.prescricao.repository.PrescricaoDiagnosticoRepository;
import br.com.basis.madre.prescricao.repository.search.PrescricaoDiagnosticoDTOSearchRepository;
import br.com.basis.madre.prescricao.repository.search.PrescricaoDiagnosticoSearchRepository;
import br.com.basis.madre.prescricao.service.dto.PrescricaoDiagnosticoDTO;
import br.com.basis.madre.prescricao.service.mapper.PrescricaoDiagnosticoMapper;

/**
 * Service Implementation for managing {@link PrescricaoDiagnostico}.
 */
@Service
@Transactional
public class PrescricaoDiagnosticoService {

	private final Logger log = LoggerFactory.getLogger(PrescricaoDiagnosticoService.class);

	private final PrescricaoDiagnosticoRepository prescricaoDiagnosticoRepository;

	private final PrescricaoDiagnosticoMapper prescricaoDiagnosticoMapper;

	private final PrescricaoDiagnosticoSearchRepository prescricaoDiagnosticoSearchRepository;

	private final PrescricaoDiagnosticoDTOSearchRepository prescricaoDiagnosticoDTOSearchRepository;

	public PrescricaoDiagnosticoService(PrescricaoDiagnosticoRepository prescricaoDiagnosticoRepository,
			PrescricaoDiagnosticoMapper prescricaoDiagnosticoMapper,
			PrescricaoDiagnosticoSearchRepository prescricaoDiagnosticoSearchRepository,
			PrescricaoDiagnosticoDTOSearchRepository prescricaoDiagnosticoDTOSearchRepository) {
		this.prescricaoDiagnosticoRepository = prescricaoDiagnosticoRepository;
		this.prescricaoDiagnosticoMapper = prescricaoDiagnosticoMapper;
		this.prescricaoDiagnosticoSearchRepository = prescricaoDiagnosticoSearchRepository;
		this.prescricaoDiagnosticoDTOSearchRepository = prescricaoDiagnosticoDTOSearchRepository;
	}

	/**
	 * Save a prescricaoDiagnostico.
	 *
	 * @param prescricaoDiagnosticoDTO the entity to save.
	 * @return the persisted entity.
	 */
	public PrescricaoDiagnosticoDTO save(PrescricaoDiagnosticoDTO prescricaoDiagnosticoDTO) {
		log.debug("Request to save PrescricaoDiagnostico : {}", prescricaoDiagnosticoDTO);

		PrescricaoDiagnostico prescricaoDiagnostico = prescricaoDiagnosticoMapper.toEntity(prescricaoDiagnosticoDTO);
		for (ItemPrescricaoDiagnostico item : prescricaoDiagnostico.getItemPrescricaoDiagnosticos()) {
			item.setPrescricaoDiagnostico(prescricaoDiagnostico);
		}
		prescricaoDiagnostico = prescricaoDiagnosticoRepository.save(prescricaoDiagnostico);
		PrescricaoDiagnosticoDTO result = prescricaoDiagnosticoMapper.toDto(prescricaoDiagnostico);
		prescricaoDiagnosticoDTOSearchRepository.save(result);
		return result;
	}

	/**
	 * Get all the prescricaoDiagnosticos.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	@Transactional(readOnly = true)
	public Page<PrescricaoDiagnosticoDTO> findAll(Pageable pageable) {
		log.debug("Request to get all PrescricaoDiagnosticos");
		return prescricaoDiagnosticoRepository.findAll(pageable).map(prescricaoDiagnosticoMapper::toDto);
	}

	/**
	 * Get one prescricaoDiagnostico by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Transactional(readOnly = true)
	public Optional<PrescricaoDiagnosticoDTO> findOne(Long id) {
		log.debug("Request to get PrescricaoDiagnostico : {}", id);
		return prescricaoDiagnosticoRepository.findById(id).map(prescricaoDiagnosticoMapper::toDto);
	}

	/**
	 * Delete the prescricaoDiagnostico by id.
	 *
	 * @param id the id of the entity.
	 */
	public void delete(Long id) {
		log.debug("Request to delete PrescricaoDiagnostico : {}", id);
		prescricaoDiagnosticoRepository.deleteById(id);
		prescricaoDiagnosticoSearchRepository.deleteById(id);
	}

	/**
	 * Search for the prescricaoDiagnostico corresponding to the query.
	 *
	 * @param query    the query of the search.
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	@Transactional(readOnly = true)
	public Page<PrescricaoDiagnosticoDTO> search(String query, Pageable pageable) {
		log.debug("Request to search for a page of PrescricaoDiagnosticos for query {}", query);
		return prescricaoDiagnosticoSearchRepository.search(queryStringQuery(query), pageable)
				.map(prescricaoDiagnosticoMapper::toDto);
	}
}
