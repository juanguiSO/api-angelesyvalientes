package org.angelesyvalientes.api.dto;

import java.time.LocalDate;

public class FichaDetalleDTO {
    private int idFicha;
    private String nombreFicha;
    private String urlRecurso;
    private int codigoFicha;
    private int idPrograma;
    private String nombrePrograma;
    private int idValiente;
    private LocalDate fechaFinalizacion;

    public FichaDetalleDTO(int idFicha, String nombreFicha, String urlRecurso, int codigoFicha,
                           int idPrograma, String nombrePrograma, int idValiente, LocalDate fechaFinalizacion) {
        this.idFicha = idFicha;
        this.nombreFicha = nombreFicha;
        this.urlRecurso = urlRecurso;
        this.codigoFicha = codigoFicha;
        this.idPrograma = idPrograma;
        this.nombrePrograma = nombrePrograma;
        this.idValiente = idValiente;
        this.fechaFinalizacion = fechaFinalizacion;
    }

    public int getIdFicha() {
        return idFicha;
    }

    public void setIdFicha(int idFicha) {
        this.idFicha = idFicha;
    }

    public String getNombreFicha() {
        return nombreFicha;
    }

    public void setNombreFicha(String nombreFicha) {
        this.nombreFicha = nombreFicha;
    }

    public String getUrlRecurso() {
        return urlRecurso;
    }

    public void setUrlRecurso(String urlRecurso) {
        this.urlRecurso = urlRecurso;
    }

    public int getCodigoFicha() {
        return codigoFicha;
    }

    public void setCodigoFicha(int codigoFicha) {
        this.codigoFicha = codigoFicha;
    }

    public int getIdPrograma() {
        return idPrograma;
    }

    public void setIdPrograma(int idPrograma) {
        this.idPrograma = idPrograma;
    }

    public String getNombrePrograma() {
        return nombrePrograma;
    }

    public void setNombrePrograma(String nombrePrograma) {
        this.nombrePrograma = nombrePrograma;
    }

    public LocalDate getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public void setFechaFinalizacion(LocalDate fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }

    public int getIdValiente() {
        return idValiente;
    }

    public void setIdValiente(int idValiente) {
        this.idValiente = idValiente;
    }
}