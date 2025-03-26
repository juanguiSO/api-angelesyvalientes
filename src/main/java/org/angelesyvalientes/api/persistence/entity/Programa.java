package org.angelesyvalientes.api.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "programa")
@Getter
@Setter
public class Programa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nm_id_programa")
    private int id;

    @Column(nullable = false, name = "tx_programa", length = 45)
    private String nombre;

    @Column(nullable = false, name = "bo_estado", columnDefinition = "Decimal(5,2)")
    private String estado;

    @Column(name = "tx_tema", length = 45)
    private String tema;

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getEstado() {
        return estado;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTema() {
        return tema;
    }

}
