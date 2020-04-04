package br.com.basis.madre.prescricao.web.rest;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.basis.madre.prescricao.domain.Dieta;
import br.com.basis.madre.prescricao.repository.DietaRepository;

@RestController
@RequestMapping("/api/prescricao-dieta")
public class DietaResource {
	
	@Autowired
	private DietaRepository dietaRepository;
	
	@GetMapping
	public List<Dieta> listar(){
		return dietaRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Dieta> criar(@Valid @RequestBody Dieta dieta, HttpServletResponse response) {
		Dieta dietaSalva = dietaRepository.save(dieta);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
				.buildAndExpand(dietaSalva.getId()).toUri();
		response.setHeader("Location", uri.toASCIIString());

		return ResponseEntity.created(uri).body(dietaSalva);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Dieta> buscarPorId(@PathVariable Long id) {
		Optional<Dieta> dieta = dietaRepository.findById(id);
		if (!dieta.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(dieta.get());
	}

}
