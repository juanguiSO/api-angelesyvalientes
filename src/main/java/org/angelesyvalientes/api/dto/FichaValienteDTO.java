package org.angelesyvalientes.api.dto;


import java.time.LocalDate;

public class FichaValienteDTO {
    private int idFicha;
    private String tema;
    private String urlRecurso;
    private int codigoFicha;
   // private int idPersona;
    private LocalDate fechaRealizada;

    public FichaValienteDTO(int idFicha, String tema, String urlRecurso, int codigoFicha, LocalDate fechaRealizada) {
        this.idFicha = idFicha;
        this.tema = tema;
        this.urlRecurso = urlRecurso;
        this.codigoFicha = codigoFicha;
       // this.idPersona = idPersona;
        this.fechaRealizada = fechaRealizada;
    }

    // Getters (y setters si es necesario)
    public int getIdFicha() {
        return idFicha;
    }

    public String getTema() {
        return tema;
    }

    public String getUrlRecurso() {
        return urlRecurso;
    }

    public int getCodigoFicha() {
        return codigoFicha;
    }
    /**
    public int getIdPersona() {
        return idPersona;
    }*/


    public LocalDate getFechaRealizada() {
        return fechaRealizada;
    }

    // Setters (opcional)
    public void setIdFicha(int idFicha) {
        this.idFicha = idFicha;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public void setUrlRecurso(String urlRecurso) {
        this.urlRecurso = urlRecurso;
    }

    public void setCodigoFicha(int codigoFicha) {
        this.codigoFicha = codigoFicha;
    }
/**
    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }*/

    public void setFechaRealizada(LocalDate fechaRealizada) {
        this.fechaRealizada = fechaRealizada;
    }
}