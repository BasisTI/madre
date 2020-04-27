package br.com.basis.madre.prescricao.web.rest;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.basis.madre.prescricao.domain.Dieta;
import br.com.basis.madre.prescricao.domain.ItemDieta;
import br.com.basis.madre.prescricao.repository.DietaRepository;
import br.com.basis.madre.prescricao.repository.ItemDietaRepository;

@RestController
@RequestMapping("/api/prescricao-dieta")
public class DietaResource {
	
	@Autowired
	private DietaRepository dietaRepository;
	
	@Autowired
	private ItemDietaRepository itemDietaRepository;
	
	@GetMapping							
	public List<Dieta> listar(){
		return dietaRepository.findAll();
		
	}
	
	@PostMapping
	public ResponseEntity<Dieta> criar(@Valid @RequestBody Dieta dieta, HttpServletResponse response) {
		
		List<ItemDieta> itemDieta = dieta.getItemDieta();
		
		dieta.setItemDieta(null);
		
		Dieta dietaSalva = dietaRepository.save(dieta);
		List<ItemDieta> collect = itemDieta.stream().map(item -> {
			item.setDieta(dietaSalva);
			return item;
			}
		).collect(Collectors.toList());
		dietaSalva.setItemDieta(collect);
		itemDietaRepository.saveAll(collect);
		
		Dieta dietaFinal = dietaRepository.save(dietaSalva);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
				.buildAndExpand(dietaSalva.getId()).toUri();
		response.setHeader("Location", uri.toASCIIString());

		return ResponseEntity.created(uri).body(dietaFinal);
	}
	
	
	@GetMapping("/paciente/{id}")
	public ResponseEntity<List<Dieta>> buscarDietaPorIdDoPaciente(@PathVariable("id") Long id) {
		List<Dieta> dietas = dietaRepository.findByIdPaciente(id);
		
		return ResponseEntity.ok(dietas);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Dieta> buscarPorId(@PathVariable Long id) {
		Optional<Dieta> dieta = dietaRepository.findById(id);
		if (dieta == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(dieta.get());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Optional<Dieta>> atualizar(@PathVariable Long id,
			@RequestBody Dieta dieta){
		Optional<Dieta> dietaAtual = dietaRepository.findById(id);
		
		if(dietaAtual != null) {
			BeanUtils.copyProperties(dieta ,dietaAtual , "id" );
			 
			dietaAtual = dietaRepository.save(dietaAtual);
			return ResponseEntity.ok(dietaAtual);
		}
		
		return ResponseEntity.notFound().build();
	}


}
