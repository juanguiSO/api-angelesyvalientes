package org.angelesyvalientes.api.dto;

import java.time.LocalDate;

public class InformeClinicoListResponse {
    private Long idInformeClinico;
    private LocalDate fecha;
    private String tipoInforme;
    private String profesional;
    private String urlPdf;

    // Constructores (puedes generarlos automáticamente en tu IDE)

    public InformeClinicoListResponse() {
    }

    public InformeClinicoListResponse(Long idInformeClinico, LocalDate fecha, String tipoInforme, String profesional, String urlPdf) {
        this.idInformeClinico = idInformeClinico;
        this.fecha = fecha;
        this.tipoInforme = tipoInforme;
        this.profesional = profesional;
        this.urlPdf = urlPdf;
    }

    // Getters y Setters (también puedes generarlos automáticamente)

    public Long getIdInformeClinico() {
        return idInformeClinico;
    }

    public void setIdInformeClinico(Long idInformeClinico) {
        this.idInformeClinico = idInformeClinico;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getTipoInforme() {
        return tipoInforme;
    }

    public void setTipoInforme(String tipoInforme) {
        this.tipoInforme = tipoInforme;
    }

    public String getProfesional() {
        return profesional;
    }

    public void setProfesional(String profesional) {
        this.profesional = profesional;
    }

    public String getUrlPdf() {
        return urlPdf;
    }

    public void setUrlPdf(String urlPdf) {
        this.urlPdf = urlPdf;
    }
}