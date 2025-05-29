package org.angelesyvalientes.api.service;

import jakarta.persistence.EntityNotFoundException;
import org.angelesyvalientes.api.dto.CartaAgradecimientoDTO;
import org.angelesyvalientes.api.dto.CertificadoDonacionDTO;
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

    /**
     * Prepara los datos para un certificado de donación.
     * @param donacionId El ID de la donación.
     * @return Un DTO con todos los datos necesarios para el certificado.
     * @throws IllegalArgumentException si la donación no se encuentra o falta información crítica.
     */
    public CertificadoDonacionDTO prepararDatosCertificadoDonacion(int donacionId) {
        logger.info("Preparando datos para certificado de donación con ID: {}", donacionId);

        Optional<Donacion> donacionOptional = donacionRepository.findById(donacionId);

        if (donacionOptional.isEmpty()) {
            logger.warn("Donación con ID {} no encontrada.", donacionId);
            throw new IllegalArgumentException("Donación con ID " + donacionId + " no encontrada.");
        }

        Donacion donacion = donacionOptional.get();
        Persona donante = donacion.getPersona();

        if (donante == null) {
            logger.error("La donación con ID {} no tiene una persona/entidad donante asociada.", donacionId);
            throw new IllegalArgumentException("La donación no tiene una persona/entidad donante asociada.");
        }

        // --- Simulación de datos del donante (AJUSTAR SEGÚN TU MODELO REAL) ---
        // Si Persona representa a una empresa:
        String nombreDonante = donante.getTxPrimerNombre(); // Asumiendo que PrimerNombre guarda la razón social
        String tipoIdentificacion = donante.getTipoIdentificacion() != null ? donante.getTipoIdentificacion().getTxTipoIdentificacion() : "N/A"; // Asumiendo TipoIdentificacion existe y tiene getTipoIdentificacion()
        String numeroIdentificacion = donante.getTxNumeroIdentificacion(); // Asumiendo que NumeroIdentificacion guarda el NIT

        // Si la donación tiene un monto
        double monto = 0.0; // Necesitas un campo para el monto en tu entidad Donacion, por ejemplo 'nm_valor'
        // donacion.getMonto(); // <-- Necesitas este campo en Donacion
        // Para este ejemplo, simulo un monto si aún no lo tienes
        if (donacionId == 1) monto = 10000000.0; // Simulación para el ejemplo de la imagen
        else monto = 500000.0; // Otro valor por defecto

        // Asumo que el tipo de donación se obtiene de la relación y las observaciones
        String tipoDonacionDesc = donacion.getTipoDonacion() != null ? donacion.getTipoDonacion().getTipoDonacion() : "Sin especificar";
        String observacion = donacion.getObservacion() != null ? donacion.getObservacion() : "";

        // Unir el tipo de donación y las observaciones para el certificado
        String tipoDonacionCertificado = tipoDonacionDesc;
        String programaApoyado = ""; // Esto es un campo que podrías añadir a Donacion o extraer de Observacion
        if (!observacion.isEmpty()) {
            // Si la observacion contiene "donación de chaquetas, apoyando al programa de cultura y educación (VALIENTES)"
            // necesitarías parsearla o tener campos dedicados en Donacion
            // Por ahora, lo simularé o lo dejaré como un ejemplo.
            // Aquí podrías tener lógica para extraer el tipo de donación real y el programa
            if (observacion.contains("chaquetas")) {
                tipoDonacionCertificado = "Donación de " + observacion;
            }
            if (observacion.contains("programa de cultura y educación")) {
                programaApoyado = "apoyando al " + observacion;
            } else {
                programaApoyado = observacion; // Si no hay programa específico, la observación es el detalle
            }

        }
        // Lógica para convertir el monto numérico a texto (ver siguiente sección)
        String montoEnTexto = convertirMontoATexto(monto);


        return new CertificadoDonacionDTO(
                nombreDonante,
                tipoIdentificacion,
                numeroIdentificacion,
                donacion.getFecha(),
                monto,
                montoEnTexto,
                tipoDonacionCertificado,
                programaApoyado // Ajustar esto para obtener el programa real
        );
    }

    /**
     * Método auxiliar para convertir un monto numérico a texto (en pesos colombianos).
     * Esta es una implementación simplificada y podrías necesitar una librería más robusta para todos los casos.
     */
    private String convertirMontoATexto(double monto) {
        // Ejemplo simplificado para "DIEZ MILLONES DE PESOS".
        // Para una solución robusta en español, se recomienda una librería como "numeral-to-words" o implementar un conversor completo.
        if (monto == 10000000.0) {
            return "DIEZ MILLONES DE PESOS ($ 10.000.000)";
        } else if (monto == 500000.0) {
            return "QUINIENTOS MIL PESOS ($ 500.000)";
        }
        return "El monto es " + String.format("%,.0f", monto) + " PESOS"; // Formato básico
    }
    /**
     * Prepara los datos para una carta de agradecimiento basada en el ID de la donación.
     * @param donacionId El ID de la donación.
     * @return Un DTO con los datos necesarios para la carta.
     * @throws IllegalArgumentException si la donación no se encuentra o no tiene una persona asociada.
     */
    public CartaAgradecimientoDTO prepararDatosCartaAgradecimiento(int donacionId) {
        logger.info("Preparando datos para carta de agradecimiento de donación con ID: {}", donacionId);

        Optional<Donacion> donacionOptional = donacionRepository.findById(donacionId);

        if (donacionOptional.isEmpty()) {
            logger.warn("Donación con ID {} no encontrada.", donacionId);
            throw new IllegalArgumentException("Donación con ID " + donacionId + " no encontrada.");
        }

        Donacion donacion = donacionOptional.get();
        Persona donante = donacion.getPersona(); // Obtener la Persona asociada a la donación

        if (donante == null) {
            logger.error("La donación con ID {} no tiene una persona donante asociada.", donacionId);
            throw new IllegalArgumentException("La donación no tiene una persona donante asociada.");
        }

        // Construir el DTO con la información de la donación y el donante
        return new CartaAgradecimientoDTO(
                donante.getTxPrimerNombre(),
                donante.getTxPrimerApellido(),
                donante.getTipoIdentificacion() != null ? donante.getTipoIdentificacion().getTxTipoIdentificacion() : "N/A", // Asegúrate de tener el getter adecuado
                donante.getTxNumeroIdentificacion(),
                donacion.getFecha(),
                donacion.getTipoDonacion() != null ? donacion.getTipoDonacion().getTipoDonacion() : "Desconocido", // Asegúrate de tener el getter adecuado
                donacion.getObservacion()
        );
    }

}
