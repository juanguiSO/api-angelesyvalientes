package org.angelesyvalientes.api.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name = "angel")
@Getter
@Setter
public class Angel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nm_id_angel")
    private int idAngel;

    @OneToOne
    @JoinColumn(name = "nm_id_persona", referencedColumnName = "nm_id_persona")
    private Persona persona;

    @ManyToOne
    @JoinColumn(name = "nm_id_donacion")
    private Donacion donacion;

    @Column(name = "tx_rol_angel", length = 45)
    private String rolAngel;

    @Column(name = "tx_descripcion", length = 255)
    private String descripcion;
}