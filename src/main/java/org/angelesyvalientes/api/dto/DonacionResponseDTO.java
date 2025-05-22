package org.angelesyvalientes.api.dto;

import org.angelesyvalientes.api.persistence.entity.Donacion; // Importar la entidad Donacion
import org.angelesyvalientes.api.persistence.entity.TipoDonacion; // Importar TipoDonacion
import org.angelesyvalientes.api.persistence.entity.Persona; // Importar Persona

import java.time.LocalDate;

/**
 * DTO (Data Transfer Object) para la respuesta de la API de Donaciones.
 * Define la estructura de los datos de una donación que se enviarán al cliente.
 * Incluye solo los campos necesarios y los IDs de las relaciones para evitar problemas de serialización.
 */
public class DonacionResponseDTO {

    private Integer idDonacion;
    private LocalDate fecha;
    private String observacion;
    private Integer idTipoDonacion; // Solo el ID del TipoDonacion
    private String nombreTipoDonacion; // Opcional: para mostrar el nombre del tipo de donación
    private Integer idPersona;      // Solo el ID de la Persona
    private String nombrePersona;   // Opcional: para mostrar el nombre de la persona (donante)

    // Constructor vacío (necesario para la deserialización de JSON si se usa en request, aunque aquí es response)
    public DonacionResponseDTO() {
    }

    // Constructor para mapear desde la entidad Donacion
    public DonacionResponseDTO(Integer idDonacion, LocalDate fecha, String observacion, Integer idTipoDonacion, String nombreTipoDonacion, Integer idPersona, String nombrePersona) {
        this.idDonacion = idDonacion;
        this.fecha = fecha;
        this.observacion = observacion;
        this.idTipoDonacion = idTipoDonacion;
        this.nombreTipoDonacion = nombreTipoDonacion;
        this.idPersona = idPersona;
        this.nombrePersona = nombrePersona;
    }

    /**
     * Método estático de conveniencia para mapear una entidad Donacion a un DonacionResponseDTO.
     * Esto es útil en el controlador o servicio para transformar la entidad antes de enviarla como respuesta.
     *
     * @param donacion La entidad Donacion a mapear.
     * @return Un DonacionResponseDTO con los datos de la entidad.
     */
    public static DonacionResponseDTO fromEntity(Donacion donacion) {
        // Asegurarse de que las relaciones no sean nulas antes de intentar acceder a ellas
        // Y que los getters de ID y nombre existan en TipoDonacion y Persona
        Integer tipoDonacionId = null;
        String tipoDonacionNombre = null;
        if (donacion.getTipoDonacion() != null) {
            // VERIFICA AQUÍ: Asegúrate que TipoDonacion.java tiene un método getIdTipoDonacion()
            // Si el ID de TipoDonacion se llama 'id', entonces usa donacion.getTipoDonacion().getId()
            tipoDonacionId = donacion.getTipoDonacion().getId();
            // VERIFICA AQUÍ: Asegúrate que TipoDonacion.java tiene un método getNombre()
            tipoDonacionNombre = donacion.getTipoDonacion().getTipoDonacion();
        }

        Integer personaId = null;
        String personaNombre = null;
        if (donacion.getPersona() != null) {
            // VERIFICA AQUÍ: Asegúrate que Persona.java tiene un método getIdPersona()
            // Si el ID de Persona se llama 'id', entonces usa donacion.getPersona().getId()
            personaId = donacion.getPersona().getNmIdPersona();
            // VERIFICA AQUÍ: Asegúrate que Persona.java tiene un método getNombre()
            personaNombre = donacion.getPersona().getTxPrimerNombre();
        }

        return new DonacionResponseDTO(
                donacion.getIdDonacion(),
                donacion.getFecha(),
                donacion.getObservacion(),
                tipoDonacionId,
                tipoDonacionNombre,
                personaId,
                personaNombre
        );
    }

    // --- Getters y Setters ---

    public Integer getIdDonacion() {
        return idDonacion;
    }

    public void setIdDonacion(Integer idDonacion) {
        this.idDonacion = idDonacion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Integer getIdTipoDonacion() {
        return idTipoDonacion;
    }

    public void setIdTipoDonacion(Integer idTipoDonacion) {
        this.idTipoDonacion = idTipoDonacion;
    }

    public String getNombreTipoDonacion() {
        return nombreTipoDonacion;
    }

    public void setNombreTipoDonacion(String nombreTipoDonacion) {
        this.nombreTipoDonacion = nombreTipoDonacion;
    }

    public Integer getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombrePersona() {
        return nombrePersona;
    }

    public void setNombrePersona(String nombrePersona) {
        this.nombrePersona = nombrePersona;
    }
}
