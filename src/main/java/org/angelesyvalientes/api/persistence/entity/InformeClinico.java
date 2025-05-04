package org.angelesyvalientes.api.persistence.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

/**
 * Entidad que representa un informe clínico asociado a una persona en el sistema.
 * Mapea la tabla "informe_clinico" en la base de datos.
 */
@Data
@Entity
@Table(name = "informe_clinico")
public class InformeClinico {

    /**
     * Identificador único del informe clínico, generado automáticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nm_id_informe_clinico")
    private Long idInformeClinico;

    /**
     * Relación muchos a uno con la entidad {@link Persona},
     * indicando a qué persona pertenece este informe clínico.
     * La columna de unión en la tabla "informe_clinico" es "nm_id_persona".
     */
    @ManyToOne
    @JoinColumn(name = "nm_id_persona")
    @NotNull(message = "La persona asociada al informe clínico es obligatoria.")
    @JsonProperty("persona")
    private Persona persona;

    /**
     * Fecha en la que se generó el informe clínico.
     */
    @Column(name = "fe_fecha")
    @NotNull(message = "La fecha del informe clínico es obligatoria.")
    @PastOrPresent(message = "La fecha del informe clínico debe ser en el presente o pasado.")
    private LocalDate fecha;

    /**
     * Tipo de informe clínico (ej. evaluación psicológica, examen médico).
     * La longitud máxima permitida para este campo es de 255 caracteres.
     */
    @Column(name = "tx_tipo_informe", length = 255)
    @NotBlank(message = "El tipo de informe clínico es obligatorio.")
    @Size(max = 255, message = "El tipo de informe clínico no debe exceder los 255 caracteres.")
    private String tipoInforme;

    /**
     * Nombre del profesional que generó el informe.
     * La longitud máxima permitida para este campo es de 45 caracteres.
     */
    @Column(name = "tx_profesional", length = 45)
    @NotBlank(message = "El nombre del profesional es obligatorio.")
    @Size(max = 45, message = "El nombre del profesional no debe exceder los 45 caracteres.")
    private String profesional;

    /**
     * URL o ubicación del archivo PDF que contiene el informe clínico.
     * La longitud máxima permitida para este campo es de 45 caracteres.
     */
    @Column(name = "tx_url_pdf", length = 45)
    @Size(max = 255, message = "La URL del PDF no debe exceder los 255 caracteres.") // Corregido a 255 según la descripción
    private String urlPdf;

    /**
     * Establece la persona a la que pertenece este informe clínico.
     *
     * @param persona La entidad {@link Persona} asociada al informe.
     */
    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    /**
     * Establece la URL del archivo PDF del informe clínico.
     *
     * @param urlPdf La URL del PDF del informe.
     */
    public void setUrlPdf(String urlPdf) {
        this.urlPdf = urlPdf;
    }

    /**
     * Establece la fecha del informe clínico.
     *
     * @param fecha La fecha del informe.
     */
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    /**
     * Establece el tipo de informe clínico.
     *
     * @param tipoInforme El tipo de informe.
     */
    public void setTipoInforme(String tipoInforme) {
        this.tipoInforme = tipoInforme;
    }

    /**
     * Establece el nombre del profesional que generó el informe.
     *
     * @param profesional El nombre del profesional.
     */
    public void setProfesional(String profesional) {
        this.profesional = profesional;
    }

    /**
     * Establece el identificador único del informe clínico.
     *
     * @param idInformeClinico El ID del informe clínico.
     */
    public void setIdInformeClinico(Long idInformeClinico) {
        this.idInformeClinico = idInformeClinico;
    }

    /**
     * Obtiene la persona asociada a este informe clínico.
     *
     * @return La entidad {@link Persona} del informe.
     */
    public Persona getPersona() {
        return persona;
    }

    /**
     * Obtiene la fecha del informe clínico.
     *
     * @return La fecha del informe.
     */
    public LocalDate getFecha() {
        return fecha;
    }

    /**
     * Obtiene la URL del archivo PDF del informe clínico.
     *
     * @return La URL del PDF del informe.
     */
    public String getUrlPdf() {
        return urlPdf;
    }

    /**
     * Obtiene el identificador único del informe clínico.
     *
     * @return El ID del informe clínico.
     */
    public Long getIdInformeClinico() {
        return idInformeClinico;
    }

    /**
     * Obtiene el nombre del profesional que generó el informe.
     *
     * @return El nombre del profesional.
     */
    public String getProfesional() {
        return profesional;
    }

    /**
     * Obtiene el tipo de informe clínico.
     *
     * @return El tipo de informe.
     */
    public String getTipoInforme() {
        return tipoInforme;
    }
}