package br.com.basis.madre.service;

import br.com.basis.madre.domain.Paciente;
import br.com.basis.madre.domain.SolicitacaoDeInternacao;
import br.com.basis.madre.repository.SolicitacaoDeInternacaoRepository;
import br.com.basis.madre.repository.search.SolicitacaoDeInternacaoSearchRepository;
import br.com.basis.madre.service.dto.SolicitacaoDeInternacaoDTO;
import br.com.basis.madre.service.mapper.SolicitacaoDeInternacaoMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

@RequiredArgsConstructor
@Service
@Transactional
public class SolicitacaoDeInternacaoService {

    private final Logger log = LoggerFactory.getLogger(SolicitacaoDeInternacaoService.class);

    private final SolicitacaoDeInternacaoRepository solicitacaoDeInternacaoRepository;

    private final SolicitacaoDeInternacaoMapper solicitacaoDeInternacaoMapper;

    private final SolicitacaoDeInternacaoSearchRepository solicitacaoDeInternacaoSearchRepository;

    private final PacienteService pacienteService;

    /**
     * Save a solicitacaoDeInternacao.
     *
     * @param solicitacaoDeInternacaoDTO the entity to save.
     * @return the persisted entity.
     */
    public SolicitacaoDeInternacaoDTO save(SolicitacaoDeInternacaoDTO solicitacaoDeInternacaoDTO) {
        log.debug("Request to save SolicitacaoDeInternacao : {}", solicitacaoDeInternacaoDTO);
        SolicitacaoDeInternacao solicitacaoDeInternacao = solicitacaoDeInternacaoMapper
            .toEntity(solicitacaoDeInternacaoDTO);
        solicitacaoDeInternacao = solicitacaoDeInternacaoRepository.save(solicitacaoDeInternacao);
        SolicitacaoDeInternacaoDTO result = solicitacaoDeInternacaoMapper
            .toDto(solicitacaoDeInternacao);
        solicitacaoDeInternacaoSearchRepository.save(solicitacaoDeInternacao);
        return result;
    }

    /**
     * Get all the solicitacaoDeInternacaos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SolicitacaoDeInternacaoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SolicitacaoDeInternacaos");
        return solicitacaoDeInternacaoRepository.findAll(pageable)
            .map(solicitacao -> {
                SolicitacaoDeInternacaoDTO dto = solicitacaoDeInternacaoMapper.toDto(solicitacao);
                Paciente paciente = pacienteService.obterPacientePorId(dto.getPacienteId()).orElseThrow(EntityNotFoundException::new);
                dto.setNomeDoPaciente(paciente.getNome());
                return dto;
            });
    }


    /**
     * Get one solicitacaoDeInternacao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SolicitacaoDeInternacaoDTO> findOne(Long id) {
        log.debug("Request to get SolicitacaoDeInternacao : {}", id);
        return solicitacaoDeInternacaoRepository.findById(id)
            .map(solicitacaoDeInternacaoMapper::toDto);
    }

    /**
     * Delete the solicitacaoDeInternacao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SolicitacaoDeInternacao : {}", id);
        solicitacaoDeInternacaoRepository.deleteById(id);
        solicitacaoDeInternacaoSearchRepository.deleteById(id);
    }

    /**
     * Search for the solicitacaoDeInternacao corresponding to the query.
     *
     * @param query    the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SolicitacaoDeInternacaoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SolicitacaoDeInternacaos for query {}", query);
        return solicitacaoDeInternacaoSearchRepository.search(queryStringQuery(query), pageable)
            .map(solicitacaoDeInternacaoMapper::toDto);
    }

}
