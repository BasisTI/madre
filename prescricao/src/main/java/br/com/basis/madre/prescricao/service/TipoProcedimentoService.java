package br.com.basis.madre.prescricao.service;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.basis.madre.prescricao.domain.TipoProcedimento;
import br.com.basis.madre.prescricao.repository.TipoProcedimentoRepository;
import br.com.basis.madre.prescricao.repository.search.TipoProcedimentoSearchRepository;
import br.com.basis.madre.prescricao.service.dto.TipoProcedimentoDTO;
import br.com.basis.madre.prescricao.service.mapper.TipoProcedimentoMapper;

/**
 * Service Implementation for managing {@link TipoProcedimento}.
 */
@Service
@Transactional
public class TipoProcedimentoService {

	private final Logger log = LoggerFactory.getLogger(TipoProcedimentoService.class);

	private final TipoProcedimentoRepository tipoProcedimentoRepository;

	private final TipoProcedimentoMapper tipoProcedimentoMapper;

	private final TipoProcedimentoSearchRepository tipoProcedimentoSearchRepository;

	public TipoProcedimentoService(TipoProcedimentoRepository tipoProcedimentoRepository,
			TipoProcedimentoMapper tipoProcedimentoMapper,
			TipoProcedimentoSearchRepository tipoProcedimentoSearchRepository) {
		this.tipoProcedimentoRepository = tipoProcedimentoRepository;
		this.tipoProcedimentoMapper = tipoProcedimentoMapper;
		this.tipoProcedimentoSearchRepository = tipoProcedimentoSearchRepository;
	}

	/**
	 * Save a tipoProcedimento.
	 *
	 * @param tipoProcedimentoDTO the entity to save.
	 * @return the persisted entity.
	 */
	public TipoProcedimentoDTO save(TipoProcedimentoDTO tipoProcedimentoDTO) {
		log.debug("Request to save TipoProcedimento : {}", tipoProcedimentoDTO);
		TipoProcedimento tipoProcedimento = tipoProcedimentoMapper.toEntity(tipoProcedimentoDTO);
		tipoProcedimento = tipoProcedimentoRepository.save(tipoProcedimento);
		TipoProcedimentoDTO result = tipoProcedimentoMapper.toDto(tipoProcedimento);
		tipoProcedimentoSearchRepository.save(tipoProcedimento);
		return result;
	}

	/**
	 * Get all the tipoProcedimentos.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	@Transactional(readOnly = true)
	public Page<TipoProcedimentoDTO> findAll(Pageable pageable) {
		log.debug("Request to get all TipoProcedimentos");
		return tipoProcedimentoRepository.findAll(pageable).map(tipoProcedimentoMapper::toDto);
	}

	@Transactional(readOnly = true)
	public Page<TipoProcedimento> listarTipoProcedimentoDiversos(Pageable pageable) {
		return tipoProcedimentoRepository.listarTipoProcedimentoDiversos(pageable);
		
	}

	@Transactional(readOnly = true)
	public Page<TipoProcedimento> listarTipoProcedimentoCirurgias(Pageable pageable) {
		return tipoProcedimentoRepository.listarTipoProcedimentoCirurgias(pageable);
	
	}

	@Transactional(readOnly = true)
	public Page<TipoProcedimento> listarTipoProcedimentoOsteseProtese(Pageable pageable) {
		return tipoProcedimentoRepository.listarTipoProcedimentoOsteseProtese(pageable);
	
	}

	/**
	 * Get one tipoProcedimento by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Transactional(readOnly = true)
	public Optional<TipoProcedimentoDTO> findOne(Long id) {
		log.debug("Request to get TipoProcedimento : {}", id);
		return tipoProcedimentoRepository.findById(id).map(tipoProcedimentoMapper::toDto);
	}

	/**
	 * Delete the tipoProcedimento by id.
	 *
	 * @param id the id of the entity.
	 */
	public void delete(Long id) {
		log.debug("Request to delete TipoProcedimento : {}", id);
		tipoProcedimentoRepository.deleteById(id);
		tipoProcedimentoSearchRepository.deleteById(id);
	}

	/**
	 * Search for the tipoProcedimento corresponding to the query.
	 *
	 * @param query    the query of the search.
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	@Transactional(readOnly = true)
	public Page<TipoProcedimentoDTO> search(String query, Pageable pageable) {
		log.debug("Request to search for a page of TipoProcedimentos for query {}", query);
		return tipoProcedimentoSearchRepository.search(queryStringQuery(query), pageable)
				.map(tipoProcedimentoMapper::toDto);
	}
}
