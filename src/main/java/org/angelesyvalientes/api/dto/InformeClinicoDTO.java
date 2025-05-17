package org.angelesyvalientes.api.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class InformeClinicoDTO {
    private PersonaForeignDTO persona;
    private LocalDate fecha;
    private String tipoInforme;
    private String profesional;
    private String urlPdf;

    public String getUrlPdf() {
        return urlPdf;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public PersonaForeignDTO getPersona() {
        return persona;
    }

    public String getTipoInforme() {
        return tipoInforme;
    }

    public String getProfesional() {
        return profesional;
    }
}

