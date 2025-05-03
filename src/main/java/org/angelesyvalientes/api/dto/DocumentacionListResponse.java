package org.angelesyvalientes.api.dto;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;


public class DocumentacionListResponse {
    private Integer idDocumentacion;
    private Long personaId; // O quizás nombre de la persona
    private String tipoDocumentacion;
    private String urlPdf;
    private LocalDate fecha;

    // Constructor, getters y setters

    public void setIdDocumentacion(Integer idDocumentacion) {
        this.idDocumentacion = idDocumentacion;
    }

    public void setPersonaId(Long personaId) {
        this.personaId = personaId;
    }

    public void setTipoDocumentacion(String tipoDocumentacion) {
        this.tipoDocumentacion = tipoDocumentacion;
    }

    public void setUrlPdf(String urlPdf) {
        this.urlPdf = urlPdf;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Long getPersonaId() {
        return personaId;
    }

    public String getTipoDocumentacion() {
        return tipoDocumentacion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getUrlPdf() {
        return urlPdf;
    }

    public Integer getIdDocumentacion() {
        return idDocumentacion;
    }
}