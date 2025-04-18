package org.angelesyvalientes.api.service;

import jakarta.transaction.Transactional;
import org.angelesyvalientes.api.DetallesValienteDTO.DetallesValienteDTO;
import org.angelesyvalientes.api.persistence.entity.FichaPorValiente;
import org.angelesyvalientes.api.persistence.entity.Persona;
import org.angelesyvalientes.api.persistence.entity.Valiente;
import org.angelesyvalientes.api.persistence.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ValienteService {

    @Autowired
    ValienteRepository valienteRepository;
    @Autowired
    GrupoPoblacionalRepository grupoPoblacionalRepository;

    @Autowired
    ClasificacionValienteRepository clasificacionValienteRepository;

    @Autowired
    ViviendaRepository viviendaRepository;

    private static final Logger logger = LoggerFactory.getLogger(ValienteService.class);

    private final PersonaRepository personaRepository;

    @Autowired
    FichaPorValienteRepository fichaPorValienteRepository;

    public ValienteService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    // Obtener una Valiente por su ID
    public Optional<Valiente> getValiente(Long id) {
        return valienteRepository.findById(id);
    }

    // Obtener todas las Valientes (activas e inactivas)
    public List<Valiente> getValientes() {
        return valienteRepository.findAll();
    }


    // Actualizar un Valiente existente
    public Valiente actualizarValiente(Long id, Valiente valienteActualizado) {
        Optional<Valiente> valienteExistente = valienteRepository.findById(id);

        if (valienteExistente.isPresent()) {
            Valiente valiente = valienteExistente.get();

            // Actualizar los campos básicos de la persona utilizando los setters de la clase Persona
            valiente.setGenero(valienteActualizado.getGenero());
            valiente.setTipoIdentificacion(valienteActualizado.getTipoIdentificacion());
            valiente.setTxPrimerNombre(valienteActualizado.getTxPrimerNombre());
            valiente.setTxSegundoNombre(valienteActualizado.getTxSegundoNombre());
            valiente.setTxPrimerApellido(valienteActualizado.getTxPrimerApellido());
            valiente.setTxSegundoApellido(valienteActualizado.getTxSegundoApellido());
            valiente.setTxTelefono(valienteActualizado.getTxTelefono());
            valiente.setTxCorreo(valienteActualizado.getTxCorreo());
            valiente.setTxNumeroIdentificacion(valienteActualizado.getTxNumeroIdentificacion());
            valiente.setActivo(valienteActualizado.isActivo());

            // Actualizar los campos específicos del Valiente
            if (valienteActualizado.getFechaNacimiento() != null) {
                valiente.setFechaNacimiento(valienteActualizado.getFechaNacimiento());
            }
            valiente.setTallaCamisa(valienteActualizado.getTallaCamisa());
            valiente.setTallaPantalon(valienteActualizado.getTallaPantalon());
            valiente.setTallaCalzado(valienteActualizado.getTallaCalzado());
            valiente.setNombreResponsable(valienteActualizado.getNombreResponsable());
            valiente.setParentescoResponsable(valienteActualizado.getParentescoResponsable());
            valiente.setTelefonoResponsable(valienteActualizado.getTelefonoResponsable());
            valiente.setUrlGaleria(valienteActualizado.getUrlGaleria());
            valiente.setPoblacionConflictoArmado(valienteActualizado.isPoblacionConflictoArmado());
            valiente.setPoblacionMigrante(valienteActualizado.isPoblacionMigrante());
            valiente.setPoblacionJoven(valienteActualizado.isPoblacionJoven());
            valiente.setPoblacionMujer(valienteActualizado.isPoblacionMujer());
            valiente.setPoblacionLgtbiq(valienteActualizado.isPoblacionLgtbiq());
            if (valienteActualizado.getGrupoPoblacional() != null) {
                valiente.setGrupoPoblacional(valienteActualizado.getGrupoPoblacional());
            }
            if (valienteActualizado.getClasificacionValiente() != null) {
                valiente.setClasificacionValiente(valienteActualizado.getClasificacionValiente());
            }
            if (valienteActualizado.getVivienda() != null) {
                valiente.setVivienda(valienteActualizado.getVivienda());
            }

            return valienteRepository.save(valiente);
        } else {
            throw new RuntimeException("Valiente con ID " + id + " no encontrado.");
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

    @Transactional
    public Optional<Valiente> crearValienteSegundaEtapa(Long idPersona, Valiente detallesValiente) {

        Optional<Persona> personaExistente = personaRepository.findById(idPersona);

        if (!personaExistente.isPresent()) {

            return Optional.empty();
        }

        Persona persona = personaExistente.get();


        Valiente nuevoValiente = new Valiente();
        nuevoValiente.setNmIdPersona(persona.getNmIdPersona());

        // Validación y logging de campos obligatorios
        if (detallesValiente.getFechaNacimiento() == null) {

            throw new IllegalArgumentException("La fecha de nacimiento es obligatoria");
        }

        if (detallesValiente.getGrupoPoblacional() == null) {

            throw new IllegalArgumentException("El grupo poblacional es obligatorio");
        }

        if (detallesValiente.getClasificacionValiente() == null) {

            throw new IllegalArgumentException("La clasificación valiente es obligatoria");
        }

        if (detallesValiente.getVivienda() == null) {

            throw new IllegalArgumentException("La vivienda es obligatoria");
        }

        // Asignación de campos con logging
        try {


            nuevoValiente.setFechaNacimiento(detallesValiente.getFechaNacimiento());

            nuevoValiente.setGrupoPoblacional(detallesValiente.getGrupoPoblacional());

            nuevoValiente.setClasificacionValiente(detallesValiente.getClasificacionValiente());

            nuevoValiente.setVivienda(detallesValiente.getVivienda());

            // Campos opcionales
            if (detallesValiente.getTallaCamisa() != null) {
                nuevoValiente.setTallaCamisa(detallesValiente.getTallaCamisa());

            }

            // ... (repetir para otros campos opcionales)

            Valiente valienteGuardado = valienteRepository.save(nuevoValiente);

            return Optional.of(valienteGuardado);

        } catch (Exception e) {

            throw e;
        }
    }

    @Transactional
    public Valiente crearValiente(Long idPersona, DetallesValienteDTO detalles) {
        // 1. Verificar que la persona existe
        Persona persona = personaRepository.findById(idPersona)
                .orElseThrow(() -> new RuntimeException("Persona no encontrada con ID: " + idPersona));

        // 2. Verificar que no sea ya un valiente
        if (valienteRepository.existsById(idPersona)) {
            throw new RuntimeException("La persona ya está registrada como valiente");
        }

        // 3. Crear el valiente
        Valiente valiente = new Valiente();

        // Asignar ID de persona (hereda la PK)
        valiente.setNmIdPersona(persona.getNmIdPersona());

        // Copiar campos obligatorios
        valiente.setFechaNacimiento(detalles.fechaNacimiento());
        valiente.setGrupoPoblacional(
                grupoPoblacionalRepository.findById(detalles.grupoPoblacionalId())
                        .orElseThrow(() -> new RuntimeException("Grupo poblacional no encontrado"))
        );
        valiente.setClasificacionValiente(
                clasificacionValienteRepository.findById(detalles.clasificacionValienteId())
                        .orElseThrow(() -> new RuntimeException("Clasificación de valiente no encontrada"))
        );
        valiente.setVivienda(
                viviendaRepository.findById(Math.toIntExact(detalles.viviendaId()))
                        .orElseThrow(() -> new RuntimeException("Vivienda no encontrada"))
        );

        // Copiar campos opcionales
        if (detalles.tallaCalzado() != null) {
            valiente.setTallaCalzado(detalles.tallaCalzado());
        }
        if (detalles.tallaCamisa() != null) {
            valiente.setTallaCamisa(detalles.tallaCamisa());
        }
        if (detalles.tallaPantalon() != null) {
            valiente.setTallaPantalon(detalles.tallaPantalon());
        }
        if (detalles.nombreResponsable() != null) {
            valiente.setNombreResponsable(detalles.nombreResponsable());
        }
        if (detalles.parentescoResponsable() != null) {
            valiente.setParentescoResponsable(detalles.parentescoResponsable());
        }
        if (detalles.telefonoResponsable() != null) {
            valiente.setTelefonoResponsable(detalles.telefonoResponsable());
        }
        if (detalles.urlGaleria() != null) {
            valiente.setUrlGaleria(detalles.urlGaleria());
        }
        if (detalles.poblacionConflictoArmado() != null) {
            valiente.setPoblacionConflictoArmado(detalles.poblacionConflictoArmado());
        }
        if (detalles.poblacionMigrante() != null) {
            valiente.setPoblacionMigrante(detalles.poblacionMigrante());
        }
        if (detalles.poblacionJoven() != null) {
            valiente.setPoblacionJoven(detalles.poblacionJoven());
        }
        if (detalles.poblacionMujer() != null) {
            valiente.setPoblacionMujer(detalles.poblacionMujer());
        }
        if (detalles.poblacionLgtbiq() != null) {
            valiente.setPoblacionLgtbiq(detalles.poblacionLgtbiq());
        }
        if (detalles.activo() != null) {
            valiente.setActivo(detalles.activo());
        }

        return valienteRepository.save(valiente);
    }
}



