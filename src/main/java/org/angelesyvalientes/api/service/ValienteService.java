package org.angelesyvalientes.api.service;

import org.angelesyvalientes.api.persistence.entity.FichaPorValiente;
import org.angelesyvalientes.api.persistence.entity.Valiente;
import org.angelesyvalientes.api.persistence.repository.FichaPorValienteRepository;
import org.angelesyvalientes.api.persistence.repository.ValienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ValienteService {

    @Autowired
    ValienteRepository valienteRepository;

    @Autowired
    FichaPorValienteRepository fichaPorValienteRepository;

    // Obtener una Valiente por su ID
    public Optional<Valiente> getValiente(Long id) {
        return valienteRepository.findById(id);
    }

    // Obtener todas las Valientes
    public List<Valiente> getValientes() {
        return valienteRepository.findAll();
    }

    // Guardar una nueva Valiente
    public Valiente saveValiente(Valiente persona) {
        return valienteRepository.save(persona);
    }

    // Actualizar una Valiente existente
    public Valiente updateValiente(Long id, Valiente personaActualizada) {
        Optional<Valiente> personaExistente = valienteRepository.findById(id);

        if (personaExistente.isPresent()) {
            Valiente persona = personaExistente.get();

            // Actualizar los campos
            persona.setGenero(personaActualizada.getGenero());
            persona.setTipoIdentificacion(personaActualizada.getTipoIdentificacion());
            persona.setTxPrimerNombre(personaActualizada.getTxPrimerNombre());
            persona.setTxSegundoNombre(personaActualizada.getTxSegundoNombre());
            persona.setTxPrimerApellido(personaActualizada.getTxPrimerApellido());
            persona.setTxSegundoApellido(personaActualizada.getTxSegundoApellido());
            persona.setTxTelefono(personaActualizada.getTxTelefono());
            persona.setTxCorreo(personaActualizada.getTxCorreo());
            persona.setTxNumeroIdentificacion(personaActualizada.getTxNumeroIdentificacion());

            return valienteRepository.save(persona);
        } else {
            throw new RuntimeException("Valiente con ID " + id + " no encontrada.");
        }
    }

    // Eliminar una Valiente por su ID
    public void deleteValiente(Long id) {
        Optional<Valiente> personaExistente = valienteRepository.findById(id);

        if (personaExistente.isPresent()) {
            valienteRepository.deleteById(id); // Elimina el registro de la base de datos
        } else {
            throw new RuntimeException("Valiente con ID " + id + " no encontrada.");
        }
    }

    // Asignar un programa a un valiente
    public void asignarFicha(int id, int idFicha) {
        FichaPorValiente fichaPorValiente = new FichaPorValiente();
        fichaPorValiente.setIdValiente(id);
        fichaPorValiente.setIdFicha(idFicha);

        fichaPorValienteRepository.save(fichaPorValiente);
    }
}
