package org.angelesyvalientes.api.dto;

import java.time.LocalDate;

public class CartaAgradecimientoDTO {
    private String nombreDonante;
    private String apellidoDonante;
    private String tipoIdentificacionDonante;
    private String numeroIdentificacionDonante;
    private LocalDate fechaDonacion;
    private String tipoDonacion;
    private String observacionDonacion; // Detalles adicionales sobre la donación

    // Constructor
    public CartaAgradecimientoDTO(String nombreDonante, String apellidoDonante,
                                  String tipoIdentificacionDonante, String numeroIdentificacionDonante,
                                  LocalDate fechaDonacion, String tipoDonacion, String observacionDonacion) {
        this.nombreDonante = nombreDonante;
        this.apellidoDonante = apellidoDonante;
        this.tipoIdentificacionDonante = tipoIdentificacionDonante;
        this.numeroIdentificacionDonante = numeroIdentificacionDonante;
        this.fechaDonacion = fechaDonacion;
        this.tipoDonacion = tipoDonacion;
        this.observacionDonacion = observacionDonacion;
    }

    // Getters (Lombok @Getter podría generar estos si lo añades a la clase)
    public String getNombreDonante() {
        return nombreDonante;
    }

    public String getApellidoDonante() {
        return apellidoDonante;
    }

    public String getTipoIdentificacionDonante() {
        return tipoIdentificacionDonante;
    }

    public String getNumeroIdentificacionDonante() {
        return numeroIdentificacionDonante;
    }

    public LocalDate getFechaDonacion() {
        return fechaDonacion;
    }

    public String getTipoDonacion() {
        return tipoDonacion;
    }

    public String getObservacionDonacion() {
        return observacionDonacion;
    }
}