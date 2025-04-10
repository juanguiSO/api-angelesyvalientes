package org.angelesyvalientes.api.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Entidad que representa el tipo de identificación de una persona en el sistema.
 * Mapea la tabla "tipo_identificacion" en la base de datos.
 */
@Data
@Entity
@Table(name = "tipo_identificacion")
public class TipoIdentificacion {
    /**
     * Identificador único del tipo de identificación, generado automáticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nm_tipo_identificacion")
    private int nmTipoIdentificacion;

    /**
     * Descripción del tipo de identificación. No puede ser nulo y tiene una
     * longitud máxima de 45 caracteres.
     */
    @Column(name = "tx_tipo_identificacion", nullable = false, length = 45)
    @NotBlank(message = "La descripción del tipo de identificación es obligatoria.")
    @Size(max = 45, message = "La descripción del tipo de identificación no debe exceder los 45 caracteres.")
    private String txTipoIdentificacion;

    /**
     * Estado del tipo de identificación (activo/inactivo). No puede ser nulo.
     */
    @Column(name = "bo_estado", nullable = false)
    @NotNull(message = "El estado del tipo de identificación es obligatorio.")
    private Boolean boEstado;

    /**
     * Obtiene el identificador único del tipo de identificación.
     *
     * @return El ID del tipo de identificación.
     */
    public int getNmTipoIdentificacion() {
        return nmTipoIdentificacion;
    }

    /**
     * Establece el identificador único del tipo de identificación.
     *
     * @param nmTipoIdentificacion El nuevo ID del tipo de identificación.
     */
    public void setNmTipoIdentificacion(int nmTipoIdentificacion) {
        this.nmTipoIdentificacion = nmTipoIdentificacion;
    }

    /**
     * Establece la descripción del tipo de identificación.
     *
     * @param txTipoIdentificacion La nueva descripción del tipo de identificación.
     */
    public void setTxTipoIdentificacion(String txTipoIdentificacion) {
        this.txTipoIdentificacion = txTipoIdentificacion;
    }

    /**
     * Obtiene la descripción del tipo de identificación.
     *
     * @return La descripción del tipo de identificación.
     */
    public String getTxTipoIdentificacion() {
        return txTipoIdentificacion;
    }

    /**
     * Obtiene el estado del tipo de identificación.
     *
     * @return El estado del tipo de identificación (true para activo, false para inactivo).
     */
    public Boolean getBoEstado() {
        return boEstado;
    }

    /**
     * Establece el estado del tipo de identificación.
     *
     * @param boEstado El nuevo estado del tipo de identificación (true para activo, false para inactivo).
     */
    public void setBoEstado(Boolean boEstado) {
        this.boEstado = boEstado;
    }
}