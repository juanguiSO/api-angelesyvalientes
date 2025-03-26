package org.angelesyvalientes.api.persistence.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "educacion")
public class Educacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nm_id_educacion")
    private int idEducacion;

    @ManyToOne
    @JoinColumn(name = "nm_id_persona")
    private Persona persona;

    @Column(name = "tx_institucion", length = 255)
    private String institucion;

    @Column(name = "tx_nivel", length = 45)
    private String nivel;

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public void setIdEducacion(int idEducacion) {
        this.idEducacion = idEducacion;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public Persona getPersona() {
        return persona;
    }

    public int getIdEducacion() {
        return idEducacion;
    }

    public String getInstitucion() {
        return institucion;
    }

    public String getNivel() {
        return nivel;
    }
}