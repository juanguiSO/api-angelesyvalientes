package org.angelesyvalientes.api.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ficha")
@Getter
@Setter
public class Ficha {
    @Id
    @Column(name = "nm_id_ficha")
    private int id;

    @Column(name = "tx_tema", length = 45)
    private String nombre;

    @Column(name = "tx_url_recurso", length = 200)
    private String urlRecurso;

    @Column(name = "cod_ficha")
    private int codigo;

    @ManyToOne
    @JoinColumn(name = "nm_id_programa", nullable = false)
    private Programa programa;
}
