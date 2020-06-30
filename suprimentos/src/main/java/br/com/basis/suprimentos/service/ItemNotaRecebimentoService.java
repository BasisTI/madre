package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.ItemNotaRecebimento;
import br.com.basis.suprimentos.repository.ItemNotaRecebimentoRepository;
import br.com.basis.suprimentos.repository.search.ItemNotaRecebimentoSearchRepository;
import br.com.basis.suprimentos.service.dto.ItemNotaRecebimentoDTO;
import br.com.basis.suprimentos.service.mapper.ItemNotaRecebimentoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class ItemNotaRecebimentoService {
    private final Logger log = LoggerFactory.getLogger(ItemNotaRecebimentoService.class);
    private final ItemNotaRecebimentoRepository itemNotaRecebimentoRepository;
    private final ItemNotaRecebimentoMapper itemNotaRecebimentoMapper;
    private final ItemNotaRecebimentoSearchRepository itemNotaRecebimentoSearchRepository;

    public ItemNotaRecebimentoDTO save(ItemNotaRecebimentoDTO itemNotaRecebimentoDTO) {
        log.debug("Request to save ItemNotaRecebimento : {}", itemNotaRecebimentoDTO);
        ItemNotaRecebimento itemNotaRecebimento = itemNotaRecebimentoMapper.toEntity(itemNotaRecebimentoDTO);
        itemNotaRecebimento = itemNotaRecebimentoRepository.save(itemNotaRecebimento);
        ItemNotaRecebimentoDTO result = itemNotaRecebimentoMapper.toDto(itemNotaRecebimento);
        itemNotaRecebimentoSearchRepository.save(itemNotaRecebimento);
        return result;
    }

    @Transactional(readOnly = true)
    public Page<ItemNotaRecebimentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ItemNotaRecebimentos");
        return itemNotaRecebimentoRepository.findAll(pageable)
            .map(itemNotaRecebimentoMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<ItemNotaRecebimentoDTO> findOne(Long id) {
        log.debug("Request to get ItemNotaRecebimento : {}", id);
        return itemNotaRecebimentoRepository.findById(id)
            .map(itemNotaRecebimentoMapper::toDto);
    }

    public void delete(Long id) {
        log.debug("Request to delete ItemNotaRecebimento : {}", id);
        itemNotaRecebimentoRepository.deleteById(id);
        itemNotaRecebimentoSearchRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<ItemNotaRecebimentoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ItemNotaRecebimentos for query {}", query);
        return itemNotaRecebimentoSearchRepository.search(queryStringQuery(query), pageable)
            .map(itemNotaRecebimentoMapper::toDto);
    }
}
