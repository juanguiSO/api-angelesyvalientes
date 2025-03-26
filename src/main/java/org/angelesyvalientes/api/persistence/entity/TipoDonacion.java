package org.angelesyvalientes.api.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name = "tipo_donacion")
@Getter
@Setter
public class TipoDonacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nm_id_tipo_donacion")
    private int id;

    @Column(name = "tx_tipo_donacion", length = 45)
    private String tipoDonacion;

    public void setId(int id) {
        this.id = id;
    }

    public void setTipoDonacion(String tipoDonacion) {
        this.tipoDonacion = tipoDonacion;
    }

    public int getId() {
        return id;
    }

    public String getTipoDonacion() {
        return tipoDonacion;
    }
}