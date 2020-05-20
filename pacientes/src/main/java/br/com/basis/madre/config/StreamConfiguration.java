package br.com.basis.madre.config;

import br.com.basis.madre.domain.events.EventoPaciente;
import br.com.basis.madre.domain.events.EventoTriagem;
import br.com.basis.madre.service.PacienteService;
import br.com.basis.madre.service.dto.PacienteInclusaoDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

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
    @Bean
    public EmitterProcessor<EventoTriagem> triagemEmitterProcessor() {
        return EmitterProcessor.create();
    }
    @Bean
    public Supplier<Flux<EventoTriagem>> triagemSupplier() {
        return this::triagemEmitterProcessor;
    }
}
