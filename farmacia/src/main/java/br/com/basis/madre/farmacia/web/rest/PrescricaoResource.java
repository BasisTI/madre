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
<<<<<<< HEAD
@RequestMapping("/api")
=======
@RequestMapping("/api/prescricoes")
>>>>>>> 772a2340ae33d6bbc7617cfe434a5f4a182d69c6
public class PrescricaoResource {

        @Autowired
        private PrescricaoSerchRepository prescricaoRepositorySearch;


        private Faker faker = new Faker(new Locale("pt-BR"));

    @GetMapping("/prescricao")
    public List<Prescricao> listar(Pageable pageable){
        Page<Prescricao> prescricaoPage = prescricaoRepositorySearch.findAll(pageable);

        List<Prescricao> prescricao = prescricaoPage.getContent();

        Page<Prescricao> prescricaoModelPage = new PageImpl<>(prescricao, pageable,
            prescricaoPage.getTotalElements());

        return prescricaoModelPage.getContent();
    }
    @GetMapping("/prescricao")
    public List<Prescricao> listarFarmacia(Pageable pageable){
        Page<Prescricao> prescricaoPage = prescricaoRepositorySearch.findAll(pageable);

        List<Prescricao> prescricao = prescricaoPage.getContent();

        Page<Prescricao> prescricaoModelPage = new PageImpl<>(prescricao, pageable,
            prescricaoPage.getTotalElements());

        return prescricaoModelPage.getContent();
    }


        @GetMapping("/fillData")
        public String fillDatabase() {

            for (int i = 0; i <= 50; i++) {
                Prescricao prescricao = new Prescricao(faker.medical().medicineName(), 02/02/2020,
                    02/02/2020, faker.medical().hospitalName());
                prescricao.setId(faker.number().randomNumber());

                prescricaoRepositorySearch.save(prescricao);
            }

            return "OK";

        }

    }
