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

import br.com.basis.madre.prescricao.domain.Medicamento;
import br.com.basis.madre.prescricao.repository.search.MedicamentoSearchRepository;

@RestController
@RequestMapping("/api/medicamentos")
public class MedicamentoResource {
	
	private Faker faker = new Faker(new Locale("pt-BR"));
	
	@Autowired
	private MedicamentoSearchRepository medicamentoReposirotySearch;
	
	@GetMapping()
	public Page<Medicamento> listar(@PageableDefault(size = 10)Pageable pageable){
		Page<Medicamento> medicamentosPage = medicamentoReposirotySearch.findAll(pageable);
		
		List<Medicamento> medicamento = medicamentosPage.getContent();
		
		Page<Medicamento> medicamentoModelPage = new PageImpl<>(medicamento, pageable,
				medicamentosPage.getTotalElements());
		
		return medicamentoModelPage;
	}

	@GetMapping("/fillData")
	 public String fillDatabase() {
	    	for (int i = 0; i <= 50; i++) {
				Medicamento medicamento = new Medicamento(faker.medical().hospitalName());
				medicamento.setId(faker.number().randomNumber());
				
				medicamentoReposirotySearch.save(medicamento);
			}
	    	
	    	return "OK";
	  
	    }
	
}
