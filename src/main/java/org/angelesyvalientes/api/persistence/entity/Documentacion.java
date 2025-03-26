package org.angelesyvalientes.api.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "documentacion")
@Getter
@Setter
public class Documentacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nm_id_documentacion")
    private Integer idDocumentacion;

    @ManyToOne
    @JoinColumn(name = "nm_id_persona")
    private Persona persona;

    @Column(name = "tx_tipo_documentacion", length = 255)
    private String tipoDocumentacion;

    @Column(name = "tx_url_pdf", length = 45)
    private String urlPdf;

    @Column(name = "fe_fecha")
    private LocalDate fecha;

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public void setIdDocumentacion(Integer idDocumentacion) {
        this.idDocumentacion = idDocumentacion;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setTipoDocumentacion(String tipoDocumentacion) {
        this.tipoDocumentacion = tipoDocumentacion;
    }

    public void setUrlPdf(String urlPdf) {
        this.urlPdf = urlPdf;
    }

    public Persona getPersona() {
        return persona;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getTipoDocumentacion() {
        return tipoDocumentacion;
    }

    public Integer getIdDocumentacion() {
        return idDocumentacion;
    }

    public String getUrlPdf() {
        return urlPdf;
    }
}