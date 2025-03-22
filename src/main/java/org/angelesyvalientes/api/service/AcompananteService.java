package org.angelesyvalientes.api.service;

import org.angelesyvalientes.api.persistence.entity.Acompanante;
import org.angelesyvalientes.api.persistence.repository.AcompananteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AcompananteService {

    private final AcompananteRepository acompananteRepository;

    @Autowired
    public AcompananteService(AcompananteRepository acompananteRepository) {
        this.acompananteRepository = acompananteRepository;
    }

    // Obtener un Acompanante por su ID
    public Optional<Acompanante> getAcompanante(Long id) {
        return acompananteRepository.findById(id);
    }

    // Obtener todos los Acompanantes
    public List<Acompanante> getAllAcompanantes() {
        return acompananteRepository.findAll();
    }

    // Guardar un nuevo Acompanante
    public Acompanante createAcompanante(Acompanante acompanante) {
        return acompananteRepository.save(acompanante);
    }

    // Actualizar un Acompanante existente
    public Acompanante updateAcompanante(Long id, Acompanante acompananteActualizado) {
        Optional<Acompanante> acompananteExistente = acompananteRepository.findById(id);

        if (acompananteExistente.isPresent()) {
            Acompanante acompanante = acompananteExistente.get();

            // Actualizar los campos
            acompanante.setPersona(acompananteActualizado.getPersona());
            acompanante.setNombreAcompanante(acompananteActualizado.getNombreAcompanante());
            acompanante.setTelefonoAcompanante(acompananteActualizado.getTelefonoAcompanante());

            return acompananteRepository.save(acompanante);
        } else {
            throw new RuntimeException("Acompanante con ID " + id + " no encontrado.");
        }
    }

    // Eliminar un Acompanante por su ID
    public void deleteAcompanante(Long id) {
        Optional<Acompanante> acompananteExistente = acompananteRepository.findById(id);

        if (acompananteExistente.isPresent()) {
            acompananteRepository.deleteById(id);
        } else {
            throw new RuntimeException("Acompanante con ID " + id + " no encontrado.");
        }
    }
}