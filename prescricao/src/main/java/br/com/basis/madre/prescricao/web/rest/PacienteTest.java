package br.com.basis.madre.prescricao.web.rest;

import java.time.ZoneId;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.javafaker.Faker;

import br.com.basis.madre.prescricao.domain.Paciente;
import br.com.basis.madre.prescricao.repository.search.PacienteRepositorySearch;

public class PacienteTest {
	
	@Autowired
	private PacienteRepositorySearch pacienteRepositorySearch;
	
	
	private Faker faker = new Faker(new Locale("pt-BR"));


    public void fillDatabase() {
    	for (int i = 0; i < 200; i++) {
			Paciente paciente = new Paciente(faker.name().firstName() + " " + faker.name().lastName(), 
					faker.date().birthday(1, 90).toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), 
					faker.number().digits(6), faker.name().firstName() + " " + faker.name().lastName(), 
					faker.date().past(7, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
			
			pacienteRepositorySearch.save(paciente);
		}
  
    }
}

