package br.com.basis.madre.prescricao.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.basis.madre.prescricao.domain.Medicamento;

@FeignClient("FARMACIA")

@Service
public interface MedicamentoService {

	@GetMapping("/api/medicamentos/{id}")
	Medicamento findById(@PathVariable("id") Long id);

}
