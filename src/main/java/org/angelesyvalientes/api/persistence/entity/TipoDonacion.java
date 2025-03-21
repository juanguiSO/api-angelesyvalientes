package org.angelesyvalientes.api.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tipo_donacion")
public class TipoDonacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nm_id_tipo_donacion")
    private int id;

    @Column(name = "tx_tipo_donacion", length = 45)
    private String tipoDonacion;
}