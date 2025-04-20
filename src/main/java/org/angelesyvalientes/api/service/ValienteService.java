package org.angelesyvalientes.api.service;

import jakarta.persistence.EntityManager;
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
    GrupoEtnicoRepository grupoEtnicoRepository;

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
            //valiente.setPoblacionConflictoArmado(valienteActualizado.isPoblacionConflictoArmado());
            //valiente.setPoblacionMigrante(valienteActualizado.isPoblacionMigrante());
            //valiente.setPoblacionJoven(valienteActualizado.isPoblacionJoven());
            //valiente.setPoblacionMujer(valienteActualizado.isPoblacionMujer());
            valiente.setPoblacionLgtbiq(valienteActualizado.isPoblacionLgtbiq());
            if (valienteActualizado.getGrupoEtnico() != null) {
                valiente.setGrupoEtnico(valienteActualizado.getGrupoEtnico());
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

        if (detallesValiente.getGrupoEtnico() == null) {

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

            nuevoValiente.setGrupoEtnico(detallesValiente.getGrupoEtnico());

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

        // 1. Verificar persona existe
        Persona persona = personaRepository.findById(idPersona)
                .orElseThrow(() -> {
                    logger.error("Persona no encontrada con ID: {}", idPersona);
                    return new RuntimeException("Persona no encontrada");
                });

        // 2. Verificar no es ya valiente
        if (valienteRepository.existsById(idPersona)) {
            logger.warn("La Persona ya está registrada como Valiente");
            throw new RuntimeException("La persona ya es valiente");
        }

        // 3. Crear nuevo valiente
        Valiente valiente = new Valiente();
        valiente.setNmIdPersona(persona.getNmIdPersona());


        // Campos obligatorios
        valiente.setFechaNacimiento(detalles.fechaNacimiento());
        valiente.setGrupoEtnico(grupoEtnicoRepository.findById(detalles.grupoEtnicoId())
                .orElseThrow(() -> new RuntimeException("Grupo etnico no encontrado")));
        valiente.setClasificacionValiente(clasificacionValienteRepository.findById(detalles.clasificacionValienteId())
                .orElseThrow(() -> new RuntimeException("Clasificación no encontrada")));
        valiente.setVivienda(viviendaRepository.findById(Math.toIntExact(detalles.viviendaId()))
                .orElseThrow(() -> new RuntimeException("Vivienda no encontrada")));

        // Campos opcionales
        Optional.ofNullable(detalles.tallaCalzado()).ifPresent(valiente::setTallaCalzado);
        Optional.ofNullable(detalles.tallaCamisa()).ifPresent(valiente::setTallaCamisa);
        Optional.ofNullable(detalles.tallaPantalon()).ifPresent(valiente::setTallaPantalon);
        Optional.ofNullable(detalles.nombreResponsable()).ifPresent(valiente::setNombreResponsable);
        Optional.ofNullable(detalles.parentescoResponsable()).ifPresent(valiente::setParentescoResponsable);
        Optional.ofNullable(detalles.telefonoResponsable()).ifPresent(valiente::setTelefonoResponsable);
        Optional.ofNullable(detalles.urlGaleria()).ifPresent(valiente::setUrlGaleria);
        //Optional.ofNullable(detalles.poblacionConflictoArmado()).ifPresent(valiente::setPoblacionConflictoArmado);
       // Optional.ofNullable(detalles.poblacionMigrante()).ifPresent(valiente::setPoblacionMigrante);
       // Optional.ofNullable(detalles.poblacionJoven()).ifPresent(valiente::setPoblacionJoven);
     //   Optional.ofNullable(detalles.poblacionMujer()).ifPresent(valiente::setPoblacionMujer);
        Optional.ofNullable(detalles.poblacionLgtbiq()).ifPresent(valiente::setPoblacionLgtbiq);
        Optional.ofNullable(detalles.activo()).ifPresent(valiente::setActivo);

        // Guardar
        return valienteRepository.saveAndFlush(valiente);
    }
}



