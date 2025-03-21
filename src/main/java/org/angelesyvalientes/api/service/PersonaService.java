package org.angelesyvalientes.api.service;

import org.angelesyvalientes.api.persistence.entity.Persona;
import org.angelesyvalientes.api.persistence.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonaService {

    private final PersonaRepository personaRepository;

    @Autowired
    public PersonaService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    // Obtener una Persona por su ID
    public Optional<Persona> getPersona(Long id) {
        return personaRepository.findById(id);
    }

    // Obtener todas las Personas
    public List<Persona> getAllPersonas() {
        return personaRepository.findAll();
    }

    // Guardar una nueva Persona
    public Persona createPersona(Persona persona) {
        return personaRepository.save(persona);
    }

    // Actualizar una Persona existente
    public Persona updatePersona(Long id, Persona personaActualizada) {
        Optional<Persona> personaExistente = personaRepository.findById(id);

        if (personaExistente.isPresent()) {
            Persona persona = personaExistente.get();

            // Actualizar los campos
            persona.setGenero(personaActualizada.getGenero());
            persona.setTipoIdentificacion(personaActualizada.getTipoIdentificacion());
            persona.setTxPrimerNombre(personaActualizada.getTxPrimerNombre());
            persona.setTxSegundoNombre(personaActualizada.getTxSegundoNombre());
            persona.setTxPrimerApellido(personaActualizada.getTxPrimerApellido());
            persona.setTxSegundoApellido(personaActualizada.getTxSegundoApellido());
            persona.setTxTelefono(personaActualizada.getTxTelefono());
            persona.setTxCorreo(personaActualizada.getTxCorreo());
            persona.setTxNumeroIdentificacion(personaActualizada.getTxNumeroIdentificacion());

            return personaRepository.save(persona);
        } else {
            throw new RuntimeException("Persona con ID " + id + " no encontrada.");
        }
    }

    // Eliminar una Persona por su ID
    public void deletePersona(Long id) {
        Optional<Persona> personaExistente = personaRepository.findById(id);

        if (personaExistente.isPresent()) {
            personaRepository.deleteById(id);
        } else {
            throw new RuntimeException("Persona con ID " + id + " no encontrada.");
        }
    }
}