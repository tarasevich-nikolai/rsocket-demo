package com.tarasevich.rsocket.server.controller;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.tarasevich.rsocket.model.GetPersonsChannel;
import com.tarasevich.rsocket.model.GetPersonsStream;
import com.tarasevich.rsocket.model.Person;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Nikolai Tarasevich
 * @since 24.10.2020
 */
@Log4j2
@Controller
public class RSocketServerController {

    private static List<String> NAMES = List.of("Ivan", "Evgeniy", "Nikolai", "Mihail", "Roman", "Anna", "Svetlana", "Anastasia", "Darya");

    @MessageMapping("person")
    public Mono<Person> getPerson(String id) {
        return Mono.just(
            Person.builder()
                .id(id)
                .name(getRandomName())
                .build()
        );
    }

    @MessageMapping("persons-stream")
    public Flux<Person> getPersonsStream(GetPersonsStream getPersonsStream) {
        return Flux.fromIterable(getPersonsStream.getIds())
            .map(id -> Person.builder()
                .id(id)
                .name(getRandomName())
                .build());
    }

    @MessageMapping("persons-channel")
    public Flux<Person> getPersonsChannel(Flux<GetPersonsChannel> getPersonsChannelFlux) {
        return getPersonsChannelFlux
            .doOnNext(request -> log.info("Received channel request {}.", request))
            .map(req -> Person.builder()
                .id(req.getId())
                .name(getRandomName())
                .build()
            );
    }

    private String getRandomName() {
        return NAMES.get(ThreadLocalRandom.current().nextInt(NAMES.size()) % NAMES.size());
    }

}
