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

import br.com.basis.madre.prescricao.domain.TipoAprazamento;
import br.com.basis.madre.prescricao.repository.TipoAprazamentoRepository;

@RestController
@RequestMapping("api/tipo-aprazamento")
public class TipoAprazamentoResource {
	
	@Autowired
	private TipoAprazamentoRepository tipoAprazamentoRepositoy;
	
	@GetMapping
	public List<TipoAprazamento> listar(){
		return tipoAprazamentoRepositoy.findAll();
	}
	
	@PostMapping
	public ResponseEntity<TipoAprazamento> criar(@Valid @RequestBody TipoAprazamento tipoAprazamento, HttpServletResponse response) {
		TipoAprazamento tipoAprazamentoSalvo = tipoAprazamentoRepositoy.save(tipoAprazamento);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
				.buildAndExpand(tipoAprazamento.getId()).toUri();
		response.setHeader("Location", uri.toASCIIString());

		return ResponseEntity.created(uri).body(tipoAprazamentoSalvo);
	}
	
}
