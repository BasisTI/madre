package br.com.basis.madre.farmacia.service;

import br.com.basis.madre.farmacia.domain.DispensacaoMedicamentos;
import br.com.basis.madre.farmacia.domain.Estorno;
import br.com.basis.madre.farmacia.repository.EstornoRepository;
import br.com.basis.madre.farmacia.repository.search.EstornoSearchRepository;
import br.com.basis.madre.farmacia.service.dto.DispensacaoMedicamentosDTO;
import br.com.basis.madre.farmacia.service.dto.EstornoDTO;
import br.com.basis.madre.farmacia.service.mapper.EstornoMapper;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Estorno}.
 */
@Service
@Transactional
public class EstornoService {

    private final Logger log = LoggerFactory.getLogger(EstornoService.class);

    private final EstornoRepository estornoRepository;

    private final EstornoMapper estornoMapper;

    private final EstornoSearchRepository estornoSearchRepository;

    private final DispensacaoMedicamentosService dispensacaoMedicamentosService;

    private static final String ENTITY_NAME = "farmaciaDispensacaoMedicamentos";

    public EstornoService(EstornoRepository estornoRepository, EstornoMapper estornoMapper, EstornoSearchRepository estornoSearchRepository, DispensacaoMedicamentosService dispensacaoMedicamentosService) {
        this.estornoRepository = estornoRepository;
        this.estornoMapper = estornoMapper;
        this.estornoSearchRepository = estornoSearchRepository;
        this.dispensacaoMedicamentosService = dispensacaoMedicamentosService;
    }

    /**
     * Save a estorno.
     *
     * @param estornoDTO the entity to save.
     * @return the persisted entity.
     */
    public EstornoDTO save(EstornoDTO estornoDTO) {
        Boolean codicao =  this.validador(estornoDTO);

        if(codicao) {
            log.debug("Request to save Estorno : {}", estornoDTO);
            Estorno estorno = estornoMapper.toEntity(estornoDTO);
            estorno = estornoRepository.save(estorno);
            EstornoDTO result = estornoMapper.toDto(estorno);
            estornoSearchRepository.save(estorno);


            Long idDispemsacaoMedicamento = estornoDTO.getDispensacaoMedicamentosId();
            Optional<DispensacaoMedicamentosDTO>  dispensacaoMedicamentos = dispensacaoMedicamentosService.findOne(idDispemsacaoMedicamento);

            DispensacaoMedicamentosDTO dispensacaoMedicamentosDTO = new DispensacaoMedicamentosDTO();
            dispensacaoMedicamentosDTO.setId( estornoDTO.getDispensacaoMedicamentosId());
            dispensacaoMedicamentosDTO.setDispensacaoId(dispensacaoMedicamentos.get().getDispensacaoId());
            dispensacaoMedicamentosDTO.setDispensado(dispensacaoMedicamentos.get().isDispensado());
            dispensacaoMedicamentosDTO.setMedicamentosId(dispensacaoMedicamentos.get().getMedicamentosId());
            dispensacaoMedicamentosDTO.setEstornado(Boolean.TRUE);
            dispensacaoMedicamentosDTO.setDispensado(Boolean.FALSE);
//            log.debug("REST request to update DispensacaoMedicamentos : {}", dispensacaoMedicamentosDTO);
//            if (dispensacaoMedicamentosDTO.getId() == null) {
//                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
//            }
            dispensacaoMedicamentosService.save(dispensacaoMedicamentosDTO);

            return result;


        }
        return null;
    }

    /**
     * Get all the estornos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EstornoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Estornos");
        return estornoRepository.findAll(pageable)
            .map(estornoMapper::toDto);
    }


    /**
     * Get one estorno by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EstornoDTO> findOne(Long id) {
        log.debug("Request to get Estorno : {}", id);
        return estornoRepository.findById(id)
            .map(estornoMapper::toDto);
    }

    /**
     * Delete the estorno by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Estorno : {}", id);
        estornoRepository.deleteById(id);
        estornoSearchRepository.deleteById(id);
    }

    /**
     * Search for the estorno corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EstornoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Estornos for query {}", query);
        return estornoSearchRepository.search(queryStringQuery(query), pageable)
            .map(estornoMapper::toDto);
    }
    public Boolean validador(EstornoDTO estornoDTO) {

        Long idDispemsacaoMedicamento = estornoDTO.getDispensacaoMedicamentosId();
        Optional<DispensacaoMedicamentosDTO>  dispensacaoMedicamentos = dispensacaoMedicamentosService.findOne(idDispemsacaoMedicamento);

        if(dispensacaoMedicamentos.get().getEstornado()== null || dispensacaoMedicamentos.get().getEstornado() == false){
            return true;
        }else {
            return false;
        }



    }
}
