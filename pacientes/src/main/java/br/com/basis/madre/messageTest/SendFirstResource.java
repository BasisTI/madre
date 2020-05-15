package br.com.basis.madre.messageTest;


import org.springframework.http.HttpStatus;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.EmitterProcessor;
import java.util.Random;

@RestController
public class SendFirstResource {
    private static final String[] chaves = new String[]{"1", "2", "3", "4"};
    private static final Random RANDOM = new Random(System.currentTimeMillis());
    private final EmitterProcessor<Message<String>> firstEmitterProcessor;
    public SendFirstResource(EmitterProcessor<Message<String>> firstEmitterProcessor)
    {
        this.firstEmitterProcessor = firstEmitterProcessor;
    }
    @PostMapping(path = "/", consumes = "*/*")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void handleRequest(@RequestBody String body) {
        String chave = chaves[RANDOM.nextInt(chaves.length)];
        Message<String> message = MessageBuilder
            .withPayload(String.format("%s [%s]", body, chave))
            .setHeader("partitionKey", chave)
            .build();
        firstEmitterProcessor.onNext(message);
    }
}
