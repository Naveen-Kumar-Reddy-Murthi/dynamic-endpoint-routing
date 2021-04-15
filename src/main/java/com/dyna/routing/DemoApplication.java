package com.dyna.routing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    @Autowired
    private URIMappingRepository uriMappingRepository;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        URIMapping uriMapping = URIMapping.builder()
								.controller("GreetingsController")
                                .method("greeting")
								.requestMapping("/greeting")
								.active(true)
								.build();
        uriMappingRepository.save(uriMapping);
    }
}
