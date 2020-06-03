package br.com.basis.madre.config;

import br.com.basis.madre.domain.evento.EventoPaciente;
import br.com.basis.madre.domain.evento.EventoTriagem;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
