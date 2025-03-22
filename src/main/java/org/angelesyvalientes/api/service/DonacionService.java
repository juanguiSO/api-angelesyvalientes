package org.angelesyvalientes.api.service;

import org.angelesyvalientes.api.persistence.entity.Donacion;
import org.angelesyvalientes.api.persistence.repository.DonacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DonacionService {

    private final DonacionRepository donacionRepository;

    @Autowired
    public DonacionService(DonacionRepository donacionRepository) {
        this.donacionRepository = donacionRepository;
    }

    // Obtener una Donacion por su ID
    public Optional<Donacion> getDonacion(Long id) {
        return donacionRepository.findById(id);
    }

    // Obtener todas las Donaciones
    public List<Donacion> getAllDonaciones() {
        return donacionRepository.findAll();
    }

    // Guardar una nueva Donacion
    public Donacion createDonacion(Donacion donacion) {
        return donacionRepository.save(donacion);
    }

    // Actualizar una Donacion existente
    public Donacion updateDonacion(Long id, Donacion donacionActualizada) {
        Optional<Donacion> donacionExistente = donacionRepository.findById(id);

        if (donacionExistente.isPresent()) {
            Donacion donacion = donacionExistente.get();

            // Actualizar los campos
            donacion.setTipoDonacion(donacionActualizada.getTipoDonacion());
            donacion.setFecha(donacionActualizada.getFecha());
            donacion.setObservacion(donacionActualizada.getObservacion());

            return donacionRepository.save(donacion);
        } else {
            throw new RuntimeException("Donacion con ID " + id + " no encontrada.");
        }
    }

    // Eliminar una Donacion por su ID
    public void deleteDonacion(Long id) {
        Optional<Donacion> donacionExistente = donacionRepository.findById(id);

        if (donacionExistente.isPresent()) {
            donacionRepository.deleteById(id);
        } else {
            throw new RuntimeException("Donacion con ID " + id + " no encontrada.");
        }
    }
}
