package org.angelesyvalientes.api.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@Table(name = "angel")
@Getter
@Setter
@NoArgsConstructor
public class Angel  extends Persona{

    @ManyToOne
    @JoinColumn(name = "nm_id_donacion")
    private Donacion donacion;

    @Column(name = "tx_profesion")
    private String profesion;

    @Column(name = "tx_url_galeria")
    private String urlGaleria;

    @Column(name = "tx_descripcion", length = 255)
    private String descripcion;

    @Column(name = "tx_rol_angel", length = 45)
    private String rolAngel;


    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setDonacion(Donacion donacion) {
        this.donacion = donacion;
    }

    public void setRolAngel(String rolAngel) {
        this.rolAngel = rolAngel;
    }

    public void setUrlGaleria(String urlGaleria) {
        this.urlGaleria = urlGaleria;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public String getRolAngel() {
        return rolAngel;
    }

    public Donacion getDonacion() {
        return donacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getProfesion() {
        return profesion;
    }

    public String getUrlGaleria() {
        return urlGaleria;
    }
}