package br.com.basis.madre.service;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import br.com.basis.madre.domain.CID;
import br.com.basis.madre.repository.CIDRepository;
import br.com.basis.madre.repository.search.CIDSearchRepository;
import br.com.basis.madre.service.dto.CidDTO;
import br.com.basis.madre.service.mapper.CIDMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class CIDService {

    private final Logger log = LoggerFactory.getLogger(CIDService.class);

    private final CIDRepository cidRepository;

    private final CIDMapper cidMapper;

    private final CIDSearchRepository cidSearchRepository;


    /**
     * Save a cID.
     *
     * @param cIDDTO the entity to save.
     * @return the persisted entity.
     */
    public CidDTO save(CidDTO cIDDTO) {
        log.debug("Request to save CID : {}", cIDDTO);
        CID cID = cidMapper.toEntity(cIDDTO);
        cID = cidRepository.save(cID);
        CidDTO result = cidMapper.toDto(cID);
        cidSearchRepository.save(cID);
        return result;
    }

    /**
     * Get all the cIDS.
     *
     * @param cidDTO
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CidDTO> findAll(CidDTO cidDTO, Pageable pageable) {
        log.debug("Request to get all CIDS");
        return cidRepository.findAll(
            Example.of(cidMapper
                .toEntity(cidDTO), ExampleMatcher.matching().withIgnoreCase().withStringMatcher(
                StringMatcher.CONTAINING))
            , pageable)
            .map(cid -> {
                System.out.println(cid);
                return cidMapper.toDto(cid);
            });
    }

    /**
     * Get one cID by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CidDTO> findOne(Long id) {
        log.debug("Request to get CID : {}", id);
        return cidRepository.findById(id)
            .map(cidMapper::toDto);
    }

    /**
     * Delete the cID by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CID : {}", id);
        cidRepository.deleteById(id);
        cidSearchRepository.deleteById(id);
    }

    /**
     * Search for the cID corresponding to the query.
     *
     * @param query    the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CidDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CIDS for query {}", query);
        return cidSearchRepository.search(queryStringQuery(query), pageable)
            .map(cidMapper::toDto);
    }

    public Page<CidDTO> getCidsPais(Pageable pageable) {
        return cidRepository.findByPaiIdNull(pageable).map(cidMapper::toDto);
    }

    public Page<CidDTO> getCidsPais(CidDTO cidDTO, Pageable pageable) {
        return cidRepository.findByPaiIdNull(pageable).map(cidMapper::toDto);
    }

    public Page<CidDTO> getCidsFilhos(CidDTO cidDTO, Pageable pageable) {
        return cidRepository.findByPaiIdNotNull(pageable).map(cidMapper::toDto);
    }

    public Page<CidDTO> getCidsFilhos(Pageable pageable) {
        return cidRepository.findByPaiIdNotNull(pageable).map(cidMapper::toDto);
    }

}
