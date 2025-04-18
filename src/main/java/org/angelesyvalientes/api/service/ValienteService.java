package org.angelesyvalientes.api.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.FlushModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.angelesyvalientes.api.DetallesValienteDTO.DetallesValienteDTO;
import org.angelesyvalientes.api.persistence.entity.*;
import org.angelesyvalientes.api.persistence.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ValienteService {
    @PersistenceContext
    private EntityManager entityManager;

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
        logger.info("Iniciando la creación de un Valiente para Persona con ID: {}", idPersona);
        logger.info("Detalles recibidos del DTO: {}", detalles);

        // 1. Verificar que la persona existe
        logger.info("Buscando Persona con ID: {}", idPersona);
        Persona persona = personaRepository.findById(idPersona)
                .orElseThrow(() -> {
                    logger.error("No se encontró la Persona con ID: {}", idPersona);
                    return new RuntimeException("Persona no encontrada con ID: " + idPersona);
                });
        logger.info("Persona encontrada: {}", persona);

        // 2. Verificar que no sea ya un valiente
        logger.info("Verificando si la Persona con ID {} ya es un Valiente", idPersona);
        if (valienteRepository.existsById(idPersona)) {
            logger.warn("La Persona con ID {} ya está registrada como Valiente", idPersona);
            throw new RuntimeException("La persona ya está registrada como valiente");
        }
        logger.info("La Persona con ID {} no es un Valiente", idPersona);

        // 3. Crear el valiente
        Valiente valiente = new Valiente();

        // Asignar ID de persona (hereda la PK)
        valiente.setNmIdPersona(persona.getNmIdPersona());
        logger.info("Asignado nmIdPersona al Valiente: {}", valiente.getNmIdPersona());

        // Copiar campos obligatorios
        logger.info("Fecha de Nacimiento recibida: {}", detalles.fechaNacimiento());
        valiente.setFechaNacimiento(detalles.fechaNacimiento());

        logger.info("Buscando GrupoPoblacional con ID: {}", detalles.grupoPoblacionalId());
        GrupoPoblacional grupoPoblacional = grupoPoblacionalRepository.findById(detalles.grupoPoblacionalId())
                .orElseThrow(() -> {
                    logger.error("No se encontró el GrupoPoblacional con ID: {}", detalles.grupoPoblacionalId());
                    return new RuntimeException("Grupo poblacional no encontrado");
                });
        logger.info("GrupoPoblacional encontrado: {}", grupoPoblacional);
        valiente.setGrupoPoblacional(grupoPoblacional);

        logger.info("Buscando ClasificacionValiente con ID: {}", detalles.clasificacionValienteId());
        ClasificacionValiente clasificacionValiente = clasificacionValienteRepository.findById(detalles.clasificacionValienteId())
                .orElseThrow(() -> {
                    logger.error("No se encontró la ClasificacionValiente con ID: {}", detalles.clasificacionValienteId());
                    return new RuntimeException("Clasificación de valiente no encontrada");
                });
        logger.info("ClasificacionValiente encontrada: {}", clasificacionValiente);
        valiente.setClasificacionValiente(clasificacionValiente);

        logger.info("Buscando Vivienda con ID: {}", detalles.viviendaId());
        Vivienda vivienda = viviendaRepository.findById(Math.toIntExact(detalles.viviendaId()))
                .orElseThrow(() -> {
                    logger.error("No se encontró la Vivienda con ID: {}", detalles.viviendaId());
                    return new RuntimeException("Vivienda no encontrada");
                });
        logger.info("Vivienda encontrada: {}", vivienda);
        valiente.setVivienda(vivienda);

        // Copiar campos opcionales
        logger.info("Talla Calzado recibida: {}", detalles.tallaCalzado());
        if (detalles.tallaCalzado() != null) {
            valiente.setTallaCalzado(detalles.tallaCalzado());
        }
        logger.info("Talla Camisa recibida: {}", detalles.tallaCamisa());
        if (detalles.tallaCamisa() != null) {
            valiente.setTallaCamisa(detalles.tallaCamisa());
        }
        logger.info("Talla Pantalon recibida: {}", detalles.tallaPantalon());
        if (detalles.tallaPantalon() != null) {
            valiente.setTallaPantalon(detalles.tallaPantalon());
        }
        logger.info("Nombre Responsable recibido: {}", detalles.nombreResponsable());
        if (detalles.nombreResponsable() != null) {
            valiente.setNombreResponsable(detalles.nombreResponsable());
        }
        logger.info("Parentesco Responsable recibido: {}", detalles.parentescoResponsable());
        if (detalles.parentescoResponsable() != null) {
            valiente.setParentescoResponsable(detalles.parentescoResponsable());
        }
        logger.info("Teléfono Responsable recibido: {}", detalles.telefonoResponsable());
        if (detalles.telefonoResponsable() != null) {
            valiente.setTelefonoResponsable(detalles.telefonoResponsable());
        }
        logger.info("URL Galería recibida: {}", detalles.urlGaleria());
        if (detalles.urlGaleria() != null) {
            valiente.setUrlGaleria(detalles.urlGaleria());
        }
        logger.info("Población Conflicto Armado recibida: {}", detalles.poblacionConflictoArmado());
        if (detalles.poblacionConflictoArmado() != null) {
            valiente.setPoblacionConflictoArmado(detalles.poblacionConflictoArmado());
        }
        logger.info("Población Migrante recibida: {}", detalles.poblacionMigrante());
        if (detalles.poblacionMigrante() != null) {
            valiente.setPoblacionMigrante(detalles.poblacionMigrante());
        }
        logger.info("Población Joven recibida: {}", detalles.poblacionJoven());
        if (detalles.poblacionJoven() != null) {
            valiente.setPoblacionJoven(detalles.poblacionJoven());
        }
        logger.info("Población Mujer recibida: {}", detalles.poblacionMujer());
        if (detalles.poblacionMujer() != null) {
            valiente.setPoblacionMujer(detalles.poblacionMujer());
        }
        logger.info("Población Lgtbiq recibida: {}", detalles.poblacionLgtbiq());
        if (detalles.poblacionLgtbiq() != null) {
            valiente.setPoblacionLgtbiq(detalles.poblacionLgtbiq());
        }
        logger.info("Activo recibido: {}", detalles.activo());
        if (detalles.activo() != null) {
            valiente.setActivo(detalles.activo());
        }


        entityManager.setFlushMode(FlushModeType.AUTO); // Ejemplo: Asegurándose de que esté en AUTO
        entityManager.persist(valiente);
        // entityManager.flush(); // También podrías ver un flush explícito
        logger.info("Guardando el Valiente: {}", valiente);
        return valienteRepository.save(valiente);
    }
}



