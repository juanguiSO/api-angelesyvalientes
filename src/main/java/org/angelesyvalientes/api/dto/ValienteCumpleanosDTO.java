package org.angelesyvalientes.api.dto;

import java.time.LocalDate;

public class ValienteCumpleanosDTO {

    private String nombres;
    private String apellidos;
    private LocalDate fechaNacimiento;

    public ValienteCumpleanosDTO(String nombres, String apellidos, LocalDate fechaNacimiento) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNombres() { return nombres; }
    public String getApellidos() { return apellidos; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }

}
