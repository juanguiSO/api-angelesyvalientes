package com.practica.angelesyvalientes.entity;

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
}
