package org.angelesyvalientes.api.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Entidad que representa una donación en el sistema.
 * Mapea la tabla "donacion" en la base de datos.
 */
@Data
@Entity
@Table(name = "donacion")
@Getter
@Setter
public class Donacion {

    /**
     * Identificador único de la donación, generado automáticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nm_id_donacion")
    private int idDonacion;

    /**
     * Relación muchos a uno con la entidad {@link TipoDonacion},
     * especificando el tipo de donación realizada.
     * La columna de unión en la tabla "donacion" es "nm_id_tipo_donacion".
     */
    @ManyToOne
    @JoinColumn(name = "nm_id_tipo_donacion")
    private TipoDonacion tipoDonacion;

    /**
     * Fecha en la que se realizó la donación.
     */
    @Column(name = "fe_fecha")
    private LocalDate fecha;

    /**
     * Observaciones o detalles adicionales sobre la donación,
     * con una longitud máxima de 255 caracteres.
     */
    @Column(name = "tx_observacion", length = 255)
    private String observacion;

    /**
     * Establece la fecha de la donación.
     *
     * @param fecha La fecha en que se realizó la donación.
     */
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    /**
     * Establece el identificador único de la donación.
     *
     * @param idDonacion El ID de la donación.
     */
    public void setIdDonacion(int idDonacion) {
        this.idDonacion = idDonacion;
    }

    /**
     * Establece las observaciones sobre la donación.
     *
     * @param observacion Las observaciones o detalles de la donación.
     */
    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    /**
     * Establece el tipo de donación.
     *
     * @param tipoDonacion La entidad {@link TipoDonacion} que representa el tipo de donación.
     */
    public void setTipoDonacion(TipoDonacion tipoDonacion) {
        this.tipoDonacion = tipoDonacion;
    }

    /**
     * Obtiene las observaciones sobre la donación.
     *
     * @return Las observaciones de la donación.
     */
    public String getObservacion() {
        return observacion;
    }

    /**
     * Obtiene la fecha de la donación.
     *
     * @return La fecha de la donación.
     */
    public LocalDate getFecha() {
        return fecha;
    }

    /**
     * Obtiene el identificador único de la donación.
     *
     * @return El ID de la donación.
     */
    public int getIdDonacion() {
        return idDonacion;
    }

    /**
     * Obtiene el tipo de donación.
     *
     * @return La entidad {@link TipoDonacion} que representa el tipo de donación.
     */
    public TipoDonacion getTipoDonacion() {
        return tipoDonacion;
    }
}
