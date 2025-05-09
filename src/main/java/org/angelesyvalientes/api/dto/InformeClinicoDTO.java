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
}
