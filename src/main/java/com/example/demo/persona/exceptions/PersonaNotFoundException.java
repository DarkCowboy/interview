package com.example.demo.persona.exceptions;

public class PersonaNotFoundException extends RuntimeException {
	private Long personaId;

	public PersonaNotFoundException(Long personId) {
		super();
		this.personaId = personId;
	}
	
	public Long getPersonaId() {
		return this.personaId;
	}
}
