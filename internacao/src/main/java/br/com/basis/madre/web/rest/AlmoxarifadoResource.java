package br.com.basis.madre.web.rest;

import br.com.basis.madre.domain.Almoxarifado;
import br.com.basis.madre.repository.search.AlmoxarifadoSearchRepository;
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
@RequestMapping("/api/almoxarifados")
public class AlmoxarifadoResource {

    private Faker faker = new Faker(new Locale("pt-BR"));

    @Autowired
    private AlmoxarifadoSearchRepository almoxarifadoSearchRepository;

    @GetMapping()
    public Page<Almoxarifado> listar(@PageableDefault(size =10)Pageable pageable){
        Page<Almoxarifado> almoxarifadoPage = almoxarifadoSearchRepository.findAll(pageable);

        List<Almoxarifado> almoxarifado = almoxarifadoPage.getContent();

        Page<Almoxarifado> almoxarifadoModelPage = new PageImpl<>(almoxarifado, pageable,
            almoxarifadoPage.getTotalElements());

        return almoxarifadoModelPage;
    }

    @GetMapping("/fillData")
    public String fillDatabase() {
        for (int i = 0; i <= 50; i++) {
            Almoxarifado almoxarifado = new Almoxarifado(faker.name().name());
            almoxarifado.setId(faker.number().randomNumber());

            almoxarifadoSearchRepository.save(almoxarifado);
        }

        return "OK";

    }

}
