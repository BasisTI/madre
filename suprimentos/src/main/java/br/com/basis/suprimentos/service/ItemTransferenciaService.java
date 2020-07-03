package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.ItemTransferencia;
import br.com.basis.suprimentos.repository.ItemTransferenciaRepository;
import br.com.basis.suprimentos.repository.search.ItemTransferenciaSearchRepository;
import br.com.basis.suprimentos.service.dto.ItemTransferenciaDTO;
import br.com.basis.suprimentos.service.mapper.ItemTransferenciaMapper;
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
public class ItemTransferenciaService {
    private final ItemTransferenciaRepository itemTransferenciaRepository;
    private final ItemTransferenciaMapper itemTransferenciaMapper;
    private final ItemTransferenciaSearchRepository itemTransferenciaSearchRepository;

    public ItemTransferenciaDTO save(ItemTransferenciaDTO itemTransferenciaDTO) {
        log.debug("Request to save ItemTransferencia : {}", itemTransferenciaDTO);
        ItemTransferencia itemTransferencia = itemTransferenciaMapper.toEntity(itemTransferenciaDTO);
        itemTransferencia = itemTransferenciaRepository.save(itemTransferencia);
        ItemTransferenciaDTO result = itemTransferenciaMapper.toDto(itemTransferencia);
        itemTransferenciaSearchRepository.save(itemTransferencia);
        return result;
    }

    @Transactional(readOnly = true)
    public Page<ItemTransferenciaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ItemTransferencias");
        return itemTransferenciaRepository.findAll(pageable)
            .map(itemTransferenciaMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<ItemTransferenciaDTO> findOne(Long id) {
        log.debug("Request to get ItemTransferencia : {}", id);
        return itemTransferenciaRepository.findById(id)
            .map(itemTransferenciaMapper::toDto);
    }

    public void delete(Long id) {
        log.debug("Request to delete ItemTransferencia : {}", id);
        itemTransferenciaRepository.deleteById(id);
        itemTransferenciaSearchRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<ItemTransferenciaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ItemTransferencias for query {}", query);
        return itemTransferenciaSearchRepository.search(queryStringQuery(query), pageable)
            .map(itemTransferenciaMapper::toDto);
    }
}
