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

import br.com.basis.madre.prescricao.domain.ItemDieta;
import br.com.basis.madre.prescricao.repository.ItemDietaRepository;

@RestController
@RequestMapping("/api/item-dieta")
public class ItemDietaResource {
	
	@Autowired
	private ItemDietaRepository itemDietaRepository;
	
	@GetMapping
	public List<ItemDieta> listar(){
		return itemDietaRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<ItemDieta> criar(@Valid @RequestBody ItemDieta itemDieta, HttpServletResponse response){
		ItemDieta itemDietaSalva = itemDietaRepository.save(itemDieta);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
				.buildAndExpand(itemDietaSalva.getId()).toUri();
		response.setHeader("Location", uri.toASCIIString());
		
		return ResponseEntity.created(uri).body(itemDietaSalva);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ItemDieta> buscarPorId(@PathVariable Long id) {
		Optional<ItemDieta> itemDieta = itemDietaRepository.findById(id);
		if (!itemDieta.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(itemDieta.get());
	}

}
