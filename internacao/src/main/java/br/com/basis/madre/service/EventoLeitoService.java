package br.com.basis.madre.service;

import br.com.basis.madre.domain.EventoLeito;
import br.com.basis.madre.domain.enumeration.CodigoDoTipoEventoLeito;
import br.com.basis.madre.repository.EventoLeitoRepository;
import br.com.basis.madre.repository.search.EventoLeitoSearchRepository;
import br.com.basis.madre.service.dto.BloqueioDeLeitoDTO;
import br.com.basis.madre.service.dto.EventoLeitoDTO;
import br.com.basis.madre.service.dto.InternacaoDTO;
import br.com.basis.madre.service.dto.LiberacaoDeLeitoDTO;
import br.com.basis.madre.service.dto.ReservaDeLeitoDTO;
import br.com.basis.madre.service.dto.TipoDoEventoLeitoDTO;
import br.com.basis.madre.service.mapper.EventoLeitoMapper;
import br.com.basis.madre.service.mapper.TipoDoEventoLeitoMapper;
import br.com.basis.madre.service.projection.EventoCalendario;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.ZonedDateTime;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

@RequiredArgsConstructor
@Service
@Transactional
public class EventoLeitoService {

    private final Logger log = LoggerFactory.getLogger(EventoLeitoService.class);

    private final EventoLeitoRepository eventoLeitoRepository;

    private final EventoLeitoMapper eventoLeitoMapper;

    private final TipoDoEventoLeitoMapper tipoDoEventoLeitoMapper;

    private final EventoLeitoSearchRepository eventoLeitoSearchRepository;

    public ReservaDeLeitoDTO reservarLeito(ReservaDeLeitoDTO reservaDeLeitoDTO) {
        EventoLeitoDTO eventoLeitoDTO = eventoLeitoMapper.toDto(reservaDeLeitoDTO);
        eventoLeitoDTO.setTipoDoEventoId(CodigoDoTipoEventoLeito.RESERVA.getValor());

        EventoLeito eventoLeito = eventoLeitoRepository
            .save(eventoLeitoMapper.toEntity(eventoLeitoDTO));
        eventoLeitoSearchRepository.save(eventoLeito);
        reservaDeLeitoDTO.setId(eventoLeito.getId());

        return reservaDeLeitoDTO;
    }

    public BloqueioDeLeitoDTO bloquearLeito(BloqueioDeLeitoDTO bloqueioDeLeitoDTO) {
        EventoLeitoDTO eventoLeitoDTO = eventoLeitoMapper.toDto(bloqueioDeLeitoDTO);
        eventoLeitoDTO.setTipoDoEventoId(CodigoDoTipoEventoLeito.BLOQUEIO.getValor());

        EventoLeito eventoLeito = eventoLeitoRepository
            .save(eventoLeitoMapper.toEntity(eventoLeitoDTO));
        eventoLeitoSearchRepository.save(eventoLeito);
        bloqueioDeLeitoDTO.setId(eventoLeito.getId());

        return bloqueioDeLeitoDTO;
    }

    public boolean ocuparLeito(InternacaoDTO internacaoDTO) {
        EventoLeitoDTO eventoLeitoDTO = new EventoLeitoDTO();

        boolean verificaLeitoOcupado = eventoLeitoRepository.existsByLeitoId(internacaoDTO.getLeitoId());
        System.err.println("VALOR INICIAL: " + verificaLeitoOcupado);

        if (!verificaLeitoOcupado) {
            eventoLeitoDTO.setTipoDoEventoId(CodigoDoTipoEventoLeito.OCUPACAO.getValor());
            eventoLeitoDTO.setDataDoLancamento(ZonedDateTime.now());
            eventoLeitoDTO.setDataInicio(internacaoDTO.getDataDaInternacao());
            eventoLeitoDTO.setLeitoId(internacaoDTO.getLeitoId());
            eventoLeitoDTO.setJustificativa(internacaoDTO.getJustificativa());

            EventoLeito eventoLeito = eventoLeitoRepository
                .save(eventoLeitoMapper.toEntity(eventoLeitoDTO));
            eventoLeitoSearchRepository.save(eventoLeito);
        }

        return verificaLeitoOcupado;
    }

    public LiberacaoDeLeitoDTO liberarLeito(LiberacaoDeLeitoDTO liberacaoDeLeitoDTO) {
        EventoLeitoDTO eventoLeitoDTO = eventoLeitoMapper.toDto(liberacaoDeLeitoDTO);
        eventoLeitoDTO.setTipoDoEventoId(CodigoDoTipoEventoLeito.LIBERACAO.getValor());

        EventoLeito eventoLeito = eventoLeitoRepository
            .save(eventoLeitoMapper.toEntity(eventoLeitoDTO));
        eventoLeitoSearchRepository.save(eventoLeito);
        liberacaoDeLeitoDTO.setId(eventoLeito.getId());

        return liberacaoDeLeitoDTO;
    }

    public void desocuparLeito(Long leitoId) {
        TipoDoEventoLeitoDTO tipoDoEventoLeitoDTO = new TipoDoEventoLeitoDTO();
        tipoDoEventoLeitoDTO.setId(CodigoDoTipoEventoLeito.OCUPACAO.getValor());

        EventoLeito eventoLeito = eventoLeitoRepository
            .findOneByLeitoIdAndTipoDoEventoAndDataFimIsNull(leitoId,
                tipoDoEventoLeitoMapper.toEntity(tipoDoEventoLeitoDTO)
            ).orElseThrow(EntityNotFoundException::new);

        eventoLeito.setDataFim(ZonedDateTime.now());
        eventoLeitoRepository.save(eventoLeito);
        eventoLeitoSearchRepository.save(eventoLeito);
    }

    public Page<EventoCalendario> obterEventosCalendario(Pageable pageable) {
        return eventoLeitoRepository.findEventoCalendarioBy(pageable);
    }

    /**
     * Save a eventoLeito.
     *
     * @param eventoLeitoDTO the entity to save.
     * @return the persisted entity.
     */
    public EventoLeitoDTO save(EventoLeitoDTO eventoLeitoDTO) {
        log.debug("Request to save EventoLeito : {}", eventoLeitoDTO);
        EventoLeito eventoLeito = eventoLeitoMapper.toEntity(eventoLeitoDTO);
        eventoLeito = eventoLeitoRepository.save(eventoLeito);
        EventoLeitoDTO result = eventoLeitoMapper.toDto(eventoLeito);
        eventoLeitoSearchRepository.save(eventoLeito);
        return result;
    }

    /**
     * Get all the eventoLeitos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EventoLeitoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EventoLeitos");
        return eventoLeitoRepository.findAll(pageable)
            .map(eventoLeitoMapper::toDto);
    }


    /**
     * Get one eventoLeito by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EventoLeitoDTO> findOne(Long id) {
        log.debug("Request to get EventoLeito : {}", id);
        return eventoLeitoRepository.findById(id)
            .map(eventoLeitoMapper::toDto);
    }

    /**
     * Delete the eventoLeito by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EventoLeito : {}", id);
        eventoLeitoRepository.deleteById(id);
        eventoLeitoSearchRepository.deleteById(id);
    }

    /**
     * Search for the eventoLeito corresponding to the query.
     *
     * @param query    the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EventoLeitoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of EventoLeitos for query {}", query);
        return eventoLeitoSearchRepository.search(queryStringQuery(query), pageable)
            .map(eventoLeitoMapper::toDto);
    }
}
