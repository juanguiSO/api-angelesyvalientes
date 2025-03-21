package org.angelesyvalientes.api.service;

import org.angelesyvalientes.api.persistence.entity.Programa;
import org.angelesyvalientes.api.persistence.repository.ProgramaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProgramaService {

    @Autowired
    ProgramaRepository programaRepository;

    // Obtener un programa por su ID
    public Optional<Programa> getPrograma(Long id) {
        return programaRepository.findById(id);
    }

    // Obtener todos los programas
    public List<Programa> getProgramas() {
        return programaRepository.findAll();
    }

    // Guardar un nuevo programa
    public Programa savePrograma(Programa programa) {
        return programaRepository.save(programa);
    }

    // Actualizar un programa existente
    public Programa updatePrograma(Long id, Programa programaActualizado) {
        Optional<Programa> programaExistente = programaRepository.findById(id);

        if (programaExistente.isPresent()) {
            Programa programa = programaExistente.get();

            // Actualizar los campos
            programa.setNombre(programaActualizado.getNombre());

            return programaRepository.save(programa);
        } else {
            throw new RuntimeException("Género con ID " + id + " no encontrado.");
        }
    }

    // Eliminar un programa por su ID
    public void deletePrograma(Long id) {
        Optional<Programa> programaExistente = programaRepository.findById(id);

        if (programaExistente.isPresent()) {
            programaRepository.deleteById(id); // Elimina el registro de la base de datos
        } else {
            throw new RuntimeException("Programa con ID " + id + " no encontrado.");
        }
    }
}
