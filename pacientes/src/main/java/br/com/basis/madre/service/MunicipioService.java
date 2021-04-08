package br.com.basis.madre.service;

import br.com.basis.madre.domain.Municipio;
import br.com.basis.madre.domain.UF;
import br.com.basis.madre.repository.MunicipioRepository;
import br.com.basis.madre.repository.search.MunicipioSearchRepository;
import br.com.basis.madre.service.dto.MunicipioDTO;
import br.com.basis.madre.service.mapper.MunicipioMapper;
import br.com.basis.madre.service.projection.MunicipioUF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing {@link Municipio}.
 */
@Service
@Transactional
public class MunicipioService {

    private final Logger log = LoggerFactory.getLogger(MunicipioService.class);

    private final MunicipioRepository municipioRepository;

    private final MunicipioMapper municipioMapper;

    private final MunicipioSearchRepository municipioSearchRepository;

    public MunicipioService(MunicipioRepository municipioRepository, MunicipioMapper municipioMapper, MunicipioSearchRepository municipioSearchRepository) {
        this.municipioRepository = municipioRepository;
        this.municipioMapper = municipioMapper;
        this.municipioSearchRepository = municipioSearchRepository;
    }

    /**
     * Save a municipio.
     *
     * @param municipioDTO the entity to save.
     * @return the persisted entity.
     */
    public MunicipioDTO save(MunicipioDTO municipioDTO) {
        log.debug("Request to save Municipio : {}", municipioDTO);
        Municipio municipio = municipioMapper.toEntity(municipioDTO);
        municipio = municipioRepository.save(municipio);
        MunicipioDTO result = municipioMapper.toDto(municipio);
        municipioSearchRepository.save(municipio);
        return result;
    }

    /**
     * Get all the municipios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MunicipioDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Municipios");
        return municipioRepository.findAll(pageable)
            .map(municipioMapper::toDto);
    }

    /**
     * Get one municipio by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MunicipioDTO> findOne(Long id) {
        log.debug("Request to get Municipio : {}", id);
        return municipioRepository.findById(id)
            .map(municipioMapper::toDto);
    }

    /**
     * Delete the municipio by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Municipio : {}", id);
        municipioRepository.deleteById(id);
        municipioSearchRepository.deleteById(id);
    }

    /**
     * Search for the municipio corresponding to the query.
     *
     * @param query    the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MunicipioDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Municipios for query {}", query);
        return municipioSearchRepository.search(queryStringQuery(query), pageable)
            .map(municipioMapper::toDto);
    }

    /**
     *  Write documentation
     */

    public Page<MunicipioUF> findAllProjectedMunicipioUFByNaturalidade(Long idUf, String nome, Pageable pageable) {
        UF uf = new UF();
        uf.setId(idUf);
        return municipioRepository.findByNomeContainsIgnoreCaseAndUf(nome,uf,pageable);
    }

}
