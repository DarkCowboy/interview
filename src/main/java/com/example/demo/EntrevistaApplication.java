package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.demo.persona.Persona;
import com.example.demo.persona.PersonaRepository;

@SpringBootApplication
public class EntrevistaApplication {

	public static void main(String[] args) {
		SpringApplication.run(EntrevistaApplication.class, args);
	}

	@Bean
	CommandLineRunner init(PersonaRepository personaRepository) {
		return (evt) -> {
			personaRepository.save(new Persona("John", "Doe"));
			personaRepository.save(new Persona("Jane", "Doe"));
		};
	}
}
