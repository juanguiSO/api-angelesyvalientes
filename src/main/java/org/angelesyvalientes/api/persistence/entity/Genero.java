package org.angelesyvalientes.api.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "genero")
@Getter
@Setter
public class Genero {
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática del ID
    @Column(name = "nm_id_genero")
    private int nmIdGenero;

    @Column(name = "tx_genero", length = 15) // Campo de texto con un límite de 15 caracteres
    private String txGenero;


    public int getNmIdGenero() {
        return nmIdGenero;
    }

    public void setNmIdGenero(int nmIdGenero) {
        this.nmIdGenero = nmIdGenero;
    }

    public String getTxGenero() {
        return txGenero;
    }

    public void setTxGenero(String txGenero) {
        this.txGenero = txGenero;
    }

}
