package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.Recebimento;
import br.com.basis.suprimentos.repository.ItemNotaRecebimentoRepository;
import br.com.basis.suprimentos.repository.RecebimentoRepository;
import br.com.basis.suprimentos.repository.search.RecebimentoSearchRepository;
import br.com.basis.suprimentos.service.dto.RecebimentoDTO;
import br.com.basis.suprimentos.service.mapper.RecebimentoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class RecebimentoService {
    private final RecebimentoRepository recebimentoRepository;
    private final ItemNotaRecebimentoRepository itemNotaRecebimentoRepository;
    private final RecebimentoMapper recebimentoMapper;
    private final RecebimentoSearchRepository recebimentoSearchRepository;

    @Transactional
    public RecebimentoDTO save(RecebimentoDTO recebimentoDTO) {
        log.debug("Request to save Recebimento : {}", recebimentoDTO);
        Recebimento recebimento = recebimentoMapper.toEntity(recebimentoDTO);
        final Recebimento recebimentoSalvo = recebimentoRepository.save(recebimento);
        recebimentoSearchRepository.save(recebimentoSalvo);

        recebimentoSalvo.getItensNotaRecebimentos().forEach(item -> {
            item.setRecebimento(recebimentoSalvo);
            itemNotaRecebimentoRepository.save(item);
        });
        recebimentoSalvo.setItensNotaRecebimentos(null);
        RecebimentoDTO result = recebimentoMapper.toDto(recebimentoSalvo);

        return result;
    }

    @Transactional(readOnly = true)
    public Page<RecebimentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Recebimentos");
        return recebimentoRepository.findAll(pageable)
            .map(recebimentoMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<RecebimentoDTO> findOne(Long id) {
        log.debug("Request to get Recebimento : {}", id);
        return recebimentoRepository.findById(id)
            .map(recebimentoMapper::toDto);
    }

    public void delete(Long id) {
        log.debug("Request to delete Recebimento : {}", id);
        recebimentoRepository.deleteById(id);
        recebimentoSearchRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<RecebimentoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Recebimentos for query {}", query);
        return recebimentoSearchRepository.search(queryStringQuery(query), pageable)
            .map(recebimentoMapper::toDto);
    }
}
