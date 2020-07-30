package br.com.basis.madre.web.rest;

import br.com.basis.madre.domain.Paciente;
import br.com.basis.madre.service.PacienteService;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PacienteResource {
    private final PacienteService pacienteService;

    @GetMapping("/pacientes")
    public ResponseEntity<List<Paciente>> obterTodosPacientes(Pageable pageable, @RequestParam(name = "query", required = false) String query) {
        log.debug("Request REST para obter uma página de pacientes.");
        Page<Paciente> page = pacienteService.obterTodosPacientes(pageable, query);
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/pacientes/{id}")
    public ResponseEntity<Paciente> obterPacientePorId(@PathVariable(name = "id") Long id) {
        Optional<Paciente> paciente = pacienteService.obterPacientePorId(id);
        return ResponseUtil.wrapOrNotFound(paciente);
    }

    @GetMapping("/pacientes/prontuario/{prontuario}")
    public ResponseEntity<Paciente> obterPacientePorProntuario(@PathVariable(name = "prontuario") Long prontuario) {
        Optional<Paciente> paciente = pacienteService.obterPacientePorProntuario(prontuario);
        return ResponseUtil.wrapOrNotFound(paciente);
    }
}
