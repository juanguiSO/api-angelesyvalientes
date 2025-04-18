package org.angelesyvalientes.api.service;

import org.angelesyvalientes.api.persistence.entity.ClasificacionValiente;
import org.angelesyvalientes.api.persistence.repository.ClasificacionValienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ClasificacionValienteService {

    private final ClasificacionValienteRepository clasificacionValienteRepository;

    @Autowired
    public ClasificacionValienteService(ClasificacionValienteRepository clasificacionValienteRepository) {
        this.clasificacionValienteRepository = clasificacionValienteRepository;
    }

    public List<ClasificacionValiente> obtenerTodasClasificaciones() {
        return clasificacionValienteRepository.findAll();
    }

    public Optional<ClasificacionValiente> obtenerClasificacionPorId(Long id) {
        return clasificacionValienteRepository.findById(id);
    }

    public ClasificacionValiente guardarClasificacion(ClasificacionValiente clasificacionValiente) {
        return clasificacionValienteRepository.save(clasificacionValiente);
    }

    public Optional<ClasificacionValiente> actualizarClasificacion(Long id, ClasificacionValiente clasificacionValiente) {
        return clasificacionValienteRepository.findById(id)
                .map(existingClasificacion -> {
                    existingClasificacion.setDescripcion(clasificacionValiente.getDescripcion());
                    return clasificacionValienteRepository.save(existingClasificacion);
                });
    }

    public boolean eliminarClasificacion(Long id) {
        if (clasificacionValienteRepository.existsById(id)) {
            clasificacionValienteRepository.deleteById(id);
            return true;
        }
        return false;
    }
}