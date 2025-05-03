package org.angelesyvalientes.api.dto;

import java.time.LocalDate;

public class DocumentacionCreationRequest {
    private int personaId;
    private String tipoDocumentacion;
    private String urlPdf;
    private LocalDate fecha;

    public int getPersonaId() {
        return personaId;
    }

    public void setPersonaId(int personaId) {
        this.personaId = personaId;
    }

    public String getTipoDocumentacion() {
        return tipoDocumentacion;
    }

    public void setTipoDocumentacion(String tipoDocumentacion) {
        this.tipoDocumentacion = tipoDocumentacion;
    }

    public String getUrlPdf() {
        return urlPdf;
    }

    public void setUrlPdf(String urlPdf) {
        this.urlPdf = urlPdf;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}