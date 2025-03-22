package org.angelesyvalientes.api.service;

import org.angelesyvalientes.api.persistence.entity.Angel;
import org.angelesyvalientes.api.persistence.repository.AngelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AngelService {

    private final AngelRepository angelRepository;

    @Autowired
    public AngelService(AngelRepository angelRepository) {
        this.angelRepository = angelRepository;
    }

    // Obtener un Angel por su ID
    public Optional<Angel> getAngel(Long id) {
        return angelRepository.findById(id);
    }

    // Obtener todos los Angeles
    public List<Angel> getAllAngeles() {
        return angelRepository.findAll();
    }

    // Guardar un nuevo Angel
    public Angel createAngel(Angel angel) {
        return angelRepository.save(angel);
    }

    // Actualizar un Angel existente
    public Angel updateAngel(Long id, Angel angelActualizado) {
        Optional<Angel> angelExistente = angelRepository.findById(id);

        if (angelExistente.isPresent()) {
            Angel angel = angelExistente.get();

            // Actualizar los campos
            angel.setPersona(angelActualizado.getPersona());
            angel.setDonacion(angelActualizado.getDonacion());
            angel.setRolAngel(angelActualizado.getRolAngel());
            angel.setDescripcion(angelActualizado.getDescripcion());

            return angelRepository.save(angel);
        } else {
            throw new RuntimeException("Angel con ID " + id + " no encontrado.");
        }
    }

    // Eliminar un Angel por su ID
    public void deleteAngel(Long id) {
        Optional<Angel> angelExistente = angelRepository.findById(id);

        if (angelExistente.isPresent()) {
            angelRepository.deleteById(id);
        } else {
            throw new RuntimeException("Angel con ID " + id + " no encontrado.");
        }
    }
}