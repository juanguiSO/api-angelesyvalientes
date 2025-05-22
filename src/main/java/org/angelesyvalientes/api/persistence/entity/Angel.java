package org.angelesyvalientes.api.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "angel")
public class Angel {

    @Id
    @Column(name = "nm_id_persona")
    private Long idPersona;  // <--- CAMBIO AQUI

    @Column(name = "tx_profesion", length = 255)
    private String profesion;

    @Column(name = "tx_descripcion", length = 255)
    private String descripcion;

    // Constructor vacío
    public Angel() {
    }

    // Constructor con campos
    public Angel(Long idPersona, String profesion, String descripcion) {
        this.idPersona = idPersona;
        this.profesion = profesion;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public Long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
