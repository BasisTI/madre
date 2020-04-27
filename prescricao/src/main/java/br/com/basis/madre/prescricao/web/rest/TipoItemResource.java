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

import br.com.basis.madre.prescricao.domain.TipoItem;
import br.com.basis.madre.prescricao.repository.TipoItemRepository;

@RestController
@RequestMapping("api/tipo-item")
public class TipoItemResource {
	
	@Autowired
	private TipoItemRepository tipoItemRepository ;
	
	@GetMapping
	public List<TipoItem> listar(){
		return tipoItemRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<TipoItem> criar(@Valid @RequestBody TipoItem tipoItem, HttpServletResponse response) {
		TipoItem tipoItemSalvo = tipoItemRepository.save(tipoItem);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
				.buildAndExpand(tipoItemSalvo.getId()).toUri();
		response.setHeader("Location", uri.toASCIIString());

		return ResponseEntity.created(uri).body(tipoItemSalvo);
	}

}
