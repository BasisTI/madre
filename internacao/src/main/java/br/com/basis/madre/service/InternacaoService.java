package br.com.basis.madre.service;

import br.com.basis.madre.domain.Internacao;
import br.com.basis.madre.repository.InternacaoRepository;
import br.com.basis.madre.repository.search.InternacaoSearchRepository;
import br.com.basis.madre.service.dto.InternacaoDTO;
import br.com.basis.madre.service.mapper.InternacaoMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

@RequiredArgsConstructor
@Service
@Transactional
public class InternacaoService {

    private final Logger log = LoggerFactory.getLogger(InternacaoService.class);

    private final InternacaoRepository internacaoRepository;

    private final InternacaoMapper internacaoMapper;

    private final InternacaoSearchRepository internacaoSearchRepository;

    private final EventoLeitoService eventoLeitoService;

    public InternacaoDTO save(InternacaoDTO internacaoDTO) {
        eventoLeitoService.ocuparLeito(internacaoDTO);

        Internacao internacao = internacaoMapper.toEntity(internacaoDTO);
        internacao = internacaoRepository.save(internacao);
        InternacaoDTO result = internacaoMapper.toDto(internacao);
        internacaoSearchRepository.save(internacao);
        return result;
    }

    /**
     * Get all the internacaos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<InternacaoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Internacaos");
        return internacaoRepository.findAll(pageable)
            .map(internacaoMapper::toDto);
    }


    /**
     * Get one internacao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<InternacaoDTO> findOne(Long id) {
        log.debug("Request to get Internacao : {}", id);
        return internacaoRepository.findById(id)
            .map(internacaoMapper::toDto);
    }

    /**
     * Delete the internacao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Internacao : {}", id);
        internacaoRepository.deleteById(id);
        internacaoSearchRepository.deleteById(id);
    }

    /**
     * Search for the internacao corresponding to the query.
     *
     * @param query    the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<InternacaoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Internacaos for query {}", query);
        return internacaoSearchRepository.search(queryStringQuery(query), pageable)
            .map(internacaoMapper::toDto);
    }

}
