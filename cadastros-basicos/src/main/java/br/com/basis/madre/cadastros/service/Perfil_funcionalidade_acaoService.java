package br.com.basis.madre.cadastros.service;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.basis.madre.cadastros.domain.Perfil_funcionalidade_acao;
import br.com.basis.madre.cadastros.repository.Perfil_funcionalidade_acaoRepository;
import br.com.basis.madre.cadastros.repository.search.Perfil_funcionalidade_acaoSearchRepository;

/**
 * Service Implementation for managing Perfil_funcionalidade_acao.
 */
@Service
@Transactional
public class Perfil_funcionalidade_acaoService {

	private final Logger log = LoggerFactory.getLogger(Perfil_funcionalidade_acaoService.class);

	private final Perfil_funcionalidade_acaoRepository perfil_funcionalidade_acaoRepository;

	private final Perfil_funcionalidade_acaoSearchRepository perfil_funcionalidade_acaoSearchRepository;

	public Perfil_funcionalidade_acaoService(Perfil_funcionalidade_acaoRepository perfil_funcionalidade_acaoRepository,
			Perfil_funcionalidade_acaoSearchRepository perfil_funcionalidade_acaoSearchRepository) {
		this.perfil_funcionalidade_acaoRepository = perfil_funcionalidade_acaoRepository;
		this.perfil_funcionalidade_acaoSearchRepository = perfil_funcionalidade_acaoSearchRepository;
	}

	/**
	 * Save a perfil_funcionalidade_acao.
	 *
	 * @param perfil_funcionalidade_acao
	 *            the entity to save
	 * @return the persisted entity
	 */
	public Perfil_funcionalidade_acao save(Perfil_funcionalidade_acao perfil_funcionalidade_acao) {
		log.debug("Request to save Perfil_funcionalidade_acao : {}", perfil_funcionalidade_acao);
		Perfil_funcionalidade_acao result = perfil_funcionalidade_acaoRepository.save(perfil_funcionalidade_acao);
		perfil_funcionalidade_acaoSearchRepository.save(result);
		return result;
	}

	/**
	 * Get all the perfil_funcionalidade_acaos.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	public Page<Perfil_funcionalidade_acao> findAll(Pageable pageable) {
		log.debug("Request to get all Perfil_funcionalidade_acaos");
		return perfil_funcionalidade_acaoRepository.findAll(pageable);
	}

	/**
	 * Get one perfil_funcionalidade_acao by id.
	 *
	 * @param id
	 *            the id of the entity
	 * @return the entity
	 */
	@Transactional(readOnly = true)
	public Perfil_funcionalidade_acao findOne(Long id) {
		log.debug("Request to get Perfil_funcionalidade_acao : {}", id);
		return perfil_funcionalidade_acaoRepository.findOne(id);
	}

	/**
	 * Delete the perfil_funcionalidade_acao by id.
	 *
	 * @param id
	 *            the id of the entity
	 */
	public void delete(Long id) {
		log.debug("Request to delete Perfil_funcionalidade_acao : {}", id);
		perfil_funcionalidade_acaoRepository.delete(id);
		perfil_funcionalidade_acaoSearchRepository.delete(id);
	}

	/**
	 * Search for the perfil_funcionalidade_acao corresponding to the query.
	 *
	 * @param query
	 *            the query of the search
	 * @param pageable
	 *            the pagination information
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	public Page<Perfil_funcionalidade_acao> search(String query, Pageable pageable) {
		log.debug("Request to search for a page of Perfil_funcionalidade_acaos for query {}", query);
		Page<Perfil_funcionalidade_acao> result = perfil_funcionalidade_acaoSearchRepository
				.search(queryStringQuery(query), pageable);
		return result;
	}
}
