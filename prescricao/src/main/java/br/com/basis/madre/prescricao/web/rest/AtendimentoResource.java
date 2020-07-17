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

import br.com.basis.madre.prescricao.domain.Atendimento;
import br.com.basis.madre.prescricao.repository.search.AtendimentoSearchRepository;

@RestController
@RequestMapping("/api/atendimentos")
public class AtendimentoResource {
	
	@Autowired
	private AtendimentoSearchRepository atendimentoSearchRepository;

	private Faker faker = new Faker(new Locale("pt-BR"));

	@GetMapping("/getAll")
	public Page<Atendimento> listar(@PageableDefault(size = 10) Pageable pageable) {

		Page<Atendimento> atedendimentosPage = atendimentoSearchRepository.findAll(pageable);

		List<Atendimento> atendimento = atedendimentosPage.getContent();

		Page<Atendimento> atendimentoModelPage = new PageImpl<>(atendimento, pageable, atedendimentosPage.getTotalElements());

		return atendimentoModelPage;
	}

	@GetMapping("/create")
	public String fillDatabase() {
		for (int i = 0; i <= 20; i++) {
			Atendimento atendimento = new Atendimento(faker.number().digits(6));
			atendimento.setId(faker.number().randomNumber());

			atendimentoSearchRepository.save(atendimento);
		}

		return "OK";

	}

}
