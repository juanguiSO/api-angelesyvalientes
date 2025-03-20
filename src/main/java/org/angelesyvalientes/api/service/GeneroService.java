package org.angelesyvalientes.api.service;

import org.angelesyvalientes.api.persistence.entity.Genero;
import org.angelesyvalientes.api.persistence.repository.GeneroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GeneroService {

    @Autowired
    GeneroRepository generoRepository;

    // Obtener un género por su ID
    public Optional<Genero> getGenero(Long id) {
        return generoRepository.findById(id);
    }

    // Obtener todos los géneros
    public List<Genero> getGeneros() {
        return generoRepository.findAll();
    }

    // Guardar un nuevo género
    public Genero saveGenero(Genero genero) {
        return generoRepository.save(genero);
    }

    // Actualizar un género existente
    public Genero updateGenero(Long id, Genero generoActualizado) {
        Optional<Genero> generoExistente = generoRepository.findById(id);

        if (generoExistente.isPresent()) {
            Genero genero = generoExistente.get();

            // Actualizar los campos
            genero.setTxGenero(generoActualizado.getTxGenero());

            return generoRepository.save(genero);
        } else {
            throw new RuntimeException("Género con ID " + id + " no encontrado.");
        }
    }

    // Eliminar un género por su ID
    public void deleteGenero(Long id) {
        Optional<Genero> generoExistente = generoRepository.findById(id);

        if (generoExistente.isPresent()) {
            generoRepository.deleteById(id); // Elimina el registro de la base de datos
        } else {
            throw new RuntimeException("Género con ID " + id + " no encontrado.");
        }
    }
}
