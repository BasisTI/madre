package br.com.basis.madre.service;

import br.com.basis.madre.domain.Endereco;
import br.com.basis.madre.repository.EnderecoRepository;
import br.com.basis.madre.repository.search.EnderecoSearchRepository;
import br.com.basis.madre.service.dto.EnderecoDTO;
import br.com.basis.madre.service.mapper.EnderecoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing {@link Endereco}.
 */
@Service
@Transactional
public class EnderecoService {

    private final Logger log = LoggerFactory.getLogger(EnderecoService.class);

    private final EnderecoRepository enderecoRepository;

    private final EnderecoMapper enderecoMapper;

    private final EnderecoSearchRepository enderecoSearchRepository;

    public EnderecoService(EnderecoRepository enderecoRepository, EnderecoMapper enderecoMapper, EnderecoSearchRepository enderecoSearchRepository) {
        this.enderecoRepository = enderecoRepository;
        this.enderecoMapper = enderecoMapper;
        this.enderecoSearchRepository = enderecoSearchRepository;
    }

    /**
     * Save a endereco.
     *
     * @param enderecoDTO the entity to save.
     * @return the persisted entity.
     */
    public EnderecoDTO save(EnderecoDTO enderecoDTO) {
        log.debug("Request to save Endereco : {}", enderecoDTO);
        Endereco endereco = enderecoMapper.toEntity(enderecoDTO);
        endereco = enderecoRepository.save(endereco);
        EnderecoDTO result = enderecoMapper.toDto(endereco);
        enderecoSearchRepository.save(endereco);
        return result;
    }

    /**
     * Get all the enderecos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EnderecoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Enderecos");
        return enderecoRepository.findAll(pageable)
            .map(enderecoMapper::toDto);
    }

    /**
     * Get one endereco by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EnderecoDTO> findOne(Long id) {
        log.debug("Request to get Endereco : {}", id);
        return enderecoRepository.findById(id)
            .map(enderecoMapper::toDto);
    }

    /**
     * Delete the endereco by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Endereco : {}", id);
        enderecoRepository.deleteById(id);
        enderecoSearchRepository.deleteById(id);
    }

    /**
     * Search for the endereco corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EnderecoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Enderecos for query {}", query);
        return enderecoSearchRepository.search(queryStringQuery(query), pageable)
            .map(enderecoMapper::toDto);
    }
}
