package br.com.basis.madre.farmacia.service;

import br.com.basis.madre.farmacia.domain.DispensacaoMedicamentos;
import br.com.basis.madre.farmacia.domain.Medicamento;
import br.com.basis.madre.farmacia.domain.Prescricao;
import br.com.basis.madre.farmacia.repository.DispensacaoMedicamentosRepository;
import br.com.basis.madre.farmacia.repository.search.DispensacaoMedicamentosSearchRepository;
import br.com.basis.madre.farmacia.repository.search.PrescricaoSearchRepository;
import br.com.basis.madre.farmacia.service.dto.DispensacaoDTO;
import br.com.basis.madre.farmacia.service.dto.DispensacaoMedicamentosDTO;
import br.com.basis.madre.farmacia.service.dto.MedicamentoDTO;
import br.com.basis.madre.farmacia.service.mapper.DispensacaoMedicamentosMapper;
import br.com.basis.madre.farmacia.service.mapper.MedicamentoMapper;
import br.com.basis.madre.farmacia.web.rest.PrescricaoResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link DispensacaoMedicamentos}.
 */
@Service
@Transactional
public class DispensacaoMedicamentosService {

    private final Logger log = LoggerFactory.getLogger(DispensacaoMedicamentosService.class);

    private final DispensacaoMedicamentosRepository dispensacaoMedicamentosRepository;

    private final DispensacaoMedicamentosMapper dispensacaoMedicamentosMapper;

    private final DispensacaoMedicamentosSearchRepository dispensacaoMedicamentosSearchRepository;
    private final MedicamentoService medicamentoService;


    private final DispensacaoService dispensacaoService;
    private final PrescricaoResource prescricaoResource;
    private final MedicamentoMapper medicamentoMapper;
    private final PrescricaoSearchRepository prescricaoSearchRepository;

    public DispensacaoMedicamentosService(DispensacaoMedicamentosRepository dispensacaoMedicamentosRepository, DispensacaoMedicamentosMapper dispensacaoMedicamentosMapper, DispensacaoMedicamentosSearchRepository dispensacaoMedicamentosSearchRepository, MedicamentoService medicamento, MedicamentoService medicamentoService, DispensacaoService dispensacaoService, PrescricaoResource prescricaoResource, MedicamentoMapper medicamentoMapper, PrescricaoSearchRepository prescricaoSearchRepository) {
        this.dispensacaoMedicamentosRepository = dispensacaoMedicamentosRepository;
        this.dispensacaoMedicamentosMapper = dispensacaoMedicamentosMapper;
        this.dispensacaoMedicamentosSearchRepository = dispensacaoMedicamentosSearchRepository;
        this.medicamentoService = medicamentoService;
        this.dispensacaoService = dispensacaoService;
        this.prescricaoResource = prescricaoResource;
        this.medicamentoMapper = medicamentoMapper;
        this.prescricaoSearchRepository = prescricaoSearchRepository;
    }

