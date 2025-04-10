package org.angelesyvalientes.api.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Entidad que representa a un familiar asociado a una vivienda en el sistema.
 * Mapea la tabla "familiar" en la base de datos.
 */
@Data
@Entity
@Table(name = "familiar")
public class Familiar {

    /**
     * Identificador único del familiar, generado automáticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nm_id_familiar")
    private int idFamiliares;

    /**
     * Nombre del familiar.
     */
    @Column(name = "nombre")
    @NotBlank(message = "El nombre del familiar es obligatorio.")
    @Size(max = 255, message = "El nombre del familiar no debe exceder los 255 caracteres.")
    private String nombre;

    /**
     * Identificador del tipo de identificación del familiar (clave foránea).
     * Los atributos `insertable = false` y `updatable = false` indican que este valor
     * se gestiona a través de la relación con la entidad {@link TipoIdentificacion}.
     */
    @Column(name = "nm_tipo_identificacion", insertable = false, updatable = false)
    private int tipoIdentificacionId;

    /**
     * Número de identificación del familiar.
     */
    @Column(name = "tx_numero_identificacion")
    @NotBlank(message = "El número de identificación es obligatorio.")
    @Size(max = 45, message = "El número de identificación no debe exceder los 45 caracteres.")
    private String numeroIdentificacion;

    /**
     * Indica si el familiar trabaja (true) o no (false).
     */
    @Column(name = "bo_trabajo")
    private boolean trabajo;

    /**
     * Indica si el familiar tiene seguridad social (true) o no (false).
     */
    @Column(name = "bo_seguridadsocial")
    private boolean seguridadSocial;

    /**
     * Indica si el familiar sabe leer (true) o no (false).
     */
    @Column(name = "bo_lee")
    private boolean lee;

    /**
     * Indica si el familiar sabe escribir (true) o no (false).
     */
    @Column(name = "bo_escribe")
    private boolean escribe;

    /**
     * Observaciones adicionales sobre el familiar.
     */
    @Column(name = "observaciones")
    @Size(max = 500, message = "Las observaciones no deben exceder los 500 caracteres.")
    private String observaciones;

    /**
     * Identificador de la vivienda a la que pertenece el familiar (clave foránea).
     * Los atributos `insertable = false` y `updatable = false` indican que este valor
     * se gestiona a través de la relación con la entidad {@link Vivienda}.
     */
    @Column(name = "nm_id_vivienda", insertable = false, updatable = false)
    private int idVivienda;

    /**
     * Relación muchos a uno con la entidad {@link Vivienda},
     * indicando la vivienda a la que pertenece este familiar.
     * La columna de unión es "nm_id_vivienda", referenciando la clave primaria de la tabla "vivienda".
     */
    @ManyToOne
    @JoinColumn(name = "nm_id_vivienda", referencedColumnName = "nm_id_vivienda")
    @NotNull(message = "La vivienda del familiar es obligatoria.")
    private Vivienda vivienda;

    /**
     * Relación muchos a uno con la entidad {@link TipoIdentificacion},
     * indicando el tipo de identificación del familiar.
     * La columna de unión es "nm_tipo_identificacion", referenciando la clave primaria de la tabla "tipo_identificacion".
     */
    @ManyToOne
    @JoinColumn(name = "nm_tipo_identificacion", referencedColumnName = "nm_tipo_identificacion")
    @NotNull(message = "El tipo de identificación del familiar es obligatorio.")
    private TipoIdentificacion tipoIdentificacion;

    /**
     * Establece el nombre del familiar.
     *
     * @param nombre El nombre del familiar.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Establece el identificador del tipo de identificación del familiar.
     *
     * @param tipoIdentificacionId El ID del tipo de identificación.
     */
    public void setTipoIdentificacionId(int tipoIdentificacionId) {
        this.tipoIdentificacionId = tipoIdentificacionId;
    }

    /**
     * Establece si el familiar sabe escribir.
     *
     * @param escribe `true` si el familiar sabe escribir, `false` en caso contrario.
     */
    public void setEscribe(boolean escribe) {
        this.escribe = escribe;
    }

    /**
     * Establece el identificador de la vivienda a la que pertenece el familiar.
     *
     * @param idVivienda El ID de la vivienda.
     */
    public void setIdVivienda(int idVivienda) {
        this.idVivienda = idVivienda;
    }

    /**
     * Establece el tipo de identificación del familiar.
     *
     * @param tipoIdentificacion La entidad {@link TipoIdentificacion} del familiar.
     */
    public void setTipoIdentificacion(TipoIdentificacion tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    /**
     * Establece si el familiar tiene seguridad social.
     *
     * @param seguridadSocial `true` si el familiar tiene seguridad social, `false` en caso contrario.
     */
    public void setSeguridadSocial(boolean seguridadSocial) {
        this.seguridadSocial = seguridadSocial;
    }

    /**
     * Establece si el familiar trabaja.
     *
     * @param trabajo `true` si el familiar trabaja, `false` en caso contrario.
     */
    public void setTrabajo(boolean trabajo) {
        this.trabajo = trabajo;
    }

    /**
     * Establece las observaciones sobre el familiar.
     *
     * @param observaciones Las observaciones sobre el familiar.
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     * Establece si el familiar sabe leer.
     *
     * @param lee `true` si el familiar sabe leer, `false` en caso contrario.
     */
    public void setLee(boolean lee) {
        this.lee = lee;
    }

    /**
     * Establece la vivienda a la que pertenece el familiar.
     *
     * @param vivienda La entidad {@link Vivienda} del familiar.
     */
    public void setVivienda(Vivienda vivienda) {
        this.vivienda = vivienda;
    }

    /**
     * Establece el identificador único del familiar.
     *
     * @param idFamiliares El ID del familiar.
     */
    public void setIdFamiliar(int idFamiliares) {
        this.idFamiliares = idFamiliares;
    }

    /**
     * Establece el número de identificación del familiar.
     *
     * @param numeroIdentificacion El número de identificación del familiar.
     */
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }
}