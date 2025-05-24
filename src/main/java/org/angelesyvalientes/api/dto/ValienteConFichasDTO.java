package org.angelesyvalientes.api.dto;

import java.time.LocalDate;
import java.util.List;

public class ValienteConFichasDTO {
    private Integer idValiente;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private LocalDate fechaNacimiento;
    private String telefono;
    private String correo;
    private List<FichaValienteDTO> fichasFinalizadas;

    public ValienteConFichasDTO(Integer idValiente, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido,
                               LocalDate fechaNacimiento, String telefono, String correo) {
        this.idValiente = idValiente;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.correo = correo;
        this.fichasFinalizadas = null; // Initialize as null
    }

    // Getters and Setters
    public Integer getIdValiente() {
        return idValiente;
    }

    public void setIdValiente(Integer idValiente) {
        this.idValiente = idValiente;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public List<FichaValienteDTO> getFichasFinalizadas() {
        return fichasFinalizadas;
    }

    public void setFichasFinalizadas(List<FichaValienteDTO> fichasFinalizadas) {
        this.fichasFinalizadas = fichasFinalizadas;
    }
}
