package br.com.basis.madre.cadastros.service;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import br.com.basis.madre.cadastros.domain.PerfilFuncionalidadeAcao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.basis.madre.cadastros.repository.PerfilFuncionalidadeAcaoRepository;
import br.com.basis.madre.cadastros.repository.search.PerfilFuncionalidadeAcaoSearchRepository;

/**
 * Service Implementation for managing PerfilFuncionalidadeAcao.
 */
@Service
@Transactional
public class PerfilFuncionalidadeAcaoService {

	private final Logger log = LoggerFactory.getLogger(PerfilFuncionalidadeAcaoService.class);

	private final PerfilFuncionalidadeAcaoRepository perfilFuncionalidadeAcaoRepository;

	private final PerfilFuncionalidadeAcaoSearchRepository perfilFuncionalidadeAcaoSearchRepository;

	public PerfilFuncionalidadeAcaoService(PerfilFuncionalidadeAcaoRepository perfilFuncionalidadeAcaoRepository,
                                           PerfilFuncionalidadeAcaoSearchRepository perfilFuncionalidadeAcaoSearchRepository) {
		this.perfilFuncionalidadeAcaoRepository = perfilFuncionalidadeAcaoRepository;
		this.perfilFuncionalidadeAcaoSearchRepository = perfilFuncionalidadeAcaoSearchRepository;
	}

	/**
	 * Save a perfil_funcionalidade_acao.
	 *
	 * @param perfil_funcionalidade_acao
	 *            the entity to save
	 * @return the persisted entity
	 */
	public PerfilFuncionalidadeAcao save(PerfilFuncionalidadeAcao perfil_funcionalidade_acao) {
		log.debug("Request to save PerfilFuncionalidadeAcao : {}", perfil_funcionalidade_acao);
		PerfilFuncionalidadeAcao result = perfilFuncionalidadeAcaoRepository.save(perfil_funcionalidade_acao);
		perfilFuncionalidadeAcaoSearchRepository.save(result);
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
	public Page<PerfilFuncionalidadeAcao> findAll(Pageable pageable) {
		log.debug("Request to get all Perfil_funcionalidade_acaos");
		return perfilFuncionalidadeAcaoRepository.findAll(pageable);
	}

	/**
	 * Get one perfil_funcionalidade_acao by id.
	 *
	 * @param id
	 *            the id of the entity
	 * @return the entity
	 */
	@Transactional(readOnly = true)
	public PerfilFuncionalidadeAcao findOne(Long id) {
		log.debug("Request to get PerfilFuncionalidadeAcao : {}", id);
		return perfilFuncionalidadeAcaoRepository.findOne(id);
	}

	/**
	 * Delete the perfil_funcionalidade_acao by id.
	 *
	 * @param id
	 *            the id of the entity
	 */
	public void delete(Long id) {
		log.debug("Request to delete PerfilFuncionalidadeAcao : {}", id);
		perfilFuncionalidadeAcaoRepository.delete(id);
		perfilFuncionalidadeAcaoSearchRepository.delete(id);
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
	public Page<PerfilFuncionalidadeAcao> search(String query, Pageable pageable) {
		log.debug("Request to search for a page of Perfil_funcionalidade_acaos for query {}", query);
		Page<PerfilFuncionalidadeAcao> result = perfilFuncionalidadeAcaoSearchRepository
				.search(queryStringQuery(query), pageable);
		return result;
	}
}
