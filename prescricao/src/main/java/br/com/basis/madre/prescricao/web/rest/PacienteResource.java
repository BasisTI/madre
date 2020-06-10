package br.com.basis.madre.prescricao.web.rest;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.basis.madre.prescricao.domain.Paciente;
import br.com.basis.madre.prescricao.repository.search.PacienteRepositorySearch;
import br.com.basis.madre.prescricao.service.PacienteService;
import io.github.jhipster.web.util.PaginationUtil;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteResource {

    private final Logger log = LoggerFactory.getLogger(PacienteResource.class);

    @Autowired
    private PacienteRepositorySearch pacienteRepositorySearch;

    @Autowired
    private PacienteService pacienteService;

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPorId(@PathVariable Long id) {
        Optional<Paciente> paciente = pacienteRepositorySearch.findById(id);
        if (paciente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(paciente.get());
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> obterTodosPacientes(Pageable pageable,
            @RequestParam(name = "nome", required = false) String query) {
        log.debug("Request REST para obter uma página de pacientes.");
        Page<Paciente> page = pacienteService.obterTodosPacientes(pageable, query);
        HttpHeaders headers = PaginationUtil
                .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
