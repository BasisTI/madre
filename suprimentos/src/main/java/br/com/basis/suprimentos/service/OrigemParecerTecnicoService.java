package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.OrigemParecerTecnico;
import br.com.basis.suprimentos.repository.OrigemParecerTecnicoRepository;
import br.com.basis.suprimentos.repository.search.OrigemParecerTecnicoSearchRepository;
import br.com.basis.suprimentos.service.dto.OrigemParecerTecnicoDTO;
import br.com.basis.suprimentos.service.mapper.OrigemParecerTecnicoMapper;
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
public class OrigemParecerTecnicoService {
    private final OrigemParecerTecnicoRepository origemParecerTecnicoRepository;
    private final OrigemParecerTecnicoMapper origemParecerTecnicoMapper;
    private final OrigemParecerTecnicoSearchRepository origemParecerTecnicoSearchRepository;

    public OrigemParecerTecnicoDTO save(OrigemParecerTecnicoDTO origemParecerTecnicoDTO) {
        log.debug("Request to save OrigemParecerTecnico : {}", origemParecerTecnicoDTO);
        OrigemParecerTecnico origemParecerTecnico = origemParecerTecnicoMapper.toEntity(origemParecerTecnicoDTO);
        origemParecerTecnico = origemParecerTecnicoRepository.save(origemParecerTecnico);
        OrigemParecerTecnicoDTO result = origemParecerTecnicoMapper.toDto(origemParecerTecnico);
        origemParecerTecnicoSearchRepository.save(origemParecerTecnico);
        return result;
    }

    @Transactional(readOnly = true)
    public Page<OrigemParecerTecnicoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OrigemParecerTecnicos");
        return origemParecerTecnicoRepository.findAll(pageable)
            .map(origemParecerTecnicoMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<OrigemParecerTecnicoDTO> findOne(Long id) {
        log.debug("Request to get OrigemParecerTecnico : {}", id);
        return origemParecerTecnicoRepository.findById(id)
            .map(origemParecerTecnicoMapper::toDto);
    }

    public void delete(Long id) {
        log.debug("Request to delete OrigemParecerTecnico : {}", id);
        origemParecerTecnicoRepository.deleteById(id);
        origemParecerTecnicoSearchRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<OrigemParecerTecnicoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of OrigemParecerTecnicos for query {}", query);
        return origemParecerTecnicoSearchRepository.search(queryStringQuery(query), pageable)
            .map(origemParecerTecnicoMapper::toDto);
    }
}
