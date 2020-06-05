package br.com.basis.madre.service;

import br.com.basis.madre.domain.Paciente;
import br.com.basis.madre.repository.search.PacienteSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class PacienteService {
    private final PacienteSearchRepository pacienteSearchRepository;

    @Transactional(readOnly = true)
    public Page<Paciente> obterTodosPacientes(Pageable pageable) {
        return pacienteSearchRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Paciente> obterPacientePorId(Long id) {
        return pacienteSearchRepository.findById(id);
    }
}