    /**
     * Save a dispensacaoMedicamentos.
     *
     * @param dispensacaoMedicamentosDTO the entity to save.
     * @return the persisted entity.
     */
    public DispensacaoMedicamentosDTO save(DispensacaoMedicamentosDTO dispensacaoMedicamentosDTO) {
       Boolean codicao=  this.validador(dispensacaoMedicamentosDTO);

        if(codicao) {
            log.debug("Request to save DispensacaoMedicamentos : {}", dispensacaoMedicamentosDTO);
            DispensacaoMedicamentos dispensacaoMedicamentos = dispensacaoMedicamentosMapper.toEntity(dispensacaoMedicamentosDTO);
            dispensacaoMedicamentos = dispensacaoMedicamentosRepository.save(dispensacaoMedicamentos);
            DispensacaoMedicamentosDTO result = dispensacaoMedicamentosMapper.toDto(dispensacaoMedicamentos);
            dispensacaoMedicamentosSearchRepository.save(dispensacaoMedicamentos);


            Long idDispensacao = dispensacaoMedicamentosDTO.getDispensacaoId();
            Optional<DispensacaoDTO> dispensacaoDTO = dispensacaoService.findOne(idDispensacao);
            Long  idPrescricao = dispensacaoDTO.get().getIdPrescricao();
            Optional<Prescricao> prescricao = prescricaoResource.getPorId(idPrescricao);
            Long idMedicamento = dispensacaoMedicamentosDTO.getMedicamentosId();
            Optional<MedicamentoDTO>  medicamento = medicamentoService.findOne(idMedicamento);
            List<Medicamento> medicamentosDispensados = prescricao.get().getMedicamentosDispensados();

            Prescricao prescricao1 = new Prescricao();
            prescricao1.setId(prescricao.get().getId());
            prescricao1.setIdDispensacao(prescricao.get().getIdDispensacao());
            prescricao1.setMedicamentos(prescricao.get().getMedicamentos());
            prescricao1.setNome(prescricao.get().getNome());
            prescricao1.setIdItemPrescricaoMedicamento(prescricao.get().getIdItemPrescricaoMedicamento());


            MedicamentoDTO medicamento1 = new MedicamentoDTO();
            medicamento1.setId(medicamento.get().getId());
            medicamento1.setApresentacaoId(medicamento.get().getApresentacaoId());
            medicamento1.setDescricao(medicamento.get().getDescricao());
            medicamento1.setConcentracao(medicamento.get().getConcentracao());
            medicamento1.setNome(medicamento.get().getNome());
            medicamento1.setCodigo(medicamento.get().getCodigo());
            medicamento1.setTipoMedicamentoId(medicamento.get().getTipoMedicamentoId());
            medicamento1.setUnidadeId(medicamento.get().getUnidadeId());
            medicamento1.setAtivo(medicamento.get().isAtivo());

            Medicamento medicamento2 = medicamentoMapper.toEntity(medicamento1);


            if (medicamentosDispensados == null) {
                medicamentosDispensados  = new ArrayList<>();
                medicamentosDispensados.add(medicamento2);


            } else if (medicamentosDispensados.size() == 0) {
                medicamentosDispensados.add(medicamento2);
            } else {
                medicamentosDispensados.add(medicamento2);


            }
            prescricao1.setMedicamentosDispensados(medicamentosDispensados);
            prescricaoSearchRepository.save(prescricao1);

            prescricaoResource.putPrescricao(prescricao1);

            return result;
        }
        return null;
    }

    /**
     * Get all the dispensacaoMedicamentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DispensacaoMedicamentosDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DispensacaoMedicamentos");
        return dispensacaoMedicamentosRepository.findAll(pageable)
            .map(dispensacaoMedicamentosMapper::toDto);
    }


    /**
     * Get one dispensacaoMedicamentos by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DispensacaoMedicamentosDTO> findOne(Long id) {
        log.debug("Request to get DispensacaoMedicamentos : {}", id);
        return dispensacaoMedicamentosRepository.findById(id)
            .map(dispensacaoMedicamentosMapper::toDto);
    }

    /**
     * Delete the dispensacaoMedicamentos by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DispensacaoMedicamentos : {}", id);
        dispensacaoMedicamentosRepository.deleteById(id);
        dispensacaoMedicamentosSearchRepository.deleteById(id);
        dispensacaoMedicamentosRepository.deleteAll();
        dispensacaoMedicamentosSearchRepository.deleteAll();
    }

    /**
     * Search for the dispensacaoMedicamentos corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DispensacaoMedicamentosDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DispensacaoMedicamentos for query {}", query);
        return dispensacaoMedicamentosSearchRepository.search(queryStringQuery(query), pageable)
            .map(dispensacaoMedicamentosMapper::toDto);
    }


    private Boolean validador(DispensacaoMedicamentosDTO dispensacaoMedicamentosDTO){
        Long idDispensacao = dispensacaoMedicamentosDTO.getDispensacaoId();
        Optional<DispensacaoDTO> dispensacaoDTO = dispensacaoService.findOne(idDispensacao);
        Long  idPrescricao = dispensacaoDTO.get().getIdPrescricao();
        Long idMedicamento = dispensacaoMedicamentosDTO.getMedicamentosId();

        MedicamentoDTO  medicamento = medicamentoService.findOne(idMedicamento).orElseThrow(EntityNotFoundException::new);

        Optional<Prescricao> prescricao = prescricaoResource.getPorId(idPrescricao);
        List<Medicamento> medicamentosDispensados = prescricao.get().getMedicamentosDispensados();

        Boolean condicao = null;
     if (medicamentosDispensados == null) {
         condicao = true;

        } else {
            for (int i = 0; i < medicamentosDispensados.size(); i++) {
                MedicamentoDTO medicamento1 = medicamentoMapper.toDto(medicamentosDispensados.get(i));


                if (medicamento1.equals(medicamento)) {
                    condicao = false;

                    break;
                } else {
                    condicao = true;
                }
            }
        }

     return condicao;
    }
}
