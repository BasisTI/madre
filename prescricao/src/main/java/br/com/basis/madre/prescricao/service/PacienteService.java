package br.com.basis.madre.prescricao.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.basis.madre.prescricao.domain.Paciente;
import br.com.basis.madre.prescricao.repository.search.PacienteRepositorySearch;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class PacienteService {
    private final PacienteRepositorySearch pacienteSearchRepository;
    
    @Transactional(readOnly = true)
    public Page<Paciente> obterTodosPacientes(Pageable pageable) {
    	
        return pacienteSearchRepository.findAll(pageable);
    }
    
}
