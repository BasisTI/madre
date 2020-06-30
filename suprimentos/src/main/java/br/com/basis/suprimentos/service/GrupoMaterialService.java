package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.GrupoMaterial;
import br.com.basis.suprimentos.repository.GrupoMaterialRepository;
import br.com.basis.suprimentos.repository.search.GrupoMaterialSearchRepository;
import br.com.basis.suprimentos.service.dto.GrupoMaterialDTO;
import br.com.basis.suprimentos.service.mapper.GrupoMaterialMapper;
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
public class GrupoMaterialService {
    private final GrupoMaterialRepository grupoMaterialRepository;
    private final GrupoMaterialMapper grupoMaterialMapper;
    private final GrupoMaterialSearchRepository grupoMaterialSearchRepository;

    public GrupoMaterialDTO save(GrupoMaterialDTO grupoMaterialDTO) {
        log.debug("Request to save GrupoMaterial : {}", grupoMaterialDTO);
        GrupoMaterial grupoMaterial = grupoMaterialMapper.toEntity(grupoMaterialDTO);
        grupoMaterial = grupoMaterialRepository.save(grupoMaterial);
        GrupoMaterialDTO result = grupoMaterialMapper.toDto(grupoMaterial);
        grupoMaterialSearchRepository.save(grupoMaterial);
        return result;
    }

    @Transactional(readOnly = true)
    public Page<GrupoMaterialDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GrupoMaterials");
        return grupoMaterialRepository.findAll(pageable)
            .map(grupoMaterialMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<GrupoMaterialDTO> findOne(Long id) {
        log.debug("Request to get GrupoMaterial : {}", id);
        return grupoMaterialRepository.findById(id)
            .map(grupoMaterialMapper::toDto);
    }

    public void delete(Long id) {
        log.debug("Request to delete GrupoMaterial : {}", id);
        grupoMaterialRepository.deleteById(id);
        grupoMaterialSearchRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<GrupoMaterialDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of GrupoMaterials for query {}", query);
        return grupoMaterialSearchRepository.search(queryStringQuery(query), pageable)
            .map(grupoMaterialMapper::toDto);
    }
}
