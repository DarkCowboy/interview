package com.example.demo.persona;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


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
		return this.personaRepository.findOne(id);
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public Collection<Persona> showAll() {
		return this.personaRepository.findAll();
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public Persona add(@RequestBody Persona persona) {
		return this.personaRepository.save(persona);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable Long id) {
		this.personaRepository.delete(id);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public Persona update(@PathVariable Long id, @RequestBody Persona persona) {
		Persona personaToUpdate = this.personaRepository.findOne(id);
		personaToUpdate.setNombre(persona.getNombre());
		personaToUpdate.setApellido(persona.getApellido());

		return this.personaRepository.save(personaToUpdate);
	}
}
