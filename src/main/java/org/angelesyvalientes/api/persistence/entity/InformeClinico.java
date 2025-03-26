package org.angelesyvalientes.api.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "informe_clinico")

public class InformeClinico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nm_id_informe_clinico")
    private Integer idInformeClinico;

    @ManyToOne
    @JoinColumn(name = "nm_id_persona")
    private Persona persona;

    @Column(name = "fe_fecha")
    private LocalDate fecha;

    @Column(name = "tx_tipo_informe", length = 255)
    private String tipoInforme;

    @Column(name = "tx_profesional", length = 45)
    private String profesional;

    @Column(name = "tx_url_pdf", length = 45)
    private String urlPdf;

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public void setUrlPdf(String urlPdf) {
        this.urlPdf = urlPdf;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setTipoInforme(String tipoInforme) {
        this.tipoInforme = tipoInforme;
    }

    public void setProfesional(String profesional) {
        this.profesional = profesional;
    }

    public void setIdInformeClinico(Integer idInformeClinico) {
        this.idInformeClinico = idInformeClinico;
    }

    public Persona getPersona() {
        return persona;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getUrlPdf() {
        return urlPdf;
    }

    public Integer getIdInformeClinico() {
        return idInformeClinico;
    }

    public String getProfesional() {
        return profesional;
    }

    public String getTipoInforme() {
        return tipoInforme;
    }
}