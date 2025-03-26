package org.angelesyvalientes.api.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "grupo_poblacional")
@Data
public class GrupoPoblacional {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática del ID
    @Column(name = "nm_id_grupo_poblacional")
    private int id;

    @Column(name = "tx_grupo_poblacional")
    private String descripcion;

    public void setId(int id) {
        this.id = id;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
