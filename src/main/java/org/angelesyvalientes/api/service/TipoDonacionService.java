package org.angelesyvalientes.api.service;

import org.angelesyvalientes.api.persistence.entity.TipoDonacion;
import org.angelesyvalientes.api.persistence.repository.TipoDonacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoDonacionService {

    private final TipoDonacionRepository tipoDonacionRepository;

    @Autowired
    public TipoDonacionService(TipoDonacionRepository tipoDonacionRepository) {
        this.tipoDonacionRepository = tipoDonacionRepository;
    }

    // Obtener un TipoDonacion por su ID
    public Optional<TipoDonacion> getTipoDonacion(Long id) {
        return tipoDonacionRepository.findById(id);
    }

    // Obtener todos los TipoDonaciones
    public List<TipoDonacion> getAllTipoDonaciones() {
        return tipoDonacionRepository.findAll();
    }

    // Guardar un nuevo TipoDonacion
    public TipoDonacion createTipoDonacion(TipoDonacion tipoDonacion) {
        return tipoDonacionRepository.save(tipoDonacion);
    }

    // Actualizar un TipoDonacion existente
    public TipoDonacion updateTipoDonacion(Long id, TipoDonacion tipoDonacionActualizado) {
        Optional<TipoDonacion> tipoDonacionExistente = tipoDonacionRepository.findById(id);

        if (tipoDonacionExistente.isPresent()) {
            TipoDonacion tipoDonacion = tipoDonacionExistente.get();

            // Actualizar los campos
            tipoDonacion.setTipoDonacion(tipoDonacionActualizado.getTipoDonacion());

            return tipoDonacionRepository.save(tipoDonacion);
        } else {
            throw new RuntimeException("TipoDonacion con ID " + id + " no encontrado.");
        }
    }

    // Eliminar un TipoDonacion por su ID
    public void deleteTipoDonacion(Long id) {
        Optional<TipoDonacion> tipoDonacionExistente = tipoDonacionRepository.findById(id);

        if (tipoDonacionExistente.isPresent()) {
            tipoDonacionRepository.deleteById(id);
        } else {
            throw new RuntimeException("TipoDonacion con ID " + id + " no encontrado.");
        }
    }
}
