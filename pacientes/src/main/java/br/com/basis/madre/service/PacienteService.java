package br.com.basis.madre.service;

import br.com.basis.madre.domain.Paciente;
import br.com.basis.madre.repository.CartaoSUSRepository;
import br.com.basis.madre.repository.PacienteRepository;
import br.com.basis.madre.repository.search.PacienteSearchRepository;
import br.com.basis.madre.service.dto.FormularioCadastroDTO;
import br.com.basis.madre.service.dto.PacienteDTO;
import br.com.basis.madre.service.mapper.PacienteMapper;
import br.com.basis.madre.service.projection.PacienteResumo;

import java.util.Objects;
import java.util.Optional;

import org.apache.lucene.util.QueryBuilder;
import org.bouncycastle.math.raw.Nat;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.Strings;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilterBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Paciente}.
 */
@Service
@Transactional
public class PacienteService {

    private final Logger log = LoggerFactory.getLogger(PacienteService.class);

    private final PacienteRepository pacienteRepository;

    private final PacienteMapper pacienteMapper;

    private final PacienteSearchRepository pacienteSearchRepository;

    public PacienteService(PacienteRepository pacienteRepository, PacienteMapper pacienteMapper, PacienteSearchRepository pacienteSearchRepository) {
        this.pacienteRepository = pacienteRepository;
        this.pacienteMapper = pacienteMapper;
        this.pacienteSearchRepository = pacienteSearchRepository;
    }

    /**
     * TODO: Write documentation
     */
    public Page<PacienteResumo> findAllProjectedPacienteResumoBy(Pageable pageable) {
        return pacienteRepository.findAllProjectedPacienteResumoBy(pageable);
    }

    /*lista de pacientes com elasticsearch*/
    public Page<Paciente> findAllElasticPaciente(Pageable pageable) {
        return pacienteSearchRepository.findAll(pageable);

    }

    /**
     * Save a paciente.
     *
     * @param pacienteDTO the entity to save.
     * @return the persisted entity.
     */

//    public PacienteDTO save(FormularioCadastroDTO formularioCadastroDTO) {
//        log.debug("Request to save Paciente : {}", formularioCadastroDTO);
//        Paciente paciente = pacienteMapper.toEntity(formularioCadastroDTO);
//        paciente = pacienteRepository.save(paciente);
//        PacienteDTO result = pacienteMapper.toDto(paciente);
//        pacienteSearchRepository.save(paciente);
//        return result;
//    }

    public PacienteDTO save(PacienteDTO pacienteDTO) {
        log.debug("Request to save Paciente : {}", pacienteDTO);
        Paciente paciente = pacienteMapper.toEntity(pacienteDTO);
        paciente = pacienteRepository.save(paciente);
        PacienteDTO result = pacienteMapper.toDto(paciente);
        pacienteSearchRepository.save(paciente);
        return result;
    }

    /**
     * Get all the pacientes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PacienteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Pacientes");
        return pacienteRepository.findAll(pageable)
            .map(pacienteMapper::toDto);
    }

    /**
     * Get one paciente by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PacienteDTO> findOne(Long id) {
        log.debug("Request to get Paciente : {}", id);
        return pacienteRepository.findById(id)
            .map(pacienteMapper::toDto);
    }

    /**
     * Delete the paciente by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Paciente : {}", id);
        pacienteRepository.deleteById(id);
        pacienteSearchRepository.deleteById(id);
    }

    /**
     * Search for the paciente corresponding to the query.
     *
     * @param query    the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PacienteDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Pacientes for query {}", query);
        return pacienteSearchRepository.search(queryStringQuery(query), pageable)
            .map(pacienteMapper::toDto);
    }

    private ElasticsearchTemplate elasticsearchTemplate;
    public Page<Paciente> buscaPacientePorNome(String nome, Pageable pageable) {


        if (Strings.isNullOrEmpty(nome)) {
            return pacienteSearchRepository.search(new NativeSearchQueryBuilder()
                .withSourceFilter(new FetchSourceFilterBuilder().withIncludes("nome", "dataDeNascimento", "genitores", "cartaoSUS").build())
            .build());
        }

        return pacienteSearchRepository.search(
            new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.fuzzyQuery("nome", nome))
                .withSourceFilter(new FetchSourceFilterBuilder().withIncludes("nome", "dataDeNascimento", "genitores", "cartaoSUS").build())

                .build()
        );
    }

}
