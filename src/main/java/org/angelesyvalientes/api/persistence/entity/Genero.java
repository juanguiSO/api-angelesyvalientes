package org.angelesyvalientes.api.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidad que representa el género de una persona en el sistema.
 * Mapea la tabla "genero" en la base de datos.
 */
@Entity
@Table(name = "genero")
@Getter
@Setter
public class Genero {
    /**
     * Identificador único del género, generado automáticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nm_id_genero")
    private int nmIdGenero;

    /**
     * Descripción del género, con un límite de 15 caracteres.
     */
    @Column(name = "tx_genero", length = 15)
    @NotBlank(message = "La descripción del género es obligatoria.")
    @Size(max = 15, message = "La descripción del género no debe exceder los 15 caracteres.")
    private String txGenero;

    /**
     * Obtiene el identificador único del género.
     *
     * @return El ID del género.
     */
    public int getNmIdGenero() {
        return nmIdGenero;
    }

    /**
     * Establece el identificador único del género.
     *
     * @param nmIdGenero El nuevo ID del género.
     */
    public void setNmIdGenero(int nmIdGenero) {
        this.nmIdGenero = nmIdGenero;
    }

    /**
     * Obtiene la descripción del género.
     *
     * @return La descripción del género.
     */
    public String getTxGenero() {
        return txGenero;
    }

    /**
     * Establece la descripción del género.
     *
     * @param txGenero La nueva descripción del género.
     */
    public void setTxGenero(String txGenero) {
        this.txGenero = txGenero;
    }
}