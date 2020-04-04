package br.com.basis.madre.prescricao.web.rest;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.basis.madre.prescricao.domain.TipoUnidade;
import br.com.basis.madre.prescricao.repository.TipoUnidadeRepository;

@RestController
@RequestMapping("api/tipo-unidade")
public class TipoUnidadeResource {
	
	@Autowired
	private TipoUnidadeRepository tipoUnidadeRepository;
	
	@GetMapping
	public List<TipoUnidade> listar(){
		return tipoUnidadeRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<TipoUnidade> criar(@Valid @RequestBody TipoUnidade tipoUnidade, HttpServletResponse response){
		TipoUnidade tipoUnidadeSalva = tipoUnidadeRepository.save(tipoUnidade);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
				.buildAndExpand(tipoUnidadeSalva.getId()).toUri();
		response.setHeader("Location", uri.toASCIIString());
		
		return ResponseEntity.created(uri).body(tipoUnidadeSalva);
		
		
	}

}
