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
}