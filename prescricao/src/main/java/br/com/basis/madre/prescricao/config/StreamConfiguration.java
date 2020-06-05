package br.com.basis.madre.prescricao.config;

import java.util.function.Supplier;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.basis.madre.prescricao.domain.evento.EventoPrescricaoMedicamento;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;

@Configuration
public class StreamConfiguration {

	@Bean
	public EmitterProcessor<EventoPrescricaoMedicamento> prescricaoMedicamentoEmitterProcessor() {
		return EmitterProcessor.create();
	}

	@Bean
	public Supplier<Flux<EventoPrescricaoMedicamento>> prescricaoMedicamentoSupplier() {
		return this::prescricaoMedicamentoEmitterProcessor;
	}

}
