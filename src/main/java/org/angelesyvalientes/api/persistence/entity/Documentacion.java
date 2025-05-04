package org.angelesyvalientes.api.persistence.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Entidad que representa la documentación asociada a una persona en el sistema.
 * Mapea la tabla "documentacion" en la base de datos.
 */
@Data
@Entity
@Table(name = "documentacion")
@Getter
@Setter
public class Documentacion {

    /**
     * Identificador único de la documentación, generado automáticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nm_id_documentacion")
    private Integer idDocumentacion;

    /**
     * Relación muchos a uno con la entidad {@link Persona},
     * indicando a qué persona pertenece este documento.
     * La columna de unión en la tabla "documentacion" es "nm_id_persona".
     */
    @ManyToOne
    @JoinColumn(name = "nm_id_persona")
    @NotNull(message = "La persona asociada a la documentación es obligatoria.")
    @JsonProperty("persona")
    private Persona persona;

    /**
     * Tipo de documentación (ej. cédula, pasaporte).
     * La longitud máxima permitida para este campo es de 255 caracteres.
     */
    @Column(name = "tx_tipo_documentacion", length = 255)
    @NotBlank(message = "El tipo de documentación es obligatorio.")
    @Size(max = 255, message = "El tipo de documentación no debe exceder los 255 caracteres.")
    private String tipoDocumentacion;

    /**
     * URL o ubicación del archivo PDF que contiene el documento.
     * La longitud máxima permitida para este campo es de 45 caracteres.
     */
    @Column(name = "tx_url_pdf", length = 255)
    @Size(max = 255, message = "La URL del PDF no debe exceder los 255 caracteres.") // Corrección de la longitud máxima
    private String urlPdf;

    /**
     * Fecha en la que se registró o se obtuvo la documentación.
     */
    @Column(name = "fe_fecha")
    @NotNull(message = "La fecha de la documentación es obligatoria.")
    @PastOrPresent(message = "La fecha de la documentación debe ser en el presente o en el pasado.")
    private LocalDate fecha;

    /**
     * Establece la persona a la que pertenece esta documentación.
     *
     * @param persona La entidad {@link Persona} asociada a la documentación.
     */
    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    /**
     * Establece el identificador único de la documentación.
     *
     * @param idDocumentacion El ID de la documentación.
     */
    public void setIdDocumentacion(Integer idDocumentacion) {
        this.idDocumentacion = idDocumentacion;
    }

    /**
     * Establece la fecha de la documentación.
     *
     * @param fecha La fecha del documento.
     */
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    /**
     * Establece el tipo de documentación.
     *
     * @param tipoDocumentacion El tipo de documento.
     */
    public void setTipoDocumentacion(String tipoDocumentacion) {
        this.tipoDocumentacion = tipoDocumentacion;
    }

    /**
     * Establece la URL del archivo PDF del documento.
     *
     * @param urlPdf La URL del PDF del documento.
     */
    public void setUrlPdf(String urlPdf) {
        this.urlPdf = urlPdf;
    }

    /**
     * Obtiene la persona asociada a esta documentación.
     *
     * @return La entidad {@link Persona} del documento.
     */
    public Persona getPersona() {
        return persona;
    }

    /**
     * Obtiene la fecha de la documentación.
     *
     * @return La fecha del documento.
     */
    public LocalDate getFecha() {
        return fecha;
    }

    /**
     * Obtiene el tipo de documentación.
     *
     * @return El tipo de documento.
     */
    public String getTipoDocumentacion() {
        return tipoDocumentacion;
    }

    /**
     * Obtiene el identificador único de la documentación.
     *
     * @return El ID de la documentación.
     */
    public Integer getIdDocumentacion() {
        return idDocumentacion;
    }

    /**
     * Obtiene la URL del archivo PDF del documento.
     *
     * @return La URL del PDF del documento.
     */
    public String getUrlPdf() {
        return urlPdf;
    }
}