package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.service.CaracteristicaArmazenamentoService;
import br.com.basis.suprimentos.service.dto.CaracteristicaArmazenamentoDTO;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CaracteristicaArmazenamentoResource {

    private static final String ENTITY_NAME = "madresuprimentosCaracteristicaArmazenamento";
    private final CaracteristicaArmazenamentoService caracteristicaArmazenamentoService;
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @PostMapping("/caracteristicas-armazenamento")
    public ResponseEntity<CaracteristicaArmazenamentoDTO> createCaracteristicaArmazenamento(@Valid @RequestBody CaracteristicaArmazenamentoDTO caracteristicaArmazenamentoDTO) throws URISyntaxException {
        log.debug("REST request to save CaracteristicaArmazenamento : {}", caracteristicaArmazenamentoDTO);
        if (caracteristicaArmazenamentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new caracteristicaArmazenamento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CaracteristicaArmazenamentoDTO result = caracteristicaArmazenamentoService.save(caracteristicaArmazenamentoDTO);
        return ResponseEntity.created(new URI("/api/caracteristicas-armazenamento/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/caracteristicas-armazenamento")
    public ResponseEntity<CaracteristicaArmazenamentoDTO> updateCaracteristicaArmazenamento(@Valid @RequestBody CaracteristicaArmazenamentoDTO caracteristicaArmazenamentoDTO) throws URISyntaxException {
        log.debug("REST request to update CaracteristicaArmazenamento : {}", caracteristicaArmazenamentoDTO);
        if (caracteristicaArmazenamentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CaracteristicaArmazenamentoDTO result = caracteristicaArmazenamentoService.save(caracteristicaArmazenamentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, caracteristicaArmazenamentoDTO.getId().toString()))
            .body(result);
    }

    @GetMapping("/caracteristicas-armazenamento")
    public ResponseEntity<List<CaracteristicaArmazenamentoDTO>> getAllCaracteristicaArmazenamentos(Pageable pageable) {
        log.debug("REST request to get a page of CaracteristicaArmazenamentos");
        Page<CaracteristicaArmazenamentoDTO> page = caracteristicaArmazenamentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/caracteristicas-armazenamento/{id}")
    public ResponseEntity<CaracteristicaArmazenamentoDTO> getCaracteristicaArmazenamento(@PathVariable Long id) {
        log.debug("REST request to get CaracteristicaArmazenamento : {}", id);
        Optional<CaracteristicaArmazenamentoDTO> caracteristicaArmazenamentoDTO = caracteristicaArmazenamentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(caracteristicaArmazenamentoDTO);
    }

    @DeleteMapping("/caracteristicas-armazenamento/{id}")
    public ResponseEntity<Void> deleteCaracteristicaArmazenamento(@PathVariable Long id) {
        log.debug("REST request to delete CaracteristicaArmazenamento : {}", id);
        caracteristicaArmazenamentoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/_search/caracteristicas-armazenamento")
    public ResponseEntity<List<CaracteristicaArmazenamentoDTO>> searchCaracteristicaArmazenamentos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CaracteristicaArmazenamentos for query {}", query);
        Page<CaracteristicaArmazenamentoDTO> page = caracteristicaArmazenamentoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
