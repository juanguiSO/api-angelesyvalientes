package org.angelesyvalientes.api.service;

import jakarta.persistence.EntityNotFoundException;
import org.angelesyvalientes.api.persistence.entity.Donacion;
import org.angelesyvalientes.api.persistence.entity.Persona; // Importar Persona
import org.angelesyvalientes.api.persistence.entity.TipoDonacion; // Importar TipoDonacion
import org.angelesyvalientes.api.persistence.repository.DonacionRepository;
import org.angelesyvalientes.api.persistence.repository.PersonaRepository; // Asumo que tienes este repositorio
import org.angelesyvalientes.api.persistence.repository.TipoDonacionRepository; // Asumo que tienes este repositorio
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importar para transacciones

import java.util.List;
import java.util.Optional;

// Suponiendo que tienes un DTO para la solicitud de creación/actualización:
import org.angelesyvalientes.api.dto.DonacionRequestDTO; // Asegúrate de que este DTO exista

/**
 * Servicio que gestiona las operaciones relacionadas con la entidad {@link Donacion}.
 * Proporciona métodos para obtener, crear, actualizar y eliminar donaciones
 * de la base de datos a través del {@link DonacionRepository}.
 */
@Service
public class DonacionService {

    private final DonacionRepository donacionRepository;
    private final TipoDonacionRepository tipoDonacionRepository; // Nuevo: Repositorio para TipoDonacion
    private final PersonaRepository personaRepository; // Nuevo: Repositorio para Persona
    private static final Logger logger = LoggerFactory.getLogger(DonacionService.class);

    /**
     * Constructor de la clase {@code DonacionService}.
     * Recibe instancias de los repositorios a través de la inyección de dependencias.
     */
    @Autowired
    public DonacionService(
            DonacionRepository donacionRepository,
            TipoDonacionRepository tipoDonacionRepository,
            PersonaRepository personaRepository) {
        this.donacionRepository = donacionRepository;
        this.tipoDonacionRepository = tipoDonacionRepository;
        this.personaRepository = personaRepository;
    }

    /**
     * Obtiene una {@link Donacion} de la base de datos por su identificador único.
     *
     * @param id El identificador único de la donación a buscar.
     * @return Un {@link Optional} que contiene la {@link Donacion} si se encuentra,
     * o un {@link Optional} vacío en caso contrario.
     */
    public Optional<Donacion> getDonacion(Integer id) {
        return donacionRepository.findById(id);
    }

    /**
     * Obtiene una lista con todas las {@link Donacion} almacenadas en la base de datos.
     *
     * @return Una {@link List} que contiene todas las donaciones encontradas.
     * Si no hay donaciones, la lista estará vacía.
     */
    public List<Donacion> getAllDonaciones() {
        return donacionRepository.findAll();
    }

    /**
     * Guarda una nueva {@link Donacion} en la base de datos a partir de un DTO de solicitud.
     * Carga las entidades relacionadas (TipoDonacion y Persona) antes de guardar la Donacion.
     *
     * @param donacionDTO El objeto {@link DonacionRequestDTO} con los datos de la nueva donación.
     * @return El objeto {@link Donacion} guardado.
     * @throws EntityNotFoundException Si el TipoDonacion o la Persona referenciados no existen.
     */
    @Transactional
    public Donacion createDonacion(DonacionRequestDTO donacionDTO) {
        logger.info("DonacionService: Intentando guardar donación desde DTO. Datos recibidos: {}", donacionDTO);

        Donacion donacion = new Donacion();
        donacion.setFecha(donacionDTO.getFecha());
        donacion.setObservacion(donacionDTO.getObservacion());

        // Cargar y asignar TipoDonacion
        TipoDonacion tipoDonacion = tipoDonacionRepository.findById(donacionDTO.getIdTipoDonacion())
                .orElseThrow(() -> new EntityNotFoundException("TipoDonacion con ID " + donacionDTO.getIdTipoDonacion() + " no encontrada."));
        donacion.setTipoDonacion(tipoDonacion);

        // Cargar y asignar Persona
        Persona persona = personaRepository.findById(donacionDTO.getIdPersona())
                .orElseThrow(() -> new EntityNotFoundException("Persona con ID " + donacionDTO.getIdPersona() + " no encontrada."));
        donacion.setPersona(persona);

        return donacionRepository.save(donacion);
    }

