package com.practica.angelesyvalientes.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "clasificacion_valiente")
@Data
public class ClasificacionValiente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática del ID
    @Column(name = "nm_id_clasificacion_valiente")
    private int id;

    @Column(name = "tx_clasificacion_valiente")
    private String descripcion;
}
