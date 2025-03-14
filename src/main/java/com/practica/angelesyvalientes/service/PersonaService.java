package com.practica.angelesyvalientes.service;

import com.practica.angelesyvalientes.entity.Persona;
import com.practica.angelesyvalientes.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonaService {

    @Autowired
    PersonaRepository personaRepository;

    // Obtener una Persona por su ID
    public Optional<Persona> getPersona(Long id) {
        return personaRepository.findById(id);
    }

    // Obtener todas las Personas
    public List<Persona> getPersonas() {
        return personaRepository.findAll();
    }

    // Guardar una nueva Persona
    public Persona savePersona(Persona persona) {
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
            personaRepository.deleteById(id); // Elimina el registro de la base de datos
        } else {
            throw new RuntimeException("Persona con ID " + id + " no encontrada.");
        }
    }
}
