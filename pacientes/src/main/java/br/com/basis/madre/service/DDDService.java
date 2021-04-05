package br.com.basis.madre.service;

import br.com.basis.madre.domain.DDD;
import br.com.basis.madre.repository.DDDRepository;
import br.com.basis.madre.repository.search.DDDSearchRepository;
import br.com.basis.madre.service.dto.DDD_DTO;
import br.com.basis.madre.service.mapper.DDDMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional
public class DDDService {

    private final Logger log = LoggerFactory.getLogger(DDDService.class);

    private final DDDRepository dddRepository;

    private final DDDMapper dddMapper;

    private final DDDSearchRepository dddSearchRepository;

    public DDDService(DDDRepository dddRepository, DDDMapper dddMapper, DDDSearchRepository dddSearchRepository) {
        this.dddRepository = dddRepository;
        this.dddMapper = dddMapper;
        this.dddSearchRepository = dddSearchRepository;
    }


    /**
     * Save a ddd.
     *
     * @param dddDTO the entity to save.
     * @return the persisted entity.
     */

    public DDD_DTO save(DDD_DTO dddDTO) {
        log.debug("Request to save DDD : {}", dddDTO);
        DDD ddd = dddMapper.toEntity(dddDTO);
        ddd = dddRepository.save(ddd);
        DDD_DTO result = dddMapper.toDto(ddd);
        dddSearchRepository.save(ddd);
        return result;
    }

    @Transactional(readOnly = true)
    public Page<DDD_DTO> findAll(DDD_DTO dddDTO, Pageable pageable) {
        log.debug("Request to get all DDDs");
        return dddRepository.findAll(
            Example.of(dddMapper.toEntity(dddDTO),
                ExampleMatcher.matching().withIgnoreCase().withStringMatcher(
                    ExampleMatcher.StringMatcher.CONTAINING)
            ),
            pageable
        ).map(dddMapper::toDto);
    }

    /**
     * Get all the DDDs.
     *
     * @param pageable the pagination information.
     * @return the list of DDDs.
     */

    @Transactional(readOnly = true)
    public Page<DDD_DTO> findAll(Pageable pageable) {
        log.debug("Request to get all DDDs");
        return dddRepository.findAll(pageable)
            .map(dddMapper::toDto);
    }

    /**
     * Get one DDD by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */

    @Transactional(readOnly = true)
    public Optional<DDD_DTO> findOne(Long id) {
        log.debug("Request to get DDD : {}", id);
        return dddRepository.findById(id)
            .map(dddMapper::toDto);
    }

}
