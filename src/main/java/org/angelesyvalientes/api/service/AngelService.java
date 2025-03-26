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

    public List<Angel> getAllAngeles() {
        return angelRepository.findAll();
    }

    public Optional<Angel> getAngelById(Long id) {
        return angelRepository.findById(id);
    }

    public Angel saveAngel(Angel angel) {
        return angelRepository.save(angel);
    }

    public void deleteAngel(Long id) {
        angelRepository.deleteById(id);
    }

    public Angel updateAngel(Long id, Angel angelDetails) {
        Optional<Angel> angelOptional = angelRepository.findById(id);

        if (angelOptional.isPresent()) {
            Angel angel = angelOptional.get();

            if (angelDetails.getDonacion() != null) {
                angel.setDonacion(angelDetails.getDonacion());
            }
            if (angelDetails.getProfesion() != null) {
                angel.setProfesion(angelDetails.getProfesion());
            }
            if (angelDetails.getUrlGaleria() != null) {
                angel.setUrlGaleria(angelDetails.getUrlGaleria());
            }
            if (angelDetails.getDescripcion() != null) {
                angel.setDescripcion(angelDetails.getDescripcion());
            }
            if (angelDetails.getRolAngel() != null) {
                angel.setRolAngel(angelDetails.getRolAngel());
            }

            return angelRepository.save(angel);
        } else {
            return null; // O lanza una excepción, según tu manejo de errores
        }
    }
}