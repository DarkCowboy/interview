package com.example.demo.persona;

import java.net.URI;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.persona.exceptions.PersonaNotFoundException;
import com.example.demo.utils.Error;

@RestController
@RequestMapping("/personas")
public class PersonaController {

	private PersonaRepository personaRepository;

	@Autowired
	public PersonaController(PersonaRepository personaRepository) {
		this.personaRepository = personaRepository;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Persona show(@PathVariable Long id) {

		Persona persona = this.personaRepository.findOne(id);

		if (persona == null)
			throw new PersonaNotFoundException(id);

		return persona;
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public Collection<Persona> showAll() {
		return this.personaRepository.findAll();
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<Persona> add(@RequestBody Persona persona, UriComponentsBuilder ucb) {

		Persona newPersona = this.personaRepository.save(persona);
		HttpHeaders httpHeaders = new HttpHeaders();
		URI locationUri = ucb.path("/personas/").path(String.valueOf(newPersona.getId())).build().toUri();

		httpHeaders.setLocation(locationUri);

		ResponseEntity<Persona> responseEntity = new ResponseEntity<>(newPersona, httpHeaders, HttpStatus.CREATED);

		return responseEntity;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable Long id) {
		
		Persona persona = this.personaRepository.findOne(id);
		
		if (persona == null)
			throw new PersonaNotFoundException(id);
		
		this.personaRepository.delete(id);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public Persona update(@PathVariable Long id, @RequestBody Persona persona) {
		Persona personaToUpdate = this.personaRepository.findOne(id);
		
		if (personaToUpdate == null)
			throw new PersonaNotFoundException(id);
		
		personaToUpdate.setNombre(persona.getNombre());
		personaToUpdate.setApellido(persona.getApellido());

		return this.personaRepository.save(personaToUpdate);
	}

	@ExceptionHandler(PersonaNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Error personasNotFound(PersonaNotFoundException e) {
		long id = e.getPersonaId();
		Error error = new Error(4, "Persona con id " + id + " no fue encontrada");

		return error;
	}
}
