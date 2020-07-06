package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.TransferenciaAlmoxarifado;
import br.com.basis.suprimentos.domain.projection.TransferenciaAutomatica;
import br.com.basis.suprimentos.repository.TransferenciaAlmoxarifadoRepository;
import br.com.basis.suprimentos.repository.search.TransferenciaAlmoxarifadoSearchRepository;
import br.com.basis.suprimentos.service.dto.TransferenciaAlmoxarifadoDTO;
import br.com.basis.suprimentos.service.mapper.TransferenciaAlmoxarifadoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class TransferenciaAlmoxarifadoService {
    private final TransferenciaAlmoxarifadoRepository transferenciaAlmoxarifadoRepository;
    private final TransferenciaAlmoxarifadoMapper transferenciaAlmoxarifadoMapper;
    private final TransferenciaAlmoxarifadoSearchRepository transferenciaAlmoxarifadoSearchRepository;
    private final AuthenticationPrincipalService authenticationPrincipalService;

    public TransferenciaAlmoxarifadoDTO criarNovaTransferenciaAutomatica(TransferenciaAlmoxarifadoDTO transferenciaAlmoxarifadoDTO) {
        transferenciaAlmoxarifadoDTO.getInformacaoTransferencia().setAtiva(true);
        transferenciaAlmoxarifadoDTO.getInformacaoTransferencia().setEfetivada(false);
        transferenciaAlmoxarifadoDTO.setGeradoEm(ZonedDateTime.now(ZoneId.systemDefault()));
        transferenciaAlmoxarifadoDTO.setGeradoPor(authenticationPrincipalService.getLoginAtivo());
        return save(transferenciaAlmoxarifadoDTO);
    }

    public TransferenciaAlmoxarifadoDTO save(TransferenciaAlmoxarifadoDTO transferenciaAlmoxarifadoDTO) {
        log.debug("Request to save TransferenciaAlmoxarifado : {}", transferenciaAlmoxarifadoDTO);
        final TransferenciaAlmoxarifado transferenciaAlmoxarifado = transferenciaAlmoxarifadoMapper.toEntity(transferenciaAlmoxarifadoDTO);
        transferenciaAlmoxarifado.getItens().forEach(item -> {
            item.setTransferenciaAlmoxarifado(transferenciaAlmoxarifado);
        });
        final TransferenciaAlmoxarifado salvo = transferenciaAlmoxarifadoRepository.save(transferenciaAlmoxarifado);
        TransferenciaAlmoxarifadoDTO result = transferenciaAlmoxarifadoMapper.toDto(salvo);
        transferenciaAlmoxarifadoSearchRepository.save(transferenciaAlmoxarifado);
        return result;
    }

    @Transactional(readOnly = true)
    public Page<TransferenciaAutomatica> findAllTransferenciasAutomaticas(Pageable pageable) {
        log.debug("Request to get all TransferenciaAlmoxarifados");
        return transferenciaAlmoxarifadoRepository.findBy(TransferenciaAutomatica.class, pageable);
    }

    @Transactional(readOnly = true)
    public Optional<TransferenciaAlmoxarifadoDTO> findOne(Long id) {
        log.debug("Request to get TransferenciaAlmoxarifado : {}", id);
        return transferenciaAlmoxarifadoRepository.findById(id)
            .map(transferenciaAlmoxarifadoMapper::toDto);
    }

    public void delete(Long id) {
        log.debug("Request to delete TransferenciaAlmoxarifado : {}", id);
        transferenciaAlmoxarifadoRepository.deleteById(id);
        transferenciaAlmoxarifadoSearchRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<TransferenciaAlmoxarifadoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TransferenciaAlmoxarifados for query {}", query);
        return transferenciaAlmoxarifadoSearchRepository.search(queryStringQuery(query), pageable)
            .map(transferenciaAlmoxarifadoMapper::toDto);
    }
}