    /**
     * Actualiza la información de una {@link Donacion} existente en la base de datos a partir de un DTO de solicitud.
     * Primero, busca la donación por su ID. Si se encuentra, actualiza sus campos
     * y las relaciones si se proporcionan IDs válidos, y luego guarda los cambios.
     *
     * @param id                  El identificador único de la donación a actualizar.
     * @param donacionDTO         El objeto {@link DonacionRequestDTO} con la información actualizada.
     * @return El objeto {@link Donacion} actualizado.
     * @throws EntityNotFoundException Si no se encuentra una donación con el ID proporcionado,
     * o si el TipoDonacion o la Persona referenciados no existen.
     */
    @Transactional
    public Donacion updateDonacion(Integer id, DonacionRequestDTO donacionDTO) {
        Donacion donacion = donacionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Donacion con ID " + id + " no encontrada para actualizar."));

        // Actualizar campos simples
        if (donacionDTO.getFecha() != null) {
            donacion.setFecha(donacionDTO.getFecha());
        }
        if (donacionDTO.getObservacion() != null) {
            donacion.setObservacion(donacionDTO.getObservacion());
        }

        // Actualizar TipoDonacion si se proporciona un ID
        if (donacionDTO.getIdTipoDonacion() != null) {
            TipoDonacion tipoDonacion = tipoDonacionRepository.findById(donacionDTO.getIdTipoDonacion())
                    .orElseThrow(() -> new EntityNotFoundException("TipoDonacion con ID " + donacionDTO.getIdTipoDonacion() + " no encontrada."));
            donacion.setTipoDonacion(tipoDonacion);
        }

        // Actualizar Persona si se proporciona un ID
        if (donacionDTO.getIdPersona() != null) {
            Persona persona = personaRepository.findById(donacionDTO.getIdPersona())
                    .orElseThrow(() -> new EntityNotFoundException("Persona con ID " + donacionDTO.getIdPersona() + " no encontrada."));
            donacion.setPersona(persona);
        }

        return donacionRepository.save(donacion);
    }

    /**
     * Elimina una {@link Donacion} de la base de datos por su identificador único.
     *
     * @param id El identificador único de la donación a eliminar.
     * @throws EntityNotFoundException Si no se encuentra una donación con el ID proporcionado.
     */
    public void deleteDonacion(Integer id) {
        if (!donacionRepository.existsById(id)) {
            throw new EntityNotFoundException("Donacion con ID " + id + " no encontrada para eliminar.");
        }
        donacionRepository.deleteById(id);
    }

    /**
     * Obtiene una lista de todas las donaciones realizadas por una persona específica.
     *
     * @param idPersona El ID (nm_id_persona) de la persona cuyas donaciones se desean listar.
     * @return Una lista de objetos Donacion, o una lista vacía si no se encuentran donaciones para esa persona.
     * @throws EntityNotFoundException Si la Persona con el ID dado no existe en el sistema.
     */
    @Transactional(readOnly = true) // Este método solo lee, es buena práctica marcarlo como readOnly
    public List<Donacion> getDonacionesByPersonaId(Integer idPersona) {
        // Opcional: Verificar que la persona exista antes de buscar sus donaciones.
        // Esto es una validación para dar un error más específico si la persona no existe,
        // en lugar de simplemente devolver una lista vacía.
        if (!personaRepository.existsById(idPersona)) {
            throw new EntityNotFoundException("Persona con ID " + idPersona + " no encontrada.");
        }

        // Utiliza el nuevo método definido en DonacionRepository
        return donacionRepository.findByPersona_NmIdPersona(idPersona);
    }
}
