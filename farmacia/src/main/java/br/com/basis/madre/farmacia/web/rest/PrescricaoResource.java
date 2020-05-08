package br.com.basis.madre.farmacia.web.rest;

import br.com.basis.madre.farmacia.domain.Prescricao;
import br.com.basis.madre.farmacia.repository.search.PrescricaoSerchRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/medicamentos")
public class PrescricaoResource {

        @Autowired
        private PrescricaoSerchRepository prescricaoRepositorySearch;


        private Faker faker = new Faker(new Locale("pt-BR"));


    @GetMapping()
    public Page<Prescricao> listar(@PageableDefault(size = 10) Pageable pageable){
        Page<Prescricao> medicamentosPage = prescricaoRepositorySearch.findAll(pageable);

        List<Prescricao> medicamento = medicamentosPage.getContent();

        Page<Prescricao> medicamentoModelPage = new PageImpl<>(medicamento, pageable,
            medicamentosPage.getTotalElements());

        return medicamentoModelPage;
    }

        @GetMapping("/fillData")
        public String fillDatabase() {

            for (int i = 0; i <= 50; i++) {
                Prescricao prescricao = new Prescricao(faker.medical().medicineName(), faker.medical().hospitalName(), faker.medical().diseaseName(), faker.medical().hospitalName());
                prescricao.setId(faker.number().randomNumber());

                prescricaoRepositorySearch.save(prescricao);
            }

            return "OK";

        }

    }
