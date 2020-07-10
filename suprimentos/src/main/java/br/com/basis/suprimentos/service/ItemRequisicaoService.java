package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.ItemRequisicao;
import br.com.basis.suprimentos.repository.ItemRequisicaoRepository;
import br.com.basis.suprimentos.repository.search.ItemRequisicaoSearchRepository;
import br.com.basis.suprimentos.service.dto.ItemRequisicaoDTO;
import br.com.basis.suprimentos.service.mapper.ItemRequisicaoMapper;
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
public class ItemRequisicaoService {
    private final ItemRequisicaoRepository itemRequisicaoRepository;
    private final ItemRequisicaoMapper itemRequisicaoMapper;
    private final ItemRequisicaoSearchRepository itemRequisicaoSearchRepository;

    public ItemRequisicaoDTO save(ItemRequisicaoDTO itemRequisicaoDTO) {
        log.debug("Request to save ItemRequisicao : {}", itemRequisicaoDTO);
        ItemRequisicao itemRequisicao = itemRequisicaoMapper.toEntity(itemRequisicaoDTO);
        itemRequisicao = itemRequisicaoRepository.save(itemRequisicao);
        ItemRequisicaoDTO result = itemRequisicaoMapper.toDto(itemRequisicao);
        itemRequisicaoSearchRepository.save(itemRequisicao);
        return result;
    }

    @Transactional(readOnly = true)
    public Page<ItemRequisicaoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ItemRequisicaos");
        return itemRequisicaoRepository.findAll(pageable)
            .map(itemRequisicaoMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<ItemRequisicaoDTO> findOne(Long id) {
        log.debug("Request to get ItemRequisicao : {}", id);
        return itemRequisicaoRepository.findById(id)
            .map(itemRequisicaoMapper::toDto);
    }

    public void delete(Long id) {
        log.debug("Request to delete ItemRequisicao : {}", id);
        itemRequisicaoRepository.deleteById(id);
        itemRequisicaoSearchRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<ItemRequisicaoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ItemRequisicaos for query {}", query);
        return itemRequisicaoSearchRepository.search(queryStringQuery(query), pageable)
            .map(itemRequisicaoMapper::toDto);
    }
}
