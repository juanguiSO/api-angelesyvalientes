package com.practica.angelesyvalientes.entity;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "tipo_identificacion")
public class TipoIdentificacion {
    @Id


    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática del ID
    @Column(name = "nm_tipo_identificacion")
    private int nmTipoIdentificacion;

    @Column(name = "tx_tipo_identificacion", nullable = false, length = 45) // Campo obligatorio
    private String txTipoIdentificacion;

    @Column(name = "bo_estado", nullable = false) // Campo booleano para el estado
    private Boolean boEstado;

    public int getNmTipoIdentificacion() {
        return nmTipoIdentificacion;
    }

    public void setNmTipoIdentificacion(int nmTipoIdentificacion) {
        this.nmTipoIdentificacion = nmTipoIdentificacion;
    }

    public void setTxTipoIdentificacion(String txTipoIdentificacion) {
        this.txTipoIdentificacion = txTipoIdentificacion;
    }

    public String getTxTipoIdentificacion() {
        return txTipoIdentificacion;
    }

    public Boolean getBoEstado() {
        return boEstado;
    }

    public void setBoEstado(Boolean boEstado) {
        this.boEstado = boEstado;
    }
}
