package com.diss.react4j.bootstrap;

import com.diss.react4j.model.Event;
import com.diss.react4j.model.Group;
import com.diss.react4j.model.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collections;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class Initializer implements CommandLineRunner {

    private final GroupRepository groupRepository;

    @Override
    public void run(String... args) throws Exception {
        Stream.of("Dallas JUG", "Denver JUG", "London JUG", "New York JUG", "Paris JUG")
                .forEach(name ->
                        groupRepository.save(new Group(name))
                );

        Group jug = groupRepository.findByName("Dallas JUG");

        Event ev = Event.builder()
                .title("Micro Frontends for Java Devs")
                .description("Lorem ipsum dolor sit amet")
                .date(Instant.parse("2024-07-01T00:00:00Z"))
                .build();

        jug.setEvents(Collections.singleton(ev));

        groupRepository.save(jug);

        groupRepository.findAll().forEach(System.out::println);
    }
}
