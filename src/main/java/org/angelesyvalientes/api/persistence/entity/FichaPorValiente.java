package org.angelesyvalientes.api.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "ficha_x_valiente")
@IdClass(FichaPorValienteId.class)
@Getter
@Setter
public class FichaPorValiente {
    @Id
    @Column(name = "nm_id_ficha", nullable = false)
    private int idFicha;

    @Id
    @Column(name = "nm_id_persona", nullable = false)
    private int idValiente;

    @Column(name = "cd_estado", columnDefinition = "CHAR(1) default 'A'")
    private String estado;

    @Column(name = "fe_realizada")
    private LocalDate fechaFinalizacion;

    @OneToOne
    @JoinColumn(name = "nm_id_ficha", nullable = false, insertable = false, updatable = false)
    private Ficha ficha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nm_id_persona", nullable = false, insertable = false, updatable = false)
    private Valiente valiente;
}
