package br.com.basis.madre.prescricao.config;

import java.util.function.Supplier;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.basis.madre.prescricao.domain.evento.EventoPaciente;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;

@Configuration
public class StreamConfiguration {
	
	@Bean  
	public EmitterProcessor<EventoPaciente> pacienteEmitterProcessor() {
	return EmitterProcessor.create();
	}
	@Bean
	public Supplier<Flux<EventoPaciente>> pacienteSupplier() {
	return this::pacienteEmitterProcessor;
	}

}
