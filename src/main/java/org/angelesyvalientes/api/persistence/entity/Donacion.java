package org.angelesyvalientes.api.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

    @Data
    @Entity
    @Table(name = "donacion")
    @Getter
    @Setter
    public class Donacion {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "nm_id_donacion")
        private int idDonacion;

        @ManyToOne
        @JoinColumn(name = "nm_id_tipo_donacion")
        private TipoDonacion tipoDonacion;

        @Column(name = "fe_fecha")
        private LocalDate fecha;

        @Column(name = "tx_observacion", length = 255)
        private String observacion;

        public void setFecha(LocalDate fecha) {
            this.fecha = fecha;
        }

        public void setIdDonacion(int idDonacion) {
            this.idDonacion = idDonacion;
        }

        public void setObservacion(String observacion) {
            this.observacion = observacion;
        }

        public void setTipoDonacion(TipoDonacion tipoDonacion) {
            this.tipoDonacion = tipoDonacion;
        }

        public String getObservacion() {
            return observacion;
        }

        public LocalDate getFecha() {
            return fecha;
        }

        public int getIdDonacion() {
            return idDonacion;
        }

        public TipoDonacion getTipoDonacion() {
            return tipoDonacion;
        }
    }

