package org.angelesyvalientes.api.service;

import org.angelesyvalientes.api.persistence.entity.Educacion;
import org.angelesyvalientes.api.persistence.repository.EducacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EducacionService {

    private final EducacionRepository educacionRepository;

    @Autowired
    public EducacionService(EducacionRepository educacionRepository) {
        this.educacionRepository = educacionRepository;
    }

    // Obtener una Educacion por su ID
    public Optional<Educacion> getEducacion(Long id) {
        return educacionRepository.findById(id);
    }

    // Obtener todas las Educaciones
    public List<Educacion> getAllEducaciones() {
        return educacionRepository.findAll();
    }

    // Guardar una nueva Educacion
    public Educacion createEducacion(Educacion educacion) {
        return educacionRepository.save(educacion);
    }

    // Actualizar una Educacion existente
    public Educacion updateEducacion(Long id, Educacion educacionActualizada) {
        Optional<Educacion> educacionExistente = educacionRepository.findById(id);

        if (educacionExistente.isPresent()) {
            Educacion educacion = educacionExistente.get();

            // Actualizar los campos
            educacion.setPersona(educacionActualizada.getPersona());
            educacion.setInstitucion(educacionActualizada.getInstitucion());
            educacion.setNivel(educacionActualizada.getNivel());

            return educacionRepository.save(educacion);
        } else {
            throw new RuntimeException("Educacion con ID " + id + " no encontrada.");
        }
    }

    // Eliminar una Educacion por su ID
    public void deleteEducacion(Long id) {
        Optional<Educacion> educacionExistente = educacionRepository.findById(id);

        if (educacionExistente.isPresent()) {
            educacionRepository.deleteById(id);
        } else {
            throw new RuntimeException("Educacion con ID " + id + " no encontrada.");
        }
    }
}