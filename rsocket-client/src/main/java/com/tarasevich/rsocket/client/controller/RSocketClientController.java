package com.tarasevich.rsocket.client.controller;

import java.time.Duration;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import com.tarasevich.rsocket.model.GetPerson;
import com.tarasevich.rsocket.model.GetPersonsChannel;
import com.tarasevich.rsocket.model.GetPersonsStream;
import com.tarasevich.rsocket.model.Person;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Nikolai Tarasevich
 * @since 24.10.2020
 */
@Log4j2
@RestController
@AllArgsConstructor
public class RSocketClientController {

    private final RSocketRequester rSocketRequester;

    @GetMapping("/person/{id}")
    public Mono<Person> getPerson(@PathVariable String id) {
        return rSocketRequester.route("person")
            .data(GetPerson.builder().id(id).build())
            .retrieveMono(Person.class)
            .doOnNext(person -> log.info("Received person {}.", person));
    }

    @GetMapping(value = "/persons-channel", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public Flux<Person> getPersonsChannel() {
        return rSocketRequester.route("persons-channel")
            .data(Flux.interval(Duration.ofSeconds(1)).map(sec -> GetPersonsChannel.builder().id(String.valueOf(sec)).build()), GetPersonsChannel.class)
            .retrieveFlux(Person.class)
            .doOnNext(person -> log.info("Received person {} from channel.", person));
    }

    @GetMapping(value = "/persons-stream/{count}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Person> getPersonsStream(@PathVariable Long count) {
        return rSocketRequester.route("persons-stream")
            .data(GetPersonsStream.builder().ids(LongStream.range(0, count).boxed().map(Object::toString).collect(Collectors.toList())).build())
            .retrieveFlux(Person.class)
            .doOnNext(person -> log.info("Received person {} from stream.", person));
    }

}
