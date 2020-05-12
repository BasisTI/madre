package br.com.basis.madre.prescricao.web.rest;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.javafaker.Faker;

import br.com.basis.madre.prescricao.domain.ListaMedicamento;
import br.com.basis.madre.prescricao.domain.Medicamento;
import br.com.basis.madre.prescricao.repository.search.ListaMedicamentoSearchRepository;
import br.com.basis.madre.prescricao.repository.search.MedicamentoSearchRepository;

@RestController
@RequestMapping("/api/lista-medicamentos")
public class ListaMedicamentoResource {
	
	private Faker faker = new Faker(new Locale("pt-BR"));
	
	@Autowired
	private ListaMedicamentoSearchRepository listaMedicamentoSearchRepository;
	
	@GetMapping()
	public Page<ListaMedicamento> listar(Pageable pageable){
		Page<ListaMedicamento> listaMedicamentosPage = listaMedicamentoSearchRepository.findAll(pageable);
		
		List<ListaMedicamento> listaMedicamentos = listaMedicamentosPage.getContent();
		
		Page<ListaMedicamento> listaMedicamentoModelPage = new PageImpl<>(listaMedicamentos, pageable,
				listaMedicamentosPage.getTotalElements());
		
		return listaMedicamentoModelPage;
	}

	@GetMapping("/fillData")
	 public String fillDatabase() {
	    	for (int i = 0; i <= 5; i++) {
				ListaMedicamento listaMedicamento = new ListaMedicamento(faker.medical().symptoms());
				listaMedicamento.setId(faker.number().randomNumber());
				
				listaMedicamentoSearchRepository.save(listaMedicamento);
			}
	    	
	    	return "OK";
	  
	    }
	
}
