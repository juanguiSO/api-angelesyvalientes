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

    // Obtener todas las Valientes (activas e inactivas)
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

            // Actualizar los campos, incluyendo activo
            persona.setGenero(personaActualizada.getGenero());
            persona.setTipoIdentificacion(personaActualizada.getTipoIdentificacion());
            persona.setTxPrimerNombre(personaActualizada.getTxPrimerNombre());
            persona.setTxSegundoNombre(personaActualizada.getTxSegundoNombre());
            persona.setTxPrimerApellido(personaActualizada.getTxPrimerApellido());
            persona.setTxSegundoApellido(personaActualizada.getTxSegundoApellido());
            persona.setTxTelefono(personaActualizada.getTxTelefono());
            persona.setTxCorreo(personaActualizada.getTxCorreo());
            persona.setTxNumeroIdentificacion(personaActualizada.getTxNumeroIdentificacion());
            persona.setActivo(personaActualizada.isActivo()); // Actualizar el campo activo

            return valienteRepository.save(persona);
        } else {
            throw new RuntimeException("Valiente con ID " + id + " no encontrada.");
        }
    }

    // Eliminar una Valiente por su ID (eliminación lógica)
    public void deleteValiente(Long id) {
        Optional<Valiente> personaExistente = valienteRepository.findById(id);

        if (personaExistente.isPresent()) {
            Valiente persona = personaExistente.get();
            persona.setActivo(false); // Establecer activo en false
            valienteRepository.save(persona); // Guardar los cambios
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