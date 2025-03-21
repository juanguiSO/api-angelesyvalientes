package org.angelesyvalientes.api.service;

import org.angelesyvalientes.api.persistence.entity.Genero;
import org.angelesyvalientes.api.persistence.entity.TipoDonacion;
import org.angelesyvalientes.api.persistence.repository.TipoDonacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class TipoDonacionService {
    @Autowired
    TipoDonacionRepository tipoDonacionRepository;

    public List<TipoDonacion> getTipoDonaciones() {
        return tipoDonacionRepository.findAll();
    }

    public Optional<TipoDonacion> getTipoDonacion(Long id) {
        return tipoDonacionRepository.findById(id);
    }

    public TipoDonacion saveTipoDonacion(TipoDonacion tipoDonacion) {
        return tipoDonacionRepository.save(tipoDonacion);
    }

    public void deleteTipoDonacion(Long id) {
        tipoDonacionRepository.deleteById(id);
        Optional<TipoDonacion> TipoDonacionExistente = tipoDonacionRepository.findById(id);

        if (TipoDonacionExistente.isPresent()) {
            tipoDonacionRepository.deleteById(id); // Elimina el registro de la base de datos
        } else {
            throw new RuntimeException("Género con ID " + id + " no encontrado.");
        }




    }
}
