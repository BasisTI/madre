package br.com.basis.madre.messageTest;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Configuration
public class FirstConfiguration {
    private static final Logger logger =
        LoggerFactory.getLogger(FirstConfiguration.class);
    @Bean
    public Consumer<Message<String>> listenFirst() {
        return mensagem -> logger.info(
            String.format("%s [%s]",
                mensagem.getPayload(),
                mensagem.getHeaders().get(KafkaHeaders.RECEIVED_PARTITION_ID)));
    }
    @Bean
    public EmitterProcessor<Message<String>> firstProcessor() {
        return EmitterProcessor.create();
    }
    @Bean
    public Supplier<Flux<Message<String>>> sendFirst() {
        return this::firstProcessor;
    }
}
