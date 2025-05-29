package org.angelesyvalientes.api.dto;

import java.time.LocalDate;

public class CertificadoDonacionDTO {
    // Datos del Donante
    private String nombreDonante; // Podría ser el nombre de la empresa o del individuo
    private String tipoIdentificacionDonante; // Ej. "NIT", "CC"
    private String numeroIdentificacionDonante;

    // Datos de la Donación
    private LocalDate fechaDonacion;
    private double montoNumerico; // El monto como número
    private String montoEnTexto; // El monto convertido a texto (ej. "DIEZ MILLONES DE PESOS")
    private String tipoDonacion; // Descripción del tipo de donación (ej. "chaquetas")
    private String programaApoyado; // Programa al que se destinó (ej. "cultura y educación (VALIENTES)")

    // Datos de la Corporación (pueden ser estáticos o cargarse de configuración)
    private String nombreCorporacion = "Corporación Ángeles y Valientes";
    private String ubicacionCorporacion = "Guarne, Antioquia";
    private String nitCorporacion = "901456705";

    // Fechas y nombres de firma (podrían ser estáticos o de un usuario autenticado)
    private LocalDate fechaEmisionCertificado = LocalDate.now();
    private String nombreContador = "CLAUDIA NANCY RAVE OSPINA";
    private String tarjetaProfesionalContador = "213274-T";
    private String nombreRepresentanteLegal = "Rocío Flórez Zapata";
    private String ccRepresentanteLegal = "c.c.43210903";

    // Constructor
    public CertificadoDonacionDTO(String nombreDonante, String tipoIdentificacionDonante, String numeroIdentificacionDonante,
                                  LocalDate fechaDonacion, double montoNumerico, String montoEnTexto,
                                  String tipoDonacion, String programaApoyado) {
        this.nombreDonante = nombreDonante;
        this.tipoIdentificacionDonante = tipoIdentificacionDonante;
        this.numeroIdentificacionDonante = numeroIdentificacionDonante;
        this.fechaDonacion = fechaDonacion;
        this.montoNumerico = montoNumerico;
        this.montoEnTexto = montoEnTexto;
        this.tipoDonacion = tipoDonacion;
        this.programaApoyado = programaApoyado;
    }

    // Getters (Lombok @Getter puede ser útil aquí)
    public String getNombreDonante() { return nombreDonante; }
    public String getTipoIdentificacionDonante() { return tipoIdentificacionDonante; }
    public String getNumeroIdentificacionDonante() { return numeroIdentificacionDonante; }
    public LocalDate getFechaDonacion() { return fechaDonacion; }
    public double getMontoNumerico() { return montoNumerico; }
    public String getMontoEnTexto() { return montoEnTexto; }
    public String getTipoDonacion() { return tipoDonacion; }
    public String getProgramaApoyado() { return programaApoyado; }
    public String getNombreCorporacion() { return nombreCorporacion; }
    public String getUbicacionCorporacion() { return ubicacionCorporacion; }
    public String getNitCorporacion() { return nitCorporacion; }
    public LocalDate getFechaEmisionCertificado() { return fechaEmisionCertificado; }
    public String getNombreContador() { return nombreContador; }
    public String getTarjetaProfesionalContador() { return tarjetaProfesionalContador; }
    public String getNombreRepresentanteLegal() { return nombreRepresentanteLegal; }
    public String getCcRepresentanteLegal() { return ccRepresentanteLegal; }
}