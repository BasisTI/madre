package br.com.basis.madre.service;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import br.com.basis.madre.domain.LocalDeAtendimento;
import br.com.basis.madre.repository.LocalDeAtendimentoRepository;
import br.com.basis.madre.repository.search.LocalDeAtendimentoSearchRepository;
import br.com.basis.madre.service.dto.LocalDeAtendimentoDTO;
import br.com.basis.madre.service.mapper.LocalDeAtendimentoMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class LocalDeAtendimentoService {

    private final Logger log = LoggerFactory.getLogger(LocalDeAtendimentoService.class);

    private final LocalDeAtendimentoRepository localDeAtendimentoRepository;

    private final LocalDeAtendimentoMapper localDeAtendimentoMapper;

    private final LocalDeAtendimentoSearchRepository localDeAtendimentoSearchRepository;

    /**
     * Save a localDeAtendimento.
     *
     * @param localDeAtendimentoDTO the entity to save.
     * @return the persisted entity.
     */
    public LocalDeAtendimentoDTO save(LocalDeAtendimentoDTO localDeAtendimentoDTO) {
        log.debug("Request to save LocalDeAtendimento : {}", localDeAtendimentoDTO);
        LocalDeAtendimento localDeAtendimento = localDeAtendimentoMapper
            .toEntity(localDeAtendimentoDTO);
        localDeAtendimento = localDeAtendimentoRepository.save(localDeAtendimento);
        LocalDeAtendimentoDTO result = localDeAtendimentoMapper.toDto(localDeAtendimento);
        localDeAtendimentoSearchRepository.save(localDeAtendimento);
        return result;
    }

    /**
     * Get all the localDeAtendimentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LocalDeAtendimentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LocalDeAtendimentos");
        return localDeAtendimentoRepository.findAll(pageable)
            .map(localDeAtendimentoMapper::toDto);
    }


    /**
     * Get one localDeAtendimento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LocalDeAtendimentoDTO> findOne(Long id) {
        log.debug("Request to get LocalDeAtendimento : {}", id);
        return localDeAtendimentoRepository.findById(id)
            .map(localDeAtendimentoMapper::toDto);
    }

    /**
     * Delete the localDeAtendimento by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LocalDeAtendimento : {}", id);
        localDeAtendimentoRepository.deleteById(id);
        localDeAtendimentoSearchRepository.deleteById(id);
    }

    /**
     * Search for the localDeAtendimento corresponding to the query.
     *
     * @param query    the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LocalDeAtendimentoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of LocalDeAtendimentos for query {}", query);
        return localDeAtendimentoSearchRepository.search(queryStringQuery(query), pageable)
            .map(localDeAtendimentoMapper::toDto);
    }

}
