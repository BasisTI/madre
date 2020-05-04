package br.com.basis.madre.service;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import br.com.basis.madre.domain.Hospital;
import br.com.basis.madre.repository.HospitalRepository;
import br.com.basis.madre.repository.search.HospitalSearchRepository;
import br.com.basis.madre.service.dto.HospitalDTO;
import br.com.basis.madre.service.mapper.HospitalMapper;
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
public class HospitalService {

    private final Logger log = LoggerFactory.getLogger(HospitalService.class);

    private final HospitalRepository hospitalRepository;

    private final HospitalMapper hospitalMapper;

    private final HospitalSearchRepository hospitalSearchRepository;

    /**
     * Save a hospital.
     *
     * @param hospitalDTO the entity to save.
     * @return the persisted entity.
     */
    public HospitalDTO save(HospitalDTO hospitalDTO) {
        log.debug("Request to save Hospital : {}", hospitalDTO);
        Hospital hospital = hospitalMapper.toEntity(hospitalDTO);
        hospital = hospitalRepository.save(hospital);
        HospitalDTO result = hospitalMapper.toDto(hospital);
        hospitalSearchRepository.save(hospital);
        return result;
    }

    /**
     * Get all the hospitals.
     *
     * @param hospitalDTO
     * @param pageable    the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<HospitalDTO> findAll(HospitalDTO hospitalDTO,
        Pageable pageable) {
        log.debug("Request to get all Hospitals");
        return hospitalRepository.findAll(
            Example.of(hospitalMapper.toEntity(hospitalDTO),
                ExampleMatcher.matching().withIgnoreCase().withStringMatcher(
                    StringMatcher.CONTAINING))
            , pageable)
            .map(hospitalMapper::toDto);
    }


    /**
     * Get one hospital by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<HospitalDTO> findOne(Long id) {
        log.debug("Request to get Hospital : {}", id);
        return hospitalRepository.findById(id)
            .map(hospitalMapper::toDto);
    }

    /**
     * Delete the hospital by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Hospital : {}", id);
        hospitalRepository.deleteById(id);
        hospitalSearchRepository.deleteById(id);
    }

    /**
     * Search for the hospital corresponding to the query.
     *
     * @param query    the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<HospitalDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Hospitals for query {}", query);
        return hospitalSearchRepository.search(queryStringQuery(query), pageable)
            .map(hospitalMapper::toDto);
    }

}
