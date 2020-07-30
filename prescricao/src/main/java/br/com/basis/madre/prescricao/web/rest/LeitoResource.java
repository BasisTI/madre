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

import br.com.basis.madre.prescricao.domain.Leito;
import br.com.basis.madre.prescricao.repository.search.LeitoSearchRepository;

@RestController
@RequestMapping("/api/leitos")
public class LeitoResource {

	@Autowired
	private LeitoSearchRepository leitoSearchRepository;

	private Faker faker = new Faker(new Locale("pt-BR"));

	@GetMapping("/getAll")
	public Page<Leito> listar(@PageableDefault(size = 10) Pageable pageable) {

		Page<Leito> medicamentosPage = leitoSearchRepository.findAll(pageable);

		List<Leito> leito = medicamentosPage.getContent();

		Page<Leito> leitoModelPage = new PageImpl<>(leito, pageable, medicamentosPage.getTotalElements());

		return leitoModelPage;
	}

	@GetMapping("/create")
	public String fillDatabase() {
		for (int i = 0; i <= 20; i++) {
			Leito leito = new Leito(faker.medical().medicineName(), faker.number().randomDigit(),
					faker.number().randomDigit());
			leito.setId(faker.number().randomNumber());

			leitoSearchRepository.save(leito);
		}

		return "OK";

	}

}
