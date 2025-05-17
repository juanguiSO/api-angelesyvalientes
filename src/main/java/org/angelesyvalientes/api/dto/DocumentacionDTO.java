package org.angelesyvalientes.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data; // Usamos @Data para generar getters, setters, etc.

import java.time.LocalDate;

/**
 * DTO para la creación y actualización de la entidad {@link org.angelesyvalientes.api.persistence.entity.Documentacion}.
 * Contiene los campos que se esperan recibir desde el cliente.
 */
@Data // Genera getters, setters, equals, hashCode, toString
public class DocumentacionDTO {
    private PersonaForeignDTO persona;
    private LocalDate fecha;
    private String tipoDocumentacion;
    private String urlPdf;

    public PersonaForeignDTO getPersona() {
        return persona;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getTipoDocumentacion() {
        return tipoDocumentacion;
    }

    public String getUrlPdf() {
        return urlPdf;
    }
}