package org.angelesyvalientes.api.dto;

import java.time.LocalDate;

public class DonacionRequestDTO {
    private LocalDate fecha;
    private String observacion;
    private Integer idTipoDonacion; // Solo el ID
    private Integer idPersona;      // Solo el ID

    // Getters y Setters (puedes usar Lombok @Data aquí también)
    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Integer getIdTipoDonacion() {
        return idTipoDonacion;
    }

    public void setIdTipoDonacion(Integer idTipoDonacion) {
        this.idTipoDonacion = idTipoDonacion;
    }

    public Integer getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }
}