package br.com.basis.suprimentos.service;

import br.com.basis.suprimentos.domain.Almoxarifado;
import br.com.basis.suprimentos.repository.AlmoxarifadoRepository;
import br.com.basis.suprimentos.repository.search.AlmoxarifadoSearchRepository;
import br.com.basis.suprimentos.service.dto.AlmoxarifadoDTO;
import br.com.basis.suprimentos.service.mapper.AlmoxarifadoMapper;
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
public class AlmoxarifadoService {

    private final AlmoxarifadoRepository almoxarifadoRepository;

    private final AlmoxarifadoMapper almoxarifadoMapper;

    private final AlmoxarifadoSearchRepository almoxarifadoSearchRepository;

    public AlmoxarifadoDTO save(AlmoxarifadoDTO almoxarifadoDTO) {
        log.debug("Request to save Almoxarifado : {}", almoxarifadoDTO);
        Almoxarifado almoxarifado = almoxarifadoMapper.toEntity(almoxarifadoDTO);
        almoxarifado = almoxarifadoRepository.save(almoxarifado);
        AlmoxarifadoDTO result = almoxarifadoMapper.toDto(almoxarifado);
        almoxarifadoSearchRepository.save(almoxarifado);
        return result;
    }

    @Transactional(readOnly = true)
    public Page<AlmoxarifadoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Almoxarifados");
        return almoxarifadoRepository.findAll(pageable)
            .map(almoxarifadoMapper::toDto);
    }


    @Transactional(readOnly = true)
    public Optional<AlmoxarifadoDTO> findOne(Long id) {
        log.debug("Request to get Almoxarifado : {}", id);
        return almoxarifadoRepository.findById(id)
            .map(almoxarifadoMapper::toDto);
    }

    public void delete(Long id) {
        log.debug("Request to delete Almoxarifado : {}", id);
        almoxarifadoRepository.deleteById(id);
        almoxarifadoSearchRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<AlmoxarifadoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Almoxarifados for query {}", query);
        return almoxarifadoSearchRepository.search(queryStringQuery(query), pageable)
            .map(almoxarifadoMapper::toDto);
    }
}
