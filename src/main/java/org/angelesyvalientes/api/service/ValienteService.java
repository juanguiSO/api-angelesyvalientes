package org.angelesyvalientes.api.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.angelesyvalientes.api.dto.DetallesValienteDTO;
import org.angelesyvalientes.api.dto.ValienteCumpleanosDTO;
import org.angelesyvalientes.api.persistence.entity.*;
import org.angelesyvalientes.api.persistence.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.MonthDay;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



@Service
public class ValienteService {

    // --- INYECCIÓN DE ENTITY MANAGER ---

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

    // Constructor para inyección de PersonaRepository
    public ValienteService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    // Constructor para inyección de todas las dependencias (necesario si hay más de un constructor @Autowired)
    @Autowired
    public ValienteService(
            PersonaRepository personaRepository,
            ValienteRepository valienteRepository,
            GrupoEtnicoRepository grupoEtnicoRepository,
            ClasificacionValienteRepository clasificacionValienteRepository,
            ViviendaRepository viviendaRepository,
            FichaPorValienteRepository fichaPorValienteRepository
    ) {
        this.personaRepository = personaRepository;
        this.valienteRepository = valienteRepository;
        this.grupoEtnicoRepository = grupoEtnicoRepository;
        this.clasificacionValienteRepository = clasificacionValienteRepository;
        this.viviendaRepository = viviendaRepository;
        this.fichaPorValienteRepository = fichaPorValienteRepository;
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
    @Transactional
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
    public Optional<Valiente> crearValienteSegundaEtapa(Integer idPersona, Valiente detallesValiente) {

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

    /**
     * Crea un registro Valiente asociado a una Persona existente.
     *
     * @param idPersona El ID de la Persona existente.
     * @param detalles  DTO con los datos específicos del Valiente a crear.
     * @return La entidad Valiente persistida.
     * @throws RuntimeException Si la Persona no se encuentra, si ya es Valiente,
     * o si alguna entidad relacionada (GrupoEtnico, etc.) no se encuentra.
     */
    @Transactional
    public Valiente crearValiente(Integer idPersona, DetallesValienteDTO detalles) throws RuntimeException {
        logger.info("Iniciando proceso (NATIVE INSERT/UPDATE) para crear Valiente asociado a Persona ID: {}", idPersona);

        // 1. Validar y Obtener la Persona existente (Solo para validación y obtener versión)
        Integer versionLeidaPersona = personaRepository.findById(idPersona)
                .map(Persona::getVersion)
                .orElseThrow(() -> {
                    String errorMsg = String.format("Error al crear Valiente: Persona con ID %d no encontrada.", idPersona);
                    logger.error(errorMsg);
                    return new RuntimeException(errorMsg);
                });
        logger.debug("Persona ID {} encontrada. Versión leída: {}", idPersona, versionLeidaPersona);
        if (versionLeidaPersona == null) {
            logger.warn("Versión leída de Persona ID {} es NULL, tratando como 0.", idPersona);
            versionLeidaPersona = 0;
        }

        // 2. Validar que esta Persona no sea ya un Valiente
        if (valienteRepository.existsById(Long.valueOf(idPersona))) { // Convertir idPersona a Long si valienteRepository.findById espera Long
            String errorMsg = String.format("Error al crear Valiente: La Persona con ID %d ya está registrada como Valiente.", idPersona);
            logger.warn(errorMsg);
            throw new RuntimeException(errorMsg);
        }

        // 3. Preparar Datos y Ejecutar INSERT NATIVO en la tabla 'valiente'
        logger.info("Intentando INSERT NATIVO en tabla 'valiente' para ID: {}", idPersona);
        try {
            // Buscar IDs de entidades relacionadas (manejar null para viviendaId)
            Integer grupoEtnicoId = Optional.ofNullable(detalles.grupoEtnicoId()).orElseThrow(() -> new RuntimeException("grupoEtnicoId es null"));
            if (!grupoEtnicoRepository.existsById(grupoEtnicoId)) throw new RuntimeException("Grupo etnico no encontrado");

            // CORRECCIÓN: Mantener clasificacionValienteId como Long si el repositorio lo espera como Long
            Long clasificacionValienteId = Optional.ofNullable(detalles.clasificacionValienteId()).orElseThrow(() -> new RuntimeException("clasificacionValienteId es null"));
            if (!clasificacionValienteRepository.existsById(clasificacionValienteId)) throw new RuntimeException("Clasificación no encontrada");

            // Asumiendo que viviendaId en DetallesValienteDTO es Long, y el repositorio de Vivienda usa Integer
            Integer viviendaId = detalles.viviendaId() != null ? Math.toIntExact(detalles.viviendaId()) : null;
            if (viviendaId != null && !viviendaRepository.existsById(viviendaId)) {
                throw new RuntimeException("Vivienda no encontrada");
            }

            String nativeSqlInsert = "INSERT INTO valiente (nm_id_persona, fe_nacimiento, nm_id_grupo_etnico, nm_id_clasificacion_valiente, nm_id_vivienda, " +
                    "tx_talla_calzado, tx_talla_camisa, tx_talla_pantalon, tx_nombre_responsable, tx_parentesco_responsable, " +
                    "tx_telefono_responsable, tx_url_galeria, bo_poblacion_lgtbiq, bo_activo) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            Query insertQuery = entityManager.createNativeQuery(nativeSqlInsert);
            insertQuery.setParameter(1, idPersona);
            insertQuery.setParameter(2, detalles.fechaNacimiento());
            insertQuery.setParameter(3, grupoEtnicoId);
            insertQuery.setParameter(4, clasificacionValienteId); // Usar el Long corregido
            insertQuery.setParameter(5, viviendaId); // Permitir valor null
            insertQuery.setParameter(6, detalles.tallaCalzado());
            insertQuery.setParameter(7, detalles.tallaCamisa());
            insertQuery.setParameter(8, detalles.tallaPantalon());
            insertQuery.setParameter(9, detalles.nombreResponsable());
            insertQuery.setParameter(10, detalles.parentescoResponsable());
            insertQuery.setParameter(11, detalles.telefonoResponsable());
            insertQuery.setParameter(12, detalles.urlGaleria());
            insertQuery.setParameter(13, detalles.poblacionLgtbiq() != null ? detalles.poblacionLgtbiq() : false);
            insertQuery.setParameter(14, detalles.activo() != null ? detalles.activo() : true);

            int rowsAffectedInsert = insertQuery.executeUpdate();
            if (rowsAffectedInsert != 1) {
                logger.error("INSERT NATIVO fallido (rowsAffected={}) para Valiente ID: {}", rowsAffectedInsert, idPersona);
                throw new RuntimeException("No se pudo insertar el registro Valiente.");
            }
            logger.info("INSERT NATIVO exitoso para Valiente ID: {}", idPersona);

        } catch (Exception e) {
            logger.error("Error durante INSERT NATIVO para Valiente ID {}: {}", idPersona, e.getMessage(), e);
            throw new RuntimeException("Error al insertar registro Valiente nativamente.", e);
        }

        // 4. Actualizar la versión de la Persona con UPDATE NATIVO
        logger.info("Intentando UPDATE NATIVO para incrementar versión de Persona ID: {} (versión esperada: {})", idPersona, versionLeidaPersona);
        try {
            String nativeSqlUpdate = "UPDATE persona SET nm_version = nm_version + 1 WHERE nm_id_persona = ? AND nm_version = ?";
            Query updateQuery = entityManager.createNativeQuery(nativeSqlUpdate);
            updateQuery.setParameter(1, idPersona);
            updateQuery.setParameter(2, versionLeidaPersona); // Condición optimista

            int rowsAffectedUpdate = updateQuery.executeUpdate();

            if (rowsAffectedUpdate != 1) {
                logger.error("UPDATE NATIVO de versión falló (rowsAffected={}) para Persona ID: {}. ¡Conflicto de versión detectado!", rowsAffectedUpdate, idPersona);
                throw new ObjectOptimisticLockingFailureException("Conflicto de versión al actualizar Persona ID " + idPersona + " nativamente.", null);
            }
            logger.info("UPDATE NATIVO de versión exitoso para Persona ID: {}", idPersona);

        } catch (ObjectOptimisticLockingFailureException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error durante UPDATE NATIVO de versión para Persona ID {}: {}", idPersona, e.getMessage(), e);
            throw new RuntimeException("Error al actualizar versión de Persona nativamente.", e);
        }

        // 5. Limpiar el contexto y buscar el Valiente final
        try {
            logger.debug("Limpiando EntityManager para refrescar contexto...");
            entityManager.clear();

            logger.info("Buscando entidad Valiente completa después de operaciones nativas, ID: {}", idPersona);
            Valiente valienteFinal = valienteRepository.findById(Long.valueOf(idPersona)) // Asegurarse de que findById reciba Long
                    .orElseThrow(() -> {
                        logger.error("¡ERROR CRÍTICO! Valiente ID {} no encontrado después de INSERT/UPDATE nativo y clear().", idPersona);
                        return new RuntimeException("Valiente no encontrado después de creación exitosa aparente.");
                    });

            logger.info("Valiente ID {} encontrado y cargado exitosamente.", idPersona);
            logger.debug("Versión final del Valiente/Persona cargado: {}", valienteFinal.getVersion());
            return valienteFinal;

        } catch (Exception e) {
            logger.error("Error al limpiar contexto o buscar Valiente final ID {}: {}", idPersona, e.getMessage(), e);
            throw new RuntimeException("Error al obtener la entidad Valiente final.", e);
        }
    }

    @Transactional
    public Valiente asignarVivienda(Integer idValiente, Integer idVivienda) {
        logger.info("Iniciando asignación de Vivienda ID {} al Valiente ID: {}", idVivienda, idValiente);

        // 1. Buscar el Valiente
        Optional<Valiente> valienteOptional = valienteRepository.findById(Long.valueOf(idValiente)); // Asegurarse de que findById reciba Long
        if (valienteOptional.isEmpty()) {
            String errorMsg = String.format("No se encontró el Valiente con ID: %d para asignar la vivienda.", idValiente);
            logger.warn(errorMsg);
            throw new RuntimeException(errorMsg);
        }
        Valiente valiente = valienteOptional.get();
        logger.debug("Valiente encontrado: {}", valiente);

        // 2. Buscar la Vivienda
        Optional<Vivienda> viviendaOptional = viviendaRepository.findById(idVivienda);
        if (viviendaOptional.isEmpty()) {
            String errorMsg = String.format("No se encontró la Vivienda con ID: %d.", idVivienda);
            logger.warn(errorMsg);
            throw new RuntimeException(errorMsg);
        }
        Vivienda vivienda = viviendaOptional.get();
        logger.debug("Vivienda encontrada: {}", vivienda);

        // 3. Asignar la Vivienda al Valiente
        valiente.setVivienda(vivienda);
        logger.info("Asignando Vivienda ID {} al Valiente ID: {}", idVivienda, idValiente);

        // 4. Guardar los cambios en el Valiente
        Valiente valienteActualizado = valienteRepository.save(valiente);
        logger.info("Vivienda ID {} asignada exitosamente al Valiente ID: {}. Valiente actualizado: {}", idVivienda, idValiente, valienteActualizado);
        return valienteActualizado;
    }


    public List<ValienteCumpleanosDTO> getCumpleanosOrdenados() {
        List<Valiente> valientes = valienteRepository.findAll();
        LocalDate hoy = LocalDate.now();


        // Ordenar los cumpleaños por proximidad
        return valientes.stream()
                .sorted((v1, v2) -> {
                    int distancia1 = calcularDiferenciaDias(hoy, v1.getFechaNacimiento());
                    int distancia2 = calcularDiferenciaDias(hoy, v2.getFechaNacimiento());
                    return Integer.compare(distancia1, distancia2);
                })
                .map(v -> new ValienteCumpleanosDTO(v.getTxPrimerNombre(), v.getTxPrimerApellido(), v.getFechaNacimiento()))
                .collect(Collectors.toList());
    }


    // Método auxiliar para calcular la diferencia en días
    private int calcularDiferenciaDias(LocalDate hoy, LocalDate cumpleaños) {
        LocalDate cumpleañosEsteAño = cumpleaños.withYear(hoy.getYear());

        // Si el cumpleaños ya pasó, consideramos la fecha del próximo año
        if (cumpleañosEsteAño.isBefore(hoy)) {
            cumpleañosEsteAño = cumpleañosEsteAño.plusYears(1);
        }

        return (int) java.time.temporal.ChronoUnit.DAYS.between(hoy, cumpleañosEsteAño);
    }



}
