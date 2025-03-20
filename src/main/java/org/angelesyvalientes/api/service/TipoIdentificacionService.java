package org.angelesyvalientes.api.service;

import org.angelesyvalientes.api.persistence.entity.TipoIdentificacion;
import org.angelesyvalientes.api.persistence.repository.TipoIdentificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoIdentificacionService {

    @Autowired
    TipoIdentificacionRepository tipoIdentificacionRepository;

    // Obtener un TipoIdentificacion por su ID
    public Optional<TipoIdentificacion> getTipoIdentificacion(Long id) {
        return tipoIdentificacionRepository.findById(id);
    }

    // Obtener todos los Tipos de Identificación
    public List<TipoIdentificacion> getTiposIdentificacion() {
        return tipoIdentificacionRepository.findAll();
    }

    // Guardar un nuevo TipoIdentificacion
    public TipoIdentificacion saveTipoIdentificacion(TipoIdentificacion tipoIdentificacion) {
        return tipoIdentificacionRepository.save(tipoIdentificacion);
    }

    // Actualizar un TipoIdentificacion existente
    public TipoIdentificacion updateTipoIdentificacion(Long id, TipoIdentificacion tipoIdentificacionActualizado) {
        Optional<TipoIdentificacion> tipoExistente = tipoIdentificacionRepository.findById(id);

        if (tipoExistente.isPresent()) {
            TipoIdentificacion tipoIdentificacion = tipoExistente.get();

            // Actualizar los campos
            tipoIdentificacion.setTxTipoIdentificacion(tipoIdentificacionActualizado.getTxTipoIdentificacion());
            tipoIdentificacion.setBoEstado(tipoIdentificacionActualizado.getBoEstado());

            return tipoIdentificacionRepository.save(tipoIdentificacion);
        } else {
            throw new RuntimeException("TipoIdentificacion con ID " + id + " no encontrado.");
        }
    }

    // Eliminar un TipoIdentificacion por su ID
    public void deleteTipoIdentificacion(Long id) {
        Optional<TipoIdentificacion> tipoExistente = tipoIdentificacionRepository.findById(id);

        if (tipoExistente.isPresent()) {
            tipoIdentificacionRepository.deleteById(id); // Elimina el registro de la base de datos
        } else {
            throw new RuntimeException("TipoIdentificacion con ID " + id + " no encontrado.");
        }
    }
}
