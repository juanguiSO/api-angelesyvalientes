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

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setFechaFinalizacion(LocalDate fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }

    public void setFicha(Ficha ficha) {
        this.ficha = ficha;
    }

    public void setIdValiente(int idValiente) {
        this.idValiente = idValiente;
    }

    public void setValiente(Valiente valiente) {
        this.valiente = valiente;
    }

    public void setIdFicha(int idFicha) {
        this.idFicha = idFicha;
    }

    public Ficha getFicha() {
        return ficha;
    }

    public int getIdFicha() {
        return idFicha;
    }

    public int getIdValiente() {
        return idValiente;
    }

    public LocalDate getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public String getEstado() {
        return estado;
    }

    public Valiente getValiente() {
        return valiente;
    }
}
